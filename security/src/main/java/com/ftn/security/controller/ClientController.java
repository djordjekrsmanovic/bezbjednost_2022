package com.ftn.security.controller;

import com.ftn.security.dto.UserDto;
import com.ftn.security.mapper.ModelDtoMapper;
import com.ftn.security.model.Client;
import com.ftn.security.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;
    private final ModelDtoMapper modelDtoMapper;

    @GetMapping("/get-users")
    List<UserDto> getUsers(){
        List<Client> users=clientService.getUsers();
        return modelDtoMapper.modelToDtoList(users);
    }

    @GetMapping("/get-users-without-principal")
    List<UserDto> getUsersWithoutPrincipal(){
        List<Client> users=clientService.getUsersWithoutPrincipal();
        return modelDtoMapper.modelToDtoList(users);
    }
}
