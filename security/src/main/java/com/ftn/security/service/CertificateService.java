package com.ftn.security.service;

import com.ftn.security.converter.ExtendedKeyUsageConverter;
import com.ftn.security.converter.KeyUsageConverter;
import com.ftn.security.dto.CertificateDTO;
import com.ftn.security.dto.CreateCertificateDto;
import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.dto.RevokeCertificateDto;
import com.ftn.security.keystores.KeyStoreReader;
import com.ftn.security.keystores.KeyStoreWriter;
import com.ftn.security.model.*;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.*;
@RequiredArgsConstructor
@Service
public class CertificateService {

    private final CertificateDataPreparationService certificateDataPreparationService;
    private final ClientService clientService;
    private final KeyUsageConverter keyUsageConverter;
    private final ExtendedKeyUsageConverter extendedKeyUsageConverter;
    private final RevokeCertificateService revokeCertificateService;


    //todo provjeriti da li je uneseni datum validan
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

    public ArrayList<X509Certificate> getCertificatesByEmail(String email){
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        ArrayList<X509Certificate> certificates = new ArrayList<X509Certificate>();
        certificates.addAll(keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.ROOT_STORE_NAME, KeyStoreData.ROOT_STORE_PASS, email));
        certificates.addAll(keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.CA_STORE_NAME, KeyStoreData.CA_STORE_PASS, email));
        certificates.addAll(keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.END_ENTITY_STORE_NAME, KeyStoreData.END_ENTITY_STORE_PASS, email));
        return certificates;
    }

    public ArrayList<CertificateDTO> getAllUserCertificatesDTO(String name) {
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        ArrayList<CertificateDTO> allUserCertificatesDTO = new ArrayList<CertificateDTO>();
        //TODO: dodati poziv metode za proveru da li je sertifikat revoked ili ne za sada su svi false
        for(X509Certificate x509cer : keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.ROOT_STORE_NAME, KeyStoreData.ROOT_STORE_PASS, name)){
            allUserCertificatesDTO.add(new CertificateDTO(x509cer, false, keyUsageConverter.getKeyUsageFromBooleanArr(x509cer.getKeyUsage()), new ArrayList<ExtendedKeyUsage>(), CertificateType.ROOT_CERTIFICATE));
        }
        for(X509Certificate x509cer : keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.CA_STORE_NAME, KeyStoreData.CA_STORE_PASS, name)){
            allUserCertificatesDTO.add(new CertificateDTO(x509cer, false, keyUsageConverter.getKeyUsageFromBooleanArr(x509cer.getKeyUsage()), new ArrayList<ExtendedKeyUsage>(), CertificateType.CA_CERTIFICATE));
        }
        for(X509Certificate x509cer : keyStoreReader.getAllCertificatesBySubjectEmail(KeyStoreData.END_ENTITY_STORE_NAME, KeyStoreData.END_ENTITY_STORE_PASS, name)){
            allUserCertificatesDTO.add(new CertificateDTO(x509cer, false, keyUsageConverter.getKeyUsageFromBooleanArr(x509cer.getKeyUsage()), new ArrayList<ExtendedKeyUsage>(), CertificateType.END_ENTITY_CERTIFICATE));
        }
        return allUserCertificatesDTO;
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

        if(!issuerCanCreateCertificate(dto.getIssuerCertificateSerialNumber())){
            System.out.println("Error: createCertificate: issuer can not create certificate");
            return;
        }

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

    public boolean issuerCanCreateCertificate(String issuerSerialNumber){
        X509Certificate issuerX509Certificate = getX509CertificateBySerialNumber(issuerSerialNumber);

        // mora da postoji
        if(issuerX509Certificate == null){
            System.out.println("Error: issuerCrtificate is not found");
            return false;
        }

        // mora biti root ili CA
        if(issuerX509Certificate.getBasicConstraints() == -1){
            System.out.println("Error: issuerCrtificate is not CA");
            return false;
        }

        if(!certificateIsValid(issuerX509Certificate)){
            return false;
        }

        // sve provere su ispunjene
        return true;
    }

    public boolean certificateIsValid(X509Certificate x509){
        // ne sme da istekne period vazenja
        if(x509.getNotAfter().before(new Date())){
            System.out.println("Error: issuerCrtificate expired");
            return false;
        }

        // ne sme biti revoked
        if(false){ //TODO: provera da li je sertifikat reovek
            System.out.println("Error: issuerCrtificate is revoked");
            return false;
        }

        return true;
    }

    public X509Certificate getX509CertificateBySerialNumber(String serialNumber){
        KeyStoreReader keyStoreReader = new KeyStoreReader();
        X509Certificate certificate = null;
        certificate = keyStoreReader.getCertificatesBySerialNumber(KeyStoreData.ROOT_STORE_NAME, KeyStoreData.ROOT_STORE_PASS, serialNumber);
        if(certificate != null)
            return certificate;
        certificate = keyStoreReader.getCertificatesBySerialNumber(KeyStoreData.CA_STORE_NAME, KeyStoreData.CA_STORE_PASS, serialNumber);
        if(certificate != null)
            return certificate;
        certificate = keyStoreReader.getCertificatesBySerialNumber(KeyStoreData.END_ENTITY_STORE_NAME, KeyStoreData.END_ENTITY_STORE_PASS, serialNumber);
        if(certificate != null)
            return certificate;

        // sertifikat nije pronadjen ni u jednom keystoru
        return null;
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

    public void revokeCertificate(RevokeCertificateDto dto){

        KeyStore rootKeyStore=getKeyStoreByCertificateType(CertificateType.ROOT_CERTIFICATE);
        KeyStore caKeyStore=getKeyStoreByCertificateType(CertificateType.CA_CERTIFICATE);
        KeyStore endEntityKeyStore=getKeyStoreByCertificateType(CertificateType.END_ENTITY_CERTIFICATE);
        if(dto.getCertificateType()==CertificateType.ROOT_CERTIFICATE){ //if certificate type is root certificate we must check every key store and revoke certificates in chain
            revokeByCertificateStore(rootKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
            revokeByCertificateStore(caKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
            revokeByCertificateStore(endEntityKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
        }else if(dto.getCertificateType()==CertificateType.CA_CERTIFICATE){ //if certificate type is ca we must only check ca key store and end entity key store
            revokeByCertificateStore(caKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
            revokeByCertificateStore(endEntityKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
        }else if(dto.getCertificateType()==CertificateType.END_ENTITY_CERTIFICATE){ //if certificate type is end-entity we must only check end entity key store
            revokeByCertificateStore(endEntityKeyStore,dto.getCertificateSerialNumber(),dto.getReason());
        }
    }

    public void revokeByCertificateStore(KeyStore keyStore,String serialNumber,CertificateRevocationReason reason){
        List<String> aliases= null;
        try {
            aliases = Collections.list(keyStore.aliases());
            for(String alias:aliases){
                revokeCertificateChain(keyStore.getCertificateChain(alias),serialNumber,reason);
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

    }

    private void revokeCertificateChain(Certificate[] certificateChain,String serialNumber,CertificateRevocationReason reason) {
        int i=certificateChain.length-1;
        for(;i>=0;i--){
            if(((X509Certificate)certificateChain[i]).getSerialNumber().equals(new BigInteger(serialNumber))){
                revokeCertificateService.revokeCertificate(serialNumber,reason);
                i--;
                break;
            }
        }
        for(;i>=0;i--){
            revokeCertificateService.revokeCertificate(((X509Certificate)certificateChain[i]).getSerialNumber().toString(),reason);
        }


    }


}
