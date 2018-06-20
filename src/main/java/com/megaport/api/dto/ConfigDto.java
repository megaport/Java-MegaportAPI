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

    boolean isValid() {
        return (mcrAsn != null) && (mcrAsn >= 0L && mcrAsn <= 4294967295L);
    }

    public String validate() {
        if (!isValid()) {
            return "Wrong asn number: " + mcrAsn;
        }
        return "";
    }

    @Override
    public String toString() {
        return "ConfigDto{" +
                "mcrAsn=" + mcrAsn +
                '}';
    }
}
