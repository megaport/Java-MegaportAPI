package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by adam on 23/12/2013.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDto implements Serializable {

    private BigDecimal monthlySetup = BigDecimal.ZERO;
    private BigDecimal monthlyRate = BigDecimal.ZERO;
    private String postPaidBaseRate = null;
    private String currency = "";

    public BigDecimal getMonthlySetup() {
        return monthlySetup;
    }

    public void setMonthlySetup(BigDecimal monthlySetup) {
        this.monthlySetup = monthlySetup;
    }

    public BigDecimal getMonthlyRate() {
        return monthlyRate;
    }

    public void setMonthlyRate(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }

    public String getPostPaidBaseRate() {
        return postPaidBaseRate;
    }

    public void setPostPaidBaseRate(String postPaidBaseRate) {
        this.postPaidBaseRate = postPaidBaseRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
