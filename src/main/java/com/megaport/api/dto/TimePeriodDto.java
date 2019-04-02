/*
 * Copyright (c) 2013. Adam Wells
 */

package com.megaport.api.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 * User: adam
 * Date: 27/11/12
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimePeriodDto implements Serializable {

    private Date start;
    private Date end;

    public TimePeriodDto(Date start, Date end){
        this.start = start;
        this.end = end;
    }

    public TimePeriodDto(){}

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

    public String toString( SimpleDateFormat sdf ) {
        if ( start == null || end == null )
            return "Invalid TimePeriod: null start or end date";

        return "from " + sdf.format( start ) + " to " + sdf.format( end );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TimePeriodDto)) return false;
        TimePeriodDto that = (TimePeriodDto) o;
        return Objects.equals(start, that.start) &&
                Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
