package com.ftn.security.service;

import com.ftn.security.converter.ExtendedKeyUsageConverter;
import com.ftn.security.converter.KeyUsageConverter;
import com.ftn.security.dto.CreateCertificateDto;
import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.keystores.KeyStoreReader;
import com.ftn.security.keystores.KeyStoreWriter;
import com.ftn.security.model.*;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.springframework.stereotype.Service;

import java.io.File;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class CertificateService {

    private final CertificateDataPreparationService certificateDataPreparationService;
    private final ClientService clientService;
    private final KeyUsageConverter keyUsageConverter;
    private final ExtendedKeyUsageConverter extendedKeyUsageConverter;


    public void createRootCertificate(CreateRootCertificateDto dto){
        Client admin=clientService.getClientByMail(dto.getAdminMail());

        Integer[] keyUsages=keyUsageConverter.convertKeyUsageToInteger(dto.getKeyUsages());
        KeyPurposeId[] extendedKeyUsages=extendedKeyUsageConverter.convertToExtendedKeyUsages(dto.getExtendedKeyUsages());
        KeyPair keyPair= certificateDataPreparationService.generateKeyPair();
        SubjectData subjectData= certificateDataPreparationService.generateSubjectData(keyPair,admin,dto.getStartDate(),dto.getEndDate(),keyUsages,extendedKeyUsages);
        IssuerData issuerData= certificateDataPreparationService.generateIssuerData(keyPair.getPrivate(),admin);
        X509Certificate certificate= certificateDataPreparationService.generateCertificate(subjectData,issuerData);

        KeyStoreWriter keyStoreWriter=new KeyStoreWriter();
        /*File file=new File(KeyStoreData.ROOT_STORE_NAME);
        keyStoreWriter.loadKeyStore(null,KeyStoreData.ROOT_STORE_PASS.toCharArray());
        keyStoreWriter.saveKeyStore(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS.toCharArray());*/

        keyStoreWriter.loadKeyStore(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS.toCharArray());
        keyStoreWriter.write(certificate.getSerialNumber().toString()+admin.getMail(),keyPair.getPrivate(),KeyStoreData.ROOT_STORE_PASS.toCharArray(),new Certificate[]{certificate});
        keyStoreWriter.saveKeyStore(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS.toCharArray());

    }

    public ArrayList<X509Certificate> getAllUserCertificates(String name) {
        ArrayList<X509Certificate> allUserCertificates = new ArrayList<X509Certificate>();

        KeyStoreReader keyStoreReader = new KeyStoreReader();
        allUserCertificates.addAll(keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.CA_STORE_NAME, KeyStoreData.CA_STORE_PASS, name));
        allUserCertificates.addAll(keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.END_ENTITY_STORE_NAME, KeyStoreData.END_ENTITY_STORE_PASS, name));

        return allUserCertificates;
    }

    //TODO uraditi provjeru da li issuer moze da izda sertifikat(datum vazenja validan,mozda provjera da li ima odredjene ekstenzije...)
    public void createCertificate(CreateCertificateDto dto){

        KeyStoreWriter keyStoreWriter=new KeyStoreWriter();
        File file=new File(KeyStoreData.END_ENTITY_STORE_NAME);
        keyStoreWriter.loadKeyStore(null,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());
        keyStoreWriter.saveKeyStore(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());

        Client issuer=clientService.getClientByMail(dto.getIssuerMail());
        Client subject=clientService.getClientByMail(dto.getSubjectMail());

        Integer[] subjectKeyUsages=keyUsageConverter.convertKeyUsageToInteger(dto.getKeyUsages());
        KeyPurposeId[] subjectExtendedKeyUsages=extendedKeyUsageConverter.convertToExtendedKeyUsages(dto.getExtendedKeyUsages());

        KeyPair subjectKeyPair=certificateDataPreparationService.generateKeyPair();
        PrivateKey issuerPrivateKey=readCertificatePrivateKey(dto.getIssuerCertificateType(),dto.getIssuerCertificateSerialNumber()+dto.getIssuerMail());

        SubjectData subjectData=certificateDataPreparationService.generateSubjectData(subjectKeyPair,subject,dto.getStartDate(),dto.getEndDate(),subjectKeyUsages,subjectExtendedKeyUsages);
        IssuerData issuerData=certificateDataPreparationService.generateIssuerData(issuerPrivateKey,issuer);
        X509Certificate certificate= certificateDataPreparationService.generateCertificate(subjectData,issuerData);

        Certificate[] certificateChain=createCertificateChain(dto.getIssuerCertificateType(),dto.getIssuerCertificateSerialNumber()+dto.getIssuerMail(),certificate);

        if(dto.getSubjectCertificateType()==CertificateType.CA_CERTIFICATE){
            keyStoreWriter.loadKeyStore(KeyStoreData.CA_STORE_NAME,KeyStoreData.CA_STORE_PASS.toCharArray());
            keyStoreWriter.write(certificate.getSerialNumber().toString()+subject.getMail(),subjectKeyPair.getPrivate(),KeyStoreData.CA_STORE_PASS.toCharArray(),certificateChain);
            keyStoreWriter.saveKeyStore(KeyStoreData.CA_STORE_NAME,KeyStoreData.CA_STORE_PASS.toCharArray());
        }else if(dto.getSubjectCertificateType()==CertificateType.END_ENTITY_CERTIFICATE){
            keyStoreWriter.loadKeyStore(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());
            keyStoreWriter.write(certificate.getSerialNumber().toString()+subject.getMail(),subjectKeyPair.getPrivate(),KeyStoreData.END_ENTITY_STORE_PASS.toCharArray(),certificateChain);
            keyStoreWriter.saveKeyStore(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());
        }

    }

    private PrivateKey readCertificatePrivateKey(CertificateType certificateType,String alias){
        KeyStoreReader keyStoreReader=new KeyStoreReader();
        PrivateKey privateKey=null;
        if (certificateType==CertificateType.ROOT_CERTIFICATE){
            privateKey=keyStoreReader.readPrivateKey(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS,alias,KeyStoreData.ROOT_STORE_PASS);
        }else if(certificateType==CertificateType.CA_CERTIFICATE){
            privateKey=keyStoreReader.readPrivateKey(KeyStoreData.CA_STORE_NAME,KeyStoreData.CA_STORE_PASS,alias,KeyStoreData.CA_STORE_PASS);
        }else{
            privateKey=keyStoreReader.readPrivateKey(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS,alias,KeyStoreData.END_ENTITY_STORE_PASS);
        }
        return privateKey;
    }

    private Certificate[] createCertificateChain(CertificateType issuerCertificateType,String issuerAlias,Certificate subjectCertificate){
        KeyStore keyStore=getKeyStoreByCertificateType(issuerCertificateType);
        try {
            Certificate[] chain=keyStore.getCertificateChain(issuerAlias);
            Certificate[] newChain=new Certificate[chain.length+1];
            newChain[0]=subjectCertificate;
            int i=1;
            for(Certificate certificate:chain){
                newChain[i++]=certificate;
            }
            return newChain;

        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return null;
    }

    private KeyStore getKeyStoreByCertificateType(CertificateType certificateType){
        KeyStoreReader keyStoreReader=new KeyStoreReader();
        KeyStore keyStore=null;
        if (certificateType==CertificateType.ROOT_CERTIFICATE){
            keyStore=keyStoreReader.getKeyStore(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS);
        }else if(certificateType==CertificateType.CA_CERTIFICATE){
            keyStore=keyStoreReader.getKeyStore(KeyStoreData.CA_STORE_NAME,KeyStoreData.CA_STORE_PASS);
        }else{
            keyStore=keyStoreReader.getKeyStore(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS);
        }
        return keyStore;
    }

}
