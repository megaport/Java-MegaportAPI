package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AzurePortDto implements Serializable {

    private String type;
    private Integer vxc;
    private String productUid;
    private String name;
    private String description;
    private Integer portSpeed;
    private Integer locationId;
    private String state;
    private String country;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getVxc() {
        return vxc;
    }

    public void setVxc(Integer vxc) {
        this.vxc = vxc;
    }

    public String getProductUid() {
        return productUid;
    }

    public void setProductUid(String productUid) {
        this.productUid = productUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPortSpeed() {
        return portSpeed;
    }

    public void setPortSpeed(Integer portSpeed) {
        this.portSpeed = portSpeed;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "AzurePortDto{" +
                "type='" + type + '\'' +
                ", vxc=" + vxc +
                ", productUid='" + productUid + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", portSpeed=" + portSpeed +
                ", locationId=" + locationId +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
