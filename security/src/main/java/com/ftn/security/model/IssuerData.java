package com.ftn.security.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class IssuerData {

    private PrivateKey privateKey;
    private X500Name x500name;
}
