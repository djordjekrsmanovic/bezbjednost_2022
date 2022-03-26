package com.ftn.security.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class ApplicationUser {

    private String username;

    private String password;

    private ApplicationRole applicationRole;


}
