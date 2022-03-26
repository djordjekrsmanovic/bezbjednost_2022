package com.ftn.security.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface ModelDtoMapper <Model,Dto>{

    Model dtoToModel(Dto dto);

    Dto modelToDto(Model model);

    default List<Model> dtoToModelList(List<Dto> dtos){
        List<Model> models=dtos.stream().map(dto -> dtoToModel(dto)).collect(Collectors.toList());
        return models;
    }

    default List<Dto> modelToDtoList(List<Model> models){
        List<Dto> dtos=models.stream().map(model -> modelToDto(model)).collect(Collectors.toList());
        return dtos;
    }

}
