package com.ftn.security.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

@Getter
@Setter

public class Subject {

    private PublicKey publicKey;
    private X500Name x500name;
    private String serialNumber;
    private Date startDate;
    private Date endDate;

}
