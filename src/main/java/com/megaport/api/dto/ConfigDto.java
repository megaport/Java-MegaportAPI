package com.megaport.api.dto;

/**
 * This config is currently optional, and is used to configure the MCR service with a global ASN
 */
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
