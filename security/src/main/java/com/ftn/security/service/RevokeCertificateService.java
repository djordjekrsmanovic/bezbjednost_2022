package com.ftn.security.service;

import com.ftn.security.model.enumeration.CertificateRevocationReason;
import com.ftn.security.model.RevokedCertificate;
import com.ftn.security.repository.RevokeCertificateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class RevokeCertificateService {

    private final RevokeCertificateRepository revokeCertificateRepository;

    public void revokeCertificate(String certificateSerialNumber, CertificateRevocationReason reason){
        if(revokeCertificateRepository.getCertificateBySerialNumber(certificateSerialNumber)==null){
            RevokedCertificate revokedCertificate=new RevokedCertificate();
            revokedCertificate.setCertificateSerialNumber(certificateSerialNumber);
            revokedCertificate.setRevocationTime(new Date());
            revokedCertificate.setCertificateRevocationReason(reason);
            revokeCertificateRepository.save(revokedCertificate);
        }
    }

    public boolean isRevoked(String certificateSerialNumber){
        return revokeCertificateRepository.getCertificateBySerialNumber(certificateSerialNumber) != null;
    }


}
