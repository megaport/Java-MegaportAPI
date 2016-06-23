package com.megaport.api.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chris.zhao on 5/05/15.
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        try {
            Long dateInMillisecond = jp.getLongValue();
            return new Date(dateInMillisecond);
        } catch (Exception e) {
            try {
                return new SimpleDateFormat("dd/MMM/yy").parse(jp.getText());
            } catch (ParseException e1) {
               return null;
            }
        }
    }
}
