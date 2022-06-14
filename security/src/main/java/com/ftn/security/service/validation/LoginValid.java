package com.ftn.security.service.validation;

import com.ftn.security.authentication.model.AuthenticationRequest;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class LoginValid implements ValidationService<AuthenticationRequest> {
    @Override
    public void validate(AuthenticationRequest object) {

    }

    public boolean validateRequest(AuthenticationRequest object){
        String regexMail = "^(.+)@(.+)$";
        Pattern patternMail = Pattern.compile(regexMail);
        Matcher matcherMail = patternMail.matcher(object.getMail());
        return matcherMail.matches();
    }
}
