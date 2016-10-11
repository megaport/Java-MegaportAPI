package com.megaport.api.exceptions;

/**
 * Created by adam.wells on 17/06/2016.
 */
public class UnauthorizedException extends RuntimeException{

    private Integer errorCode;
    private Integer httpResponseCode;

    public UnauthorizedException(String message, Integer httpResponseCode, Integer errorCode) {
        super(message);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(Integer httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }
}
