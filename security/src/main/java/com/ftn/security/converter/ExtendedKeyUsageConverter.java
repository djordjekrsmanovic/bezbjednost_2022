package com.ftn.security.converter;

import com.ftn.security.model.enumeration.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ExtendedKeyUsageConverter {

    public KeyPurposeId[] convertToExtendedKeyUsages(List<ExtendedKeyUsage> extendedKeyUsages){
        if (extendedKeyUsages.isEmpty()){
            return new KeyPurposeId[0];
        }
        KeyPurposeId[] keyPurposeIds=new KeyPurposeId[extendedKeyUsages.size()];
        int i=0;
        for(ExtendedKeyUsage extendedKeyUsage:extendedKeyUsages){
            keyPurposeIds[i++]=convertToExtendedKeyUsage(extendedKeyUsage);
        }
        return keyPurposeIds;
    }

    private KeyPurposeId convertToExtendedKeyUsage(ExtendedKeyUsage keyUsage){
        switch (keyUsage){
            case IPSEC_USER:return KeyPurposeId.id_kp_ipsecUser;
            case EMAIL_PROTECTION:return KeyPurposeId.id_kp_emailProtection;
            case TSL_WEB_SERVER_AUTHENTICATION:return KeyPurposeId.id_kp_serverAuth;
            case TLS_WEB_CLIENT_AUTHENTICATION:return KeyPurposeId.id_kp_clientAuth;
            case TIMESTAMPING:return KeyPurposeId.id_kp_timeStamping;
            case SIGN_EXECUTABLE_CODE:return KeyPurposeId.id_kp_codeSigning;
            case IPSEC_TUNNEL:return KeyPurposeId.id_kp_ipsecTunnel;
            case IPSEC_END_SYSTEM:return KeyPurposeId.id_kp_ipsecEndSystem;
        }
        return KeyPurposeId.anyExtendedKeyUsage;
    }
}
