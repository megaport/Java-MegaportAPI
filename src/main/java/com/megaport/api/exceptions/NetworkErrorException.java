package com.megaport.api.exceptions;

/**
 * This is effectively a wrapper for a 500 error
 * Created by adam.wells on 17/06/2016.
 */
public class NetworkErrorException extends RuntimeException{

    private Integer errorCode;
    private Integer httpResponseCode;

    public NetworkErrorException(String message, Integer httpResponseCode, Integer errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.httpResponseCode = httpResponseCode;
    }

    public Integer getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(Integer httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
