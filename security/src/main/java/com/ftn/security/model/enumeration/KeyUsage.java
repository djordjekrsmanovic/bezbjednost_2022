package com.ftn.security.model.enumeration;

public enum KeyUsage {
    DIGITAL_SIGNATURE,
    NON_REPUDATION,
    KEY_ENCIPHERMENT,
    DATA_ENCIPHERMENT,
    KEY_AGREEMENT,
    KEY_CERT_SIGN,
    CRL_SIGN,
    ENCIPHER_ONLY,
    DECIPHER_ONLY;

    public static KeyUsage fromInteger(int x){
        switch (x){
            case 0: return DIGITAL_SIGNATURE;
            case 1: return NON_REPUDATION;
            case 2: return KEY_ENCIPHERMENT;
            case 3: return DATA_ENCIPHERMENT;
            case 4: return KEY_AGREEMENT;
            case 5: return KEY_CERT_SIGN;
            case 6: return CRL_SIGN;
            case 7: return ENCIPHER_ONLY;
            case 8: return DECIPHER_ONLY;
        }
        return DIGITAL_SIGNATURE;
    }
}
