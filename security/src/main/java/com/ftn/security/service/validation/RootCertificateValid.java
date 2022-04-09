package com.ftn.security.service.validation;

import com.ftn.security.dto.CreateRootCertificateDto;
import com.ftn.security.exceptions.GenericException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RootCertificateValid implements ValidationService<CreateRootCertificateDto> {

    @Override
    public void validate(CreateRootCertificateDto object) {
        if(object.getStartDate()==null || object.getEndDate()==null || object.getKeyUsages()==null || object.getExtendedKeyUsages()==null){
            throw new GenericException("Fill all data");
        }
        if(object.getStartDate().after(object.getEndDate()) || object.getStartDate().before(new Date()) || object.getEndDate().before(new Date())){
            throw  new GenericException("Select valid dates");
        }
    }
}
