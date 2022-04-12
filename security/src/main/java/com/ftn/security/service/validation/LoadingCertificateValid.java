package com.ftn.security.service.validation;

import com.ftn.security.dto.LoadCertificateForSigningDto;
import com.ftn.security.exceptions.GenericException;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LoadingCertificateValid implements ValidationService<LoadCertificateForSigningDto>{
    @Override
    public void validate(LoadCertificateForSigningDto object) {
        if (object.getDateTo()==null || object.getDateFrom()==null){
            throw new GenericException("Select valid dates for loading certificates");
        }
        if(object.getDateFrom().after(object.getDateTo()) || object.getDateTo().before(new Date())){ // object.getDateFrom().before(new Date())
            throw  new GenericException("Select valid dates");
        }
    }
}
