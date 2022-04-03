package com.ftn.security.dto;

import com.ftn.security.model.enumeration.CertificateType;
import com.ftn.security.model.enumeration.ExtendedKeyUsage;
import com.ftn.security.model.enumeration.KeyUsage;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateCertificateDto {

    private String issuerMail;
    private String issuerCertificateSerialNumber;
    private CertificateType issuerCertificateType;
    private String subjectMail;
    private Date startDate;
    private Date endDate;
    private List<KeyUsage> keyUsages;
    private List<ExtendedKeyUsage> extendedKeyUsages;
    private CertificateType subjectCertificateType;
}
