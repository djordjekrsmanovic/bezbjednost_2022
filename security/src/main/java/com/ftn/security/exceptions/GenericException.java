package com.ftn.security.exceptions;

import lombok.Getter;

@Getter
public class GenericException extends  RuntimeException{

    private String message;
    public GenericException(String message){
        super(message);
        this.message=message;

    }
}
