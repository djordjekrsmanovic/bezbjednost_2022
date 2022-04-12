package com.ftn.security.service.validation;

import com.ftn.security.dto.CreateCertificateDto;
import com.ftn.security.exceptions.GenericException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CertificateValid implements ValidationService<CreateCertificateDto>{
    @Override
    public void validate(CreateCertificateDto object) {
        if(object.getEndDate()==null || object.getStartDate()==null || object.getSubjectCertificateType()==null || object.getIssuerMail()==null || object.getIssuerCertificateSerialNumber()==null){
            throw new GenericException("Fill all data");
        }
        if(object.getStartDate().after(object.getEndDate()) || object.getEndDate().before(new Date())){ // object.getStartDate().before(new Date())
            throw  new GenericException("Select valid dates");
        }
    }
}
