package com.ftn.security.keystores;

import com.ftn.security.model.IssuerData;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KeyStoreReader {
    private KeyStore keyStore;

    public KeyStoreReader() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
    /**
     * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
     * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
     *
     * @param keyStoreFile - datoteka odakle se citaju podaci
     * @param alias - alias putem kog se identifikuje sertifikat izdavaoca
     * @param password - lozinka koja je neophodna da se otvori key store
     * @param keyPass - lozinka koja je neophodna da se izvuce privatni kljuc
     * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
     */
    public IssuerData readIssuerFromStore(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            //Datoteka se ucitava
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);
            //Iscitava se sertifikat koji ima dati alias
            Certificate cert = keyStore.getCertificate(alias);
            //Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
            PrivateKey privKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new IssuerData(privKey, issuerName);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ucitava sertifikat is KS fajla
     */
    public Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                Certificate cert = ks.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ucitava privatni kljuc is KS fajla
     */
    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<X509Certificate> getAllCertificatesBySubjectEmail(String keyStoreFile, String keyStorePass, String subjectEmail) {

        ArrayList<X509Certificate> allCertificatesBySubject = new ArrayList<X509Certificate>();

        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            List<String> aliases = Collections.list(ks.aliases());
            for(String alias : aliases){
                if (ks.isKeyEntry(alias)) {
                    X509Certificate certificate = (X509Certificate)ks.getCertificate(alias);
                    if(getEmailFromCertificate(certificate).equals(subjectEmail)){
                        allCertificatesBySubject.add(certificate);
                    }
                }
            }

        } catch (KeyStoreException | NoSuchProviderException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return allCertificatesBySubject;
    }

    private String getEmailFromCertificate(X509Certificate certificate){
        for(String certPart : certificate.getSubjectDN().getName().split(",")){
            if(certPart.contains("EMAILADDRESS")){
                return certPart.split("=")[1];
            }
        }
        return "err";
    }

    public KeyStore getKeyStore(String keyStoreName,String keyStorePassword){
        try {
            KeyStore keyStore=KeyStore.getInstance("JKS","SUN");
            BufferedInputStream inputStream=new BufferedInputStream(new FileInputStream(keyStoreName));
            keyStore.load(inputStream,keyStorePassword.toCharArray());
            return keyStore;
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public X509Certificate getCertificatesBySerialNumber(String keyStoreFile, String keyStorePass, String serialNumber) {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            List<String> aliases = Collections.list(ks.aliases());
            for(String alias : aliases){
                if (ks.isKeyEntry(alias)) {
                    X509Certificate certificate = (X509Certificate)ks.getCertificate(alias);
                    if(certificate.getSerialNumber().toString().equals(serialNumber)){
                        return certificate;
                    }
                }
            }

        } catch (KeyStoreException | NoSuchProviderException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<X509Certificate> getAllCertificates(String keyStoreFile, String keyStorePass) {
        ArrayList<X509Certificate> allCertificates = new ArrayList<X509Certificate>();

        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("JKS", "SUN");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            List<String> aliases = Collections.list(ks.aliases());
            for(String alias : aliases){
                if (ks.isKeyEntry(alias)) {
                    X509Certificate certificate = (X509Certificate)ks.getCertificate(alias);
                    allCertificates.add(certificate);
                }
            }

        } catch (KeyStoreException | NoSuchProviderException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return allCertificates;
    }
}
