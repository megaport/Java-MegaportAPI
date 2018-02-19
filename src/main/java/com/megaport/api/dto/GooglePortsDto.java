package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GooglePortsDto implements Serializable {

    private List<Integer> bandwidths;
    private String resource_type;
    private String service_key;
    private List<GooglePortDto> megaports;

    public List<Integer> getBandwidths() {
        return bandwidths;
    }

    public void setBandwidths(List<Integer> bandwidths) {
        this.bandwidths = bandwidths;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getService_key() {
        return service_key;
    }

    public void setService_key(String service_key) {
        this.service_key = service_key;
    }

    public List<GooglePortDto> getMegaports() {
        return megaports;
    }

    public void setMegaports(List<GooglePortDto> megaports) {
        this.megaports = megaports;
    }
}
