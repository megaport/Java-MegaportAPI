package com.megaport.api.dto;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by michael.stack on 26/08/2014.
 */
public enum OrderType {

    NEW("NEW"), SPEED_CHANGE("SPEED_CHANGE");

    private static final Map<String, OrderType> lookup = new HashMap<>();

    static {
        for (OrderType s : EnumSet.allOf(OrderType.class))
            lookup.put(s.getCode(), s);
    }

    private String code;

    OrderType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static OrderType get(String code) {
        return lookup.get(code);
    }

}
