package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvailableProductDto implements Serializable {

    private Boolean mcr;
    private Integer mcrVersion;
    private List<Integer> megaport = new ArrayList<>();

    public AvailableProductDto() {
    }

    public Boolean getmcr() {
        return mcr;
    }

    public void setmcr(Boolean mcr) {
        this.mcr = mcr;
    }

    public List<Integer> getMegaport() {
        return megaport;
    }

    public Integer getMcrVersion() {
        return mcrVersion;
    }

    public void setMcrVersion(Integer mcrVersion) {
        this.mcrVersion = mcrVersion;
    }

    public void setMegaport(List<Integer> megaport) {
        this.megaport = megaport;
    }
}
