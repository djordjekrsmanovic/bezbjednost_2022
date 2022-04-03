package com.ftn.security.dto;

import com.ftn.security.model.enumeration.CertificateRevocationReason;
import com.ftn.security.model.enumeration.CertificateType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RevokeCertificateDto {

    CertificateType certificateType;
    String subjectMail;
    String certificateSerialNumber;
    CertificateRevocationReason reason;

}
