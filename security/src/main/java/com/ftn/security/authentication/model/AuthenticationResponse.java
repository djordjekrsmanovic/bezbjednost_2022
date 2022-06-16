package com.ftn.security.authentication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {

    private String jwt;
    private String mail;
    private String role;

}
