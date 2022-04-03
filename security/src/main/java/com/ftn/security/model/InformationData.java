package com.ftn.security.model;

import lombok.Getter;
import lombok.Setter;

import java.security.Principal;

@Getter
@Setter
public class InformationData {

    private String userID;
    private String email;
    private String country;
    private String organizationUnit;
    private String organizationName;
    private String givenName;
    private String surname;
    private String commonName;

    public InformationData(String data) {
        // UID=3, EMAILADDRESS=user2@gmail.com, C=Serbia, OU=PKI-Novi Sad, O=PKI, GIVENNAME=User2, SURNAME=User2, CN=User2 User2
        String[] parts = data.split(",");
        userID = parts[0].split("=")[1];
        email = parts[1].split("=")[1];
        country = parts[2].split("=")[1];
        organizationUnit = parts[3].split("=")[1];
        organizationName = parts[4].split("=")[1];
        givenName = parts[5].split("=")[1];
        surname = parts[6].split("=")[1];
        commonName = parts[7].split("=")[1];
    }
}
