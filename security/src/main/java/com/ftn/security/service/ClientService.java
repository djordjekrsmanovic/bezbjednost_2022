package com.ftn.security.service;

import com.ftn.security.model.Client;
import com.ftn.security.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    public Client getClientByMail(String mail){
        return clientRepository.getClientByMail(mail);
    }

    public List<Client> getUsers(){
        return clientRepository.getUsers();
    }
}
