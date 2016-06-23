package com.megaport.api.exceptions;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String message) {
        super(message);
    }

}
