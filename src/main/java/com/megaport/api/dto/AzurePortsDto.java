package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo.na on 16/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AzurePortsDto implements Serializable {

    private Integer bandwidth;
    private String resource_type;
    private String service_key;
    private Integer vlan;
    private List<AzurePortDto> megaports;

    public Integer getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(Integer bandwidth) {
        this.bandwidth = bandwidth;
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

    public Integer getVlan() {
        return vlan;
    }

    public void setVlan(Integer vlan) {
        this.vlan = vlan;
    }

    public List<AzurePortDto> getMegaports() {
        return megaports;
    }

    public void setMegaports(List<AzurePortDto> megaports) {
        this.megaports = megaports;
    }

    @Override
    public String toString() {
        return "AzurePortsDto{" +
                "bandwidth=" + bandwidth +
                ", resource_type='" + resource_type + '\'' +
                ", service_key='" + service_key + '\'' +
                ", vlan=" + vlan +
                ", megaports=" + megaports +
                '}';
    }
}
