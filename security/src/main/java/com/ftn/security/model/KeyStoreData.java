package com.ftn.security.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeyStoreData {

    public static final String ROOT_STORE_NAME="self_signed.jks";
    public static final String ROOT_STORE_PASS="self_signed";

    public static final String CA_STORE_NAME="ca_store.jks";
    public static final String CA_STORE_PASS="ca_store";

    public static final String END_ENTITY_STORE_NAME="end_entity.jks";
    public static final String END_ENTITY_STORE_PASS="end_entity";

}
