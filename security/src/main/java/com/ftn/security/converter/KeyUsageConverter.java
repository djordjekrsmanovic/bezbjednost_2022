package com.ftn.security.converter;

import com.ftn.security.model.KeyUsage;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KeyUsageConverter {

    public  Integer[] convertKeyUsageToInteger(List<KeyUsage> keyUsageList){
        if (keyUsageList.isEmpty()){
            return new Integer[0];
        }
        Integer [] keyUsageArray=new Integer[keyUsageList.size()];
        int i=0;
        for(KeyUsage keyUsage:keyUsageList){
            keyUsageArray[i++]=getUsageNumber(keyUsage);
        }
        return keyUsageArray;
    }

    private Integer getUsageNumber(KeyUsage keyUsage){
        switch (keyUsage){
            case DIGITAL_SIGNATURE:return 1<<7;
            case NON_REPUDATION:return 1<<6;
            case KEY_ENCIPHERMENT:return 1<<5;
            case DATA_ENCIPHERMENT:return 1<<4;
            case KEY_AGREEMENT:return 1<<3;
            case KEY_CERT_SIGN:return 1<<2;
            case CRL_SIGN:return 1<<1;
            case ENCIPHER_ONLY:return 1<<0;
            case DECIPHER_ONLY:return 1<<15;
        }

        return null;
    }
}
