package com.ftn.security.service;

import com.ftn.security.model.Client;
import com.ftn.security.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Client> getUsersWithoutPrincipal(){
        List<Client> clients = new ArrayList<>();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();
        for(Client c : clientRepository.getUsers()){
            if(!c.getMail().equals(userEmail)){
                clients.add(c);
            }
        }
    return clients;
    }
}
