package com.ftn.security.model;

import com.ftn.security.model.enumeration.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mail;

    private String password;

    private Role role;

    private String commonName;

    private String surname;

    private String givenName;

    private String organization;

    private String organizationUnit;

    private String country;


}
