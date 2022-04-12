package com.ftn.security.converter;

import com.ftn.security.model.enumeration.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public ArrayList<ExtendedKeyUsage> getExtendedKeyUsage(List<String> extendedKeyUsage) {
        ArrayList<ExtendedKeyUsage> extendedKeyUsages = new ArrayList<ExtendedKeyUsage>();

        // List<String> extendedKeyUsage = {"1.3.6.1.5.5.7.3.4", "1.3.6.1.5.5.7.3.2", "1.3.6.1.5.5.7.3.1", ...}
        // we wil use last number to identify extended key usage
        // https://docs.aws.amazon.com/acm/latest/APIReference/API_ExtendedKeyUsage.html

        for(String extKU : extendedKeyUsage){
            int num = Integer.parseInt(extKU.split("\\.")[8]);

            switch (num){
                // 1.3.6.1.5.5.7.3.1 (TLS_WEB_SERVER_AUTHENTICATION)
                case 1: extendedKeyUsages.add(ExtendedKeyUsage.TSL_WEB_SERVER_AUTHENTICATION); break;

                // 1.3.6.1.5.5.7.3.2 (TLS_WEB_CLIENT_AUTHENTICATION)
                case 2: extendedKeyUsages.add(ExtendedKeyUsage.TLS_WEB_CLIENT_AUTHENTICATION); break;

                // 1.3.6.1.5.5.7.3.3 (CODE_SIGNING)
                case 3: extendedKeyUsages.add(ExtendedKeyUsage.SIGN_EXECUTABLE_CODE); break;

                // 1.3.6.1.5.5.7.3.4 (EMAIL_PROTECTION)
                case 4: extendedKeyUsages.add(ExtendedKeyUsage.EMAIL_PROTECTION); break;

                // 1.3.6.1.5.5.7.3.5 (IPSEC_END_SYSTEM)
                case 5: extendedKeyUsages.add(ExtendedKeyUsage.IPSEC_END_SYSTEM); break;

                // 1.3.6.1.5.5.7.3.6 (IPSEC_TUNNEL)
                case 6: extendedKeyUsages.add(ExtendedKeyUsage.IPSEC_TUNNEL); break;

                // 1.3.6.1.5.5.7.3.7 (IPSEC_USER)
                case 7: extendedKeyUsages.add(ExtendedKeyUsage.IPSEC_USER); break;

                // 1.3.6.1.5.5.7.3.8 (TIME_STAMPING)
                case 8: extendedKeyUsages.add(ExtendedKeyUsage.TIMESTAMPING); break;

                // 1.3.6.1.5.5.7.3.9 (OCSP_SIGNING)
                case 9: extendedKeyUsages.add(ExtendedKeyUsage.SIGN_EXECUTABLE_CODE); break;
            }
        }

        return extendedKeyUsages;
    }
}
