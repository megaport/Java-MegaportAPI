package com.megaport.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by pbarrientos on 14/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VxcApproval implements Serializable {
    private VxcApprovalStatus status;
    private String message;
    private String uid;
    private OrderType type;
    private String newSpeed;

    public VxcApprovalStatus getStatus() {
        return status;
    }

    public void setStatus(VxcApprovalStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public String getNewSpeed() {
        return newSpeed;
    }

    public void setNewSpeed(String newSpeed) {
        this.newSpeed = newSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VxcApproval that = (VxcApproval) o;

        return getUid().equals(that.getUid());

    }

    @Override
    public int hashCode() {
        return getUid().hashCode();
    }
}
