package com.megaport.api.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adam.wells on 22/06/2016.
 */
public class JsonConverter {

    private static JsonFactory factory = new JsonFactory();
    private static ObjectMapper mapper = new ObjectMapper(factory);
    public static TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {};

    public static HashMap<String, Object> fromJson(String jsonString) {

        try {
            return mapper.readValue(new ByteArrayInputStream(jsonString.getBytes("UTF-8")), typeRef);
        } catch (IOException e) {
            return new HashMap<>();
        }

    }

    public static <T extends Object> List<T> fromJsonDataAsList(String jsonString, Class<T> typeReference) {

        List<T> result = new ArrayList<>();

        HashMap<String, Object> map = fromJson(jsonString);
        if (!map.get("data").equals("[]")){
            List<Map<String,Object>> list = (List<Map<String,Object>>) map.get("data");
            for (Map<String,Object> item : list){
                result.add(fromJson(toJson(item),typeReference));
            }
        }

        return result;

    }

    public static <T extends Object> List<T> fromJsonDataAsSimpleList(String jsonString, Class<T> typeReference) {

        List<T> result = new ArrayList<>();

        HashMap<String, Object> map = fromJson(jsonString);
        if (!map.get("data").equals("[]")){
            result = (List<T>) map.get("data");
        }

        return result;

    }



    public static List<Map<String,Object>> fromJsonDataAsList(String jsonString) {

        HashMap<String, Object> map = fromJson(jsonString);
        if (!map.get("data").equals("[]")){
            return (List<Map<String,Object>>) map.get("data");
        } else {
            return new ArrayList<>();
        }

    }

    public static HashMap<String, Object> fromJsonDataAsMap(String jsonString) {

        HashMap<String, Object> map = fromJson(jsonString);
        Map<String,Object> item = (Map<String,Object>) map.get("data");

        return fromJson(toJson(item));
    }

    public static <T extends Object> T fromJsonDataAsObject(String jsonString, Class<T> typeReference) {

        HashMap<String, Object> map = fromJson(jsonString);
        Map<String,Object> item = (Map<String,Object>) map.get("data");

        return fromJson(toJson(item),typeReference);
    }

    public static <T extends Object> T fromJson(String jsonString, Class<T> typeReference) {

        try {
            return mapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            return null;
        }

    }

    public static String toJson(Object object) {

        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }

    }

    public static String toJsonPretty(Object object) {

        if (object.getClass().equals(String.class)){
            object = fromJson((String)object);
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (IOException e) {
            return null;
        }

    }

    public static Map<String,Object> toMap(Object o){
        if (o.getClass().equals(String.class)){
            return fromJson((String) o);
        } else {
            String json = toJson(o);
            return fromJson(json);
        }
    }

}
