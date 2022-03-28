package com.ftn.security.authentication.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AuthenticationRequest {
    String mail;
    String password;
}
