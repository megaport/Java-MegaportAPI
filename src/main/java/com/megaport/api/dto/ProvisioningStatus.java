/*
 * Copyright (c) 2013. Adam Wells
 */

package com.megaport.api.dto;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: adam
 * Date: 28/11/12
 * Time: 8:25 AM
 * To change this template use File | Settings | File Templates.
 */
public enum ProvisioningStatus {
    NEW("NEW"),
    DESIGN("DESIGN"),
    IN_PROGRESS("IN_PROGRESS"),
    ON_HOLD("ON_HOLD"),
    DEPLOYABLE("DEPLOYABLE"),
    CONFIGURED("CONFIGURED"),
    LIVE("LIVE"),
    CANCELLED("CANCELLED"),
    CANCELLED_PARENT("CANCELLED_PARENT"),
    SLEEP("SLEEP"),
    FAILED("FAILED"),
    DECOMMISSIONED("DECOMMISSIONED");

    private static final Map<String, ProvisioningStatus> lookup = new HashMap<>();

    static {
        for (ProvisioningStatus s : EnumSet.allOf(ProvisioningStatus.class))
            lookup.put(s.getCode(), s);
    }

    private String code;

    ProvisioningStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ProvisioningStatus get(String code) {
        return lookup.get(code);
    }

}
