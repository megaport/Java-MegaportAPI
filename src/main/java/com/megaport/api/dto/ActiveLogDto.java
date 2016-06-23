package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by leo.na on 7/12/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActiveLogDto implements Serializable, Comparable<ActiveLogDto> {

    private Integer count;
    private String event;
    private String message;
    private Integer resource_id;
    private String resource_type;
    private Integer service_id;
    private Date time;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int compareTo(ActiveLogDto other) {

        if (this.time == null || other.time == null){
            return 0;
        } else {
            return this.time.compareTo(other.time);
        }

    }
}
