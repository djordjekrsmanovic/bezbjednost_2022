package com.ftn.security.controller;

import com.ftn.security.authentication.model.AuthenticationRequest;
import com.ftn.security.authentication.model.AuthenticationResponse;
import com.ftn.security.service.AuthenticationService;
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request){
        try {
            AuthenticationResponse authenticationResponse=authenticationService.authenticate(request);
            return new ResponseEntity<>(authenticationResponse,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Wrong username or password", HttpStatus.FORBIDDEN);
        }

    }
}
