package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * This config is currently optional, and is used to configure the MCR service with a global ASN
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigDto {
    private Long mcrAsn;

    public ConfigDto() { }

    public ConfigDto(Long mcrAsn) {
        this.mcrAsn = mcrAsn;
    }

    public long getMcrAsn() {
        return mcrAsn;
    }

    public void setMcrAsn(Long mcrAsn) {
        this.mcrAsn = mcrAsn;
    }


    @Override
    public String toString() {
        return "ConfigDto{" +
                "mcrAsn=" + mcrAsn +
                '}';
    }
}
