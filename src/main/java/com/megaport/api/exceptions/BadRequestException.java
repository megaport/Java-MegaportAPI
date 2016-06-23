package com.megaport.api.exceptions;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class BadRequestException extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
    }

}
