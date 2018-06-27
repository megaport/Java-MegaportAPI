package com.megaport.api.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AvailableProductDto implements Serializable {

    private Boolean MCR;
    private List<Integer> MEGAPORT = new ArrayList<>();

    public AvailableProductDto(Boolean MCR, List<Integer> MEGAPORT) {
        this.MCR = MCR;
        this.MEGAPORT = MEGAPORT;
    }

    public AvailableProductDto(Boolean MCR, Integer ... speeds) {
        this.MCR = MCR;
        this.MEGAPORT = Arrays.asList(speeds);
    }

    public Boolean getMCR() {
        return MCR;
    }

    public void setMCR(Boolean MCR) {
        this.MCR = MCR;
    }

    public List<Integer> getMEGAPORT() {
        return MEGAPORT;
    }

    public void setMEGAPORT(List<Integer> MEGAPORT) {
        this.MEGAPORT = MEGAPORT;
    }
}
