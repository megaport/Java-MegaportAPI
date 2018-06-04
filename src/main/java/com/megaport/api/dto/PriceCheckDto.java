package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by adam on 23/12/2013.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class PriceCheckDto implements Serializable {

    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal delta = BigDecimal.ZERO;
    private BigDecimal longTermMonthly = BigDecimal.ZERO;
    private String currency = "";
    private ForceProductChange forceProductChange = null;

    public PriceCheckDto(){}

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getDelta() {
        return delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    public BigDecimal getLongTermMonthly() {
        return longTermMonthly;
    }

    public void setLongTermMonthly(BigDecimal longTermMonthly) {
        this.longTermMonthly = longTermMonthly;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ForceProductChange getForceProductChange() {
        return forceProductChange;
    }

    public void setForceProductChange(ForceProductChange forceProductChange) {
        this.forceProductChange = forceProductChange;
    }

    @Override
    public String toString() {
        return "PriceCheckDto{" +
                "total=" + total +
                ", delta=" + delta +
                ", longTermMonthly=" + longTermMonthly +
                ", currency='" + currency + '\'' +
                ", forceProductChange=" + forceProductChange +
                '}';
    }
}
