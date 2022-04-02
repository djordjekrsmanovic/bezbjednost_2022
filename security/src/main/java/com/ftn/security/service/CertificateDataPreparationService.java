package com.ftn.security.service;

import com.ftn.security.model.Client;
import com.ftn.security.model.IssuerData;
import com.ftn.security.model.SubjectData;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;

import static java.lang.Math.abs;

@Service
public class CertificateDataPreparationService {

    CertificateDataPreparationService(){
        Security.addProvider(new BouncyCastleProvider());
    }
    public X509Certificate generateCertificate(SubjectData subjectData, IssuerData issuerData) {

        try {
            //Posto klasa za generisanje sertifiakta ne moze da primi direktno privatni kljuc pravi se builder za objekat
            //Ovaj objekat sadrzi privatni kljuc izdavaoca sertifikata i koristiti se za potpisivanje sertifikata
            //Parametar koji se prosledjuje je algoritam koji se koristi za potpisivanje sertifiakta
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            //Takodje se navodi koji provider se koristi, u ovom slucaju Bouncy Castle
            builder = builder.setProvider("BC");

            //Formira se objekat koji ce sadrzati privatni kljuc i koji ce se koristiti za potpisivanje sertifikata
            ContentSigner contentSigner = builder.build(issuerData.getPrivateKey());

            //Postavljaju se podaci za generisanje sertifiakta
            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuerData.getX500name(),
                    new BigInteger(subjectData.getSerialNumber()),
                    subjectData.getStartDate(),
                    subjectData.getEndDate(),
                    subjectData.getX500name(),
                    subjectData.getPublicKey());

            certGen.addExtension(Extension.keyUsage,false,new KeyUsage(subjectData.getNumberFromKeyUsage()));

            certGen.addExtension(Extension.extendedKeyUsage,false,new ExtendedKeyUsage(subjectData.getExtendedKeyUsage()));
            //Generise se sertifikat
            X509CertificateHolder certHolder = certGen.build(contentSigner);
            //Builder generise sertifikat kao objekat klase X509CertificateHolder
            //Nakon toga je potrebno certHolder konvertovati u sertifikat, za sta se koristi certConverter
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            //Konvertuje objekat u sertifikat
            return certConverter.getCertificate(certHolder);
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (CertIOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }

    public IssuerData generateIssuerData(PrivateKey issuerKey, Client issuer) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, issuer.getCommonName());
        builder.addRDN(BCStyle.SURNAME, issuer.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, issuer.getGivenName());
        builder.addRDN(BCStyle.O, issuer.getOrganization());
        builder.addRDN(BCStyle.OU, issuer.getOrganizationUnit());
        builder.addRDN(BCStyle.C, issuer.getCountry());
        builder.addRDN(BCStyle.E, issuer.getMail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, issuer.getId().toString());
        //Kreiraju se podaci za issuer-a, sto u ovom slucaju ukljucuje:
        // - privatni kljuc koji ce se koristiti da potpise sertifikat koji se izdaje
        // - podatke o vlasniku sertifikata koji izdaje nov sertifikat
        return new IssuerData(issuerKey, builder.build());
    }

    SubjectData generateSubjectData(KeyPair keyPairSubject, Client client, Date startDate, Date endDate, Integer[] keyUsage, KeyPurposeId[] extendedKeyUsage){
        //Serijski broj sertifikata
        String certificateSerialNumber= generateCertificateSerialNumber();
        //klasa X500NameBuilder pravi X500Name objekat koji predstavlja podatke o vlasniku
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, client.getCommonName());
        builder.addRDN(BCStyle.SURNAME, client.getSurname());
        builder.addRDN(BCStyle.GIVENNAME, client.getGivenName());
        builder.addRDN(BCStyle.O, client.getOrganization());
        builder.addRDN(BCStyle.OU, client.getOrganizationUnit());
        builder.addRDN(BCStyle.C, client.getCountry());
        builder.addRDN(BCStyle.E, client.getMail());
        //UID (USER ID) je ID korisnika
        builder.addRDN(BCStyle.UID, client.getId().toString());

        //Kreiraju se podaci za sertifikat, sto ukljucuje:
        // - javni kljuc koji se vezuje za sertifikat
        // - podatke o vlasniku
        // - serijski broj sertifikata
        // - od kada do kada vazi sertifikat
        SubjectData subjectData=new SubjectData();
        subjectData.setSerialNumber(certificateSerialNumber);
        subjectData.setX500name(builder.build());
        subjectData.setPublicKey(keyPairSubject.getPublic());
        subjectData.setStartDate(startDate);
        subjectData.setEndDate(endDate);
        subjectData.setKeyUsage(keyUsage);
        subjectData.setExtendedKeyUsage(extendedKeyUsage);
        return subjectData;

    }

    private String generateCertificateSerialNumber(){
        try {
            Random random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            Integer number=abs(random.nextInt());
            return number.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
