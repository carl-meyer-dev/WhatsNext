package com.a2pt.whatsnext.models;

import org.joda.time.LocalTime;

import java.io.Serializable;



/**
 * Created by Carl on 2017-08-19.
 */

public class Session implements Serializable {

    public static enum Day_Of_Week {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY}

    public int sessionID;
    public Day_Of_Week dayOfWeek;
    public LocalTime startTime;
    public LocalTime endTime;

    /*public Session(int sessionID) {
        sessionID = sessionID;

    }*/

    public Session(Day_Of_Week dayOfWeek, LocalTime startTime, LocalTime endTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Day_Of_Week getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Day_Of_Week dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getSessionID() {
        return sessionID;
    }
}