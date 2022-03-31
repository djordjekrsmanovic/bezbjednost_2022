package com.ftn.security.model;

import java.security.PublicKey;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.KeyPurposeId;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class SubjectData {

    private PublicKey publicKey;
    private X500Name x500name;
    private Long serialNumber;
    private Date startDate;
    private Date endDate;
    private Integer[] keyUsage;
    private KeyPurposeId[] extendedKeyUsage;


    public Integer getNumberFromKeyUsage(){
        Integer retVal=0;
        for (int i=0;i<keyUsage.length;i++){
            retVal=retVal|keyUsage[i];
        }
        return retVal;
    }

}
