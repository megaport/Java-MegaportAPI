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
public enum ProductType {
    MEGAPORT("MEGAPORT"),
    IX("IX"),
    VXC("VXC"),
    CXC("CXC")
    ;

    private static final Map<String, ProductType> lookup = new HashMap<>();

    static {
        for (ProductType s : EnumSet.allOf(ProductType.class))
            lookup.put(s.getCode(), s);
    }

    private String code;

    ProductType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ProductType get(String code) {
        return lookup.get(code);
    }

}
