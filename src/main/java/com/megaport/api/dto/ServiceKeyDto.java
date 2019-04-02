package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by leo.na on 30/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceKeyDto implements Serializable {

    private String key;

    private String description;

    private String productUid;

    private Integer vlan;

    private Integer maxSpeed;

    private Boolean preApproved;

    private Boolean singleUse;

    private TimePeriodDto validFor;

    private Boolean expired;

    private Boolean valid;

    private String promoCode;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public Integer getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(Integer maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public Boolean getPreApproved() {
        return preApproved;
    }

    public void setPreApproved(Boolean preApproved) {
        this.preApproved = preApproved;
    }

    public Boolean getSingleUse() {
        return singleUse;
    }

    public void setSingleUse(Boolean singleUse) {
        this.singleUse = singleUse;
    }

    public TimePeriodDto getValidFor() {
        return validFor;
    }

    public void setValidFor(TimePeriodDto validFor) {
        this.validFor = validFor;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getValid() {
        return valid;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceKeyDto)) return false;
        ServiceKeyDto that = (ServiceKeyDto) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(description, that.description) &&
                Objects.equals(productUid, that.productUid) &&
                Objects.equals(vlan, that.vlan) &&
                Objects.equals(maxSpeed, that.maxSpeed) &&
                Objects.equals(preApproved, that.preApproved) &&
                Objects.equals(singleUse, that.singleUse) &&
                Objects.equals(validFor, that.validFor) &&
                Objects.equals(promoCode, that.promoCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, description, productUid, vlan, maxSpeed, preApproved, singleUse, validFor, expired, valid, promoCode);
    }
}
