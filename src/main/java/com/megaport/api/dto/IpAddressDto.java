package com.megaport.api.dto;

/**
 * Created by adam.wells on 27/06/2016.
 */
public class IpAddressDto {

    private String value;
    private String type;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
