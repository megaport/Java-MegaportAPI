package com.megaport.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvailableProductDto implements Serializable {

    private Boolean mcr;
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

    public void setMegaport(List<Integer> megaport) {
        this.megaport = megaport;
    }
}
