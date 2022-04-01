package com.ftn.security.dto;

import com.ftn.security.converter.KeyUsageConverter;
import com.ftn.security.model.CertificateType;
import com.ftn.security.model.ExtendedKeyUsage;
import com.ftn.security.model.InformationData;
import com.ftn.security.model.KeyUsage;
import lombok.Getter;
import lombok.Setter;

import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CertificateDTO {

    private InformationData subjectData;
    private InformationData issuerData;
    private String serialNumber;
    private Date startDate;
    private Date endDate;
    private List<KeyUsage> keyUsages;
    private List<ExtendedKeyUsage> extendedKeyUsages;
    private CertificateType certificateType;

    public CertificateDTO(X509Certificate x509cer, List<KeyUsage> keyUsages, List<ExtendedKeyUsage> extendedKeyUsages) {
        this.subjectData = new InformationData(x509cer.getSubjectDN().getName());
        this.issuerData = new InformationData(x509cer.getIssuerDN().getName());
        this.serialNumber = x509cer.getSerialNumber().toString();
        this.startDate = x509cer.getNotBefore();
        this.endDate = x509cer.getNotAfter();
        this.keyUsages = keyUsages;
        this.extendedKeyUsages = extendedKeyUsages;
        if(x509cer.getBasicConstraints() == -1){
            this.certificateType = CertificateType.END_ENTITY_CERTIFICATE;
        }else{
            //TODO: na ovaj nacin mozemo znati dal ima pravo CA, ali ne mozemo znati dal je root
            this.certificateType = CertificateType.CA_CERTIFICATE;
        }

    }
}
