package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by adam on 23/12/2013.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PriceDto implements Serializable {

    private BigDecimal monthlySetup = BigDecimal.ZERO;
    private BigDecimal monthlyRate = BigDecimal.ZERO;
    private String postPaidBaseRate = "no base rate";
    private String currency = "";
    private String key = "no key";
    private ProductType productType;
    private ForceProductChange forceProductChange = null;

    public PriceDto(){}

    public String getPostPaidBaseRate() {
        return postPaidBaseRate;
    }

    public void setPostPaidBaseRate(String postPaidBaseRate) {
        this.postPaidBaseRate = postPaidBaseRate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public BigDecimal getMonthlySetup() {
        return monthlySetup;
    }

    public void setMonthlySetup(BigDecimal monthlySetup) {
        this.monthlySetup = monthlySetup;
    }

    public ForceProductChange getForceProductChange() {
        return forceProductChange;
    }

    public void setForceProductChange(ForceProductChange forceProductChange) {
        this.forceProductChange = forceProductChange;
    }

    public BigDecimal getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @Override
    public String toString() {
        return "PriceDto{" +
                "monthlySetup=" + monthlySetup +
                ", monthlyRate=" + monthlyRate +
                ", postPaidBaseRate='" + postPaidBaseRate + '\'' +
                ", currency='" + currency + '\'' +
                ", key='" + key + '\'' +
                ", productType=" + productType +
                ", forceProductChange=" + forceProductChange +
                '}';
    }
}
