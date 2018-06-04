package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VxcServiceModificationDto implements Serializable {

    private String productUid;
    private String productName;
    private Integer rateLimit;
    private Integer aEndVlan;
    private Integer bEndVlan;

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
    }

    public Integer getaEndVlan() {
        return aEndVlan;
    }

    public void setaEndVlan(Integer aEndVlan) {
        this.aEndVlan = aEndVlan;
    }

    public Integer getbEndVlan() {
        return bEndVlan;
    }

    public void setbEndVlan(Integer bEndVlan) {
        this.bEndVlan = bEndVlan;
    }
}
