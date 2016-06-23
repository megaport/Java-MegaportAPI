package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class GraphDto implements Serializable {

    private Integer width;
    private Date start;
    private Date end;
    private String name;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Double> in_mbps = new ArrayList<>();
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Double> out_mbps = new ArrayList<>();

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getIn_mbps() {
        return in_mbps;
    }

    public void setIn_mbps(List<Double> in_mbps) {
        this.in_mbps = in_mbps;
    }

    public List<Double> getOut_mbps() {
        return out_mbps;
    }

    public void setOut_mbps(List<Double> out_mbps) {
        this.out_mbps = out_mbps;
    }
}
