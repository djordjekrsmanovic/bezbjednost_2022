package com.ftn.security.controller;

import com.ftn.security.dto.UserDto;
import com.ftn.security.mapper.ModelDtoMapper;
import com.ftn.security.model.Client;
import com.ftn.security.service.ClientService;
import com.ftn.security.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final LoggingService loggingService;

    @PreAuthorize("hasAuthority('GET_USERS_PERMISSION')")
    @GetMapping("/get-users")
    List<UserDto> getUsers(){
        List<Client> users=clientService.getUsers();
        loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " requested all users list.");
        return modelDtoMapper.modelToDtoList(users);
    }

    @PreAuthorize("hasAuthority('GET_USERS_WITHOUT_PRINCIPAL_PERMISSION')")
    @GetMapping("/get-users-without-principal")
    List<UserDto> getUsersWithoutPrincipal(){
        List<Client> users=clientService.getUsersWithoutPrincipal();
        loggingService.MakeInfoLog("User " + clientService.getLoggedUserMail() + " requested all users without principal list.");
        return modelDtoMapper.modelToDtoList(users);
    }
}
