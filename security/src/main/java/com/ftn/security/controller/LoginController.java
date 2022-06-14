package com.ftn.security.controller;

import com.ftn.security.authentication.model.AuthenticationRequest;
import com.ftn.security.authentication.model.AuthenticationResponse;
import com.ftn.security.service.AuthenticationService;
import com.ftn.security.service.LoggingService;
import com.ftn.security.service.validation.LoginValid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LoginController {

    private final AuthenticationService authenticationService;
    private final LoggingService loggingService;
    private final LoginValid   loginValid;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        try {
            if(!loginValid.validateRequest(request)){
                return new ResponseEntity<>("Bad format of email.",HttpStatus.NOT_ACCEPTABLE);
            }
            AuthenticationResponse authenticationResponse=authenticationService.authenticate(request);
            loggingService.MakeInfoLog("User " + request.getMail() + " has logged in.");
            return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
        } catch (Exception e) {
            loggingService.MakeWarningLog("User " + request.getMail() + " tryed to log in with bad credentials.");
            return new ResponseEntity<>("Wrong username or password", HttpStatus.FORBIDDEN);
        }

    }
}
