package com.ftn.security.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;

@Getter
@Setter
@RequiredArgsConstructor
public class Issuer {

    private X500Name x500name;
    private PrivateKey privateKey;
}
