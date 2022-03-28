package com.ftn.security.authentication.model;

import com.ftn.security.model.Role;
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
    private Role role;

}
