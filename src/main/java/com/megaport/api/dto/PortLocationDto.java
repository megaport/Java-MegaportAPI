package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PortLocationDto implements Serializable {

    private String market;
    private String country;
    private String networkRegion;
    private Address address;
    private String campus;
    private String metro;
    private String name;
    private Integer id;
    private Boolean sdrcAvailable = false;
    private Boolean vRouterAvailable = false;

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getNetworkRegion() {
        return networkRegion;
    }

    public void setNetworkRegion(String networkRegion) {
        this.networkRegion = networkRegion;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public Boolean getSdrcAvailable() {
        return sdrcAvailable;
    }

    public void setSdrcAvailable(Boolean sdrcAvailable) {
        this.sdrcAvailable = sdrcAvailable;
    }

    public String getMetro() {
        return metro;
    }

    public void setMetro(String metro) {
        this.metro = metro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getvRouterAvailable() {
        return vRouterAvailable;
    }

    public void setvRouterAvailable(Boolean vRouterAvailable) {
        this.vRouterAvailable = vRouterAvailable;
    }
}
