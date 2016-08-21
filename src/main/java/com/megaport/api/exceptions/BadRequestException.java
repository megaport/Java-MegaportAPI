package com.megaport.api.exceptions;

/**
 * This kind of exception is caused by input, where we expect the caller to be able to do something about it.
 * Created by adam.wells on 17/06/2016.
 */
public class BadRequestException extends RuntimeException{

    private Integer errorCode;
    private Integer httpResponseCode;

    public BadRequestException(String message, Integer httpResponseCode, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpResponseCode = httpResponseCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(Integer httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
