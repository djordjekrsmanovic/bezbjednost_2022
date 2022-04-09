package com.ftn.security;

import com.ftn.security.keystores.KeyStoreWriter;
import com.ftn.security.model.KeyStoreData;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
@EnableAutoConfiguration
@RequiredArgsConstructor
public class Initialization implements ApplicationListener<ApplicationReadyEvent> {

    private final KeyStoreWriter keyStoreWriter;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Map<String, String> envVariables = System.getenv();
        KeyStoreData.ROOT_STORE_PASS=envVariables.get("ROOT_STORE_PASS");
        KeyStoreData.CA_STORE_PASS=envVariables.get("CA_STORE_PASS");
        KeyStoreData.END_ENTITY_STORE_PASS=envVariables.get("END_ENTITY_STORE_PASS");
        initializeKeyStores();
    }

    private void initializeKeyStores(){
        File rootStore=new File(KeyStoreData.ROOT_STORE_NAME);
        File caStore=new File(KeyStoreData.CA_STORE_NAME);
        File endEntityStore=new File(KeyStoreData.END_ENTITY_STORE_NAME);

        if (rootStore.exists() && caStore.exists() && endEntityStore.exists() ){
            System.out.println("Key stores exists");
            return;
        }
        generateStores(rootStore,caStore,endEntityStore);

    }

    private void generateStores(File rootStore, File caStore, File endEntityStore) {
        if (!rootStore.exists()){
            initializeRootKeyStore();
        }
        if (!caStore.exists()){
            initializeCaStore();
        }
        if (!endEntityStore.exists()){
            initializeEndEntityStore();
        }
    }

    private void initializeEndEntityStore() {
        keyStoreWriter.loadKeyStore(null,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());
        keyStoreWriter.saveKeyStore(KeyStoreData.END_ENTITY_STORE_NAME,KeyStoreData.END_ENTITY_STORE_PASS.toCharArray());
    }

    private void initializeCaStore() {
        keyStoreWriter.loadKeyStore(null,KeyStoreData.CA_STORE_PASS.toCharArray());
        keyStoreWriter.saveKeyStore(KeyStoreData.CA_STORE_NAME,KeyStoreData.CA_STORE_PASS.toCharArray());
    }

    private void initializeRootKeyStore() {
        keyStoreWriter.loadKeyStore(null,KeyStoreData.ROOT_STORE_PASS.toCharArray());
        keyStoreWriter.saveKeyStore(KeyStoreData.ROOT_STORE_NAME,KeyStoreData.ROOT_STORE_PASS.toCharArray());
    }



}
