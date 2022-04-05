package com.ftn.security.mapper;

import com.ftn.security.dto.UserDto;
import com.ftn.security.model.Client;
import org.springframework.stereotype.Component;


@Component
public class UserToUserDtoMapper implements ModelDtoMapper<Client,UserDto>{
    @Override
    public Client dtoToModel(UserDto userDto) {
        return null;
    }

    @Override
    public UserDto modelToDto(Client client) {
        UserDto userDto=new UserDto();

        userDto.setId(client.getId());
        userDto.setMail(client.getMail());
        userDto.setName(client.getCommonName());
        userDto.setCountry(client.getCountry());
        userDto.setOrganizationUnit(client.getOrganizationUnit());

        return userDto;
    }
}
