package com.megaport.api.dto;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: adam
 * Date: 12/11/2013
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */

public enum VxcDistanceBand {
    DATA_CENTRE("datacentre"),
    CAMPUS("campus"),
    METRO("metro"),
    INTRA_STATE("intra_state"),
    INTERCAP("intercap"),
    ZONE("zone"),
    INTERZONE("interzone"),
    INTERNATIONAL("international");

    private static final Map<String, VxcDistanceBand> lookup = new HashMap<>();

    static {
        for (VxcDistanceBand s : EnumSet.allOf(VxcDistanceBand.class))
            lookup.put(s.getCode(), s);
    }

    private String code;

    VxcDistanceBand(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static VxcDistanceBand get(String code) {
        return lookup.get(code);
    }
}
