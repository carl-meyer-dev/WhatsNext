package com.a2pt.whatsnext.models;

import org.joda.time.LocalTime;

import java.io.Serializable;



/**
 * Created by Carl on 2017-08-19.
 */

public class Session implements Serializable {

    public static enum Day_Of_Week {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}

    ;

    public int sessionID;
    public Day_Of_Week dayOfWeek;
    public LocalTime startTime;
    public LocalTime endTime;

    public Session(int sessionID) {
        sessionID = sessionID;


    }
}