package com.ftn.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    Long id;
    private String mail;
    private String name;
    private String country;
    private String organizationUnit;
}
