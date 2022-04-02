package com.ftn.security.dto;

import com.ftn.security.model.CertificateRevocationReason;
import com.ftn.security.model.CertificateType;
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
