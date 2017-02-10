package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VxcEndDto implements Serializable {

    private String ownerUid;
    private String productUid;
    private String productName;
    private Integer locationId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String location;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer vlan;
    private Map<String, Object> partnerConfig = new HashMap<>();

    public String getOwnerUid() {
        return ownerUid;
    }

    public void setOwnerUid(String ownerUid) {
        this.ownerUid = ownerUid;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Map<String, Object> getPartnerConfig() {
        return partnerConfig;
    }

    public void setPartnerConfig(Map<String, Object> partnerConfig) {
        this.partnerConfig = partnerConfig;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation( String location ) {
        this.location = location;
    }

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan( Integer vlan ) {
        this.vlan = vlan;
    }
}
