package com.a2pt.whatsnext.models;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Carl on 2017-08-19.
 */

public class Activity implements Serializable {

    public static enum Activity_Type {LECTURE, ASSIGNMENT, TEST};
    public static enum Assignment_Status {PENDING, COMPLETED};
    public static enum Day_Of_Week {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY};

    private String modID;
    private Activity_Type actType;

    //Assignment variables
    private String assignmentTitle;
    private LocalDate assignmentDueDate; //This includes the Due Time According to How Date works
    private LocalTime assignmentDueTime;
    private Assignment_Status assignmentStatus;

    //test variiables
    private String testDescriiption; //We might have left this out ?
    private LocalDate testDate; //This includes Date and Time
    private LocalTime testTime;
    private String testVenue;

    //Lecture Variables
    private String lectureVenue;
    private int sessionID; //From that Session Table
    private LocalTime lecStartTime;
    private String dayOfWeek;
    private boolean isDuplicate;

    //Constructor for Assignment Activity
    public Activity(String modID, Activity_Type actType, String assignmentTitle, LocalDate assignmentDueDate, LocalTime assignmentDueTime, Assignment_Status assignmentStatus) {
        this.modID = modID;
        this.actType = actType;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDueDate = assignmentDueDate;
        this.assignmentDueTime = assignmentDueTime;
        this.assignmentStatus = assignmentStatus;
    }

    //Constructor for Test Activity
    public Activity(String modID, Activity_Type actType, String testDescriiption, LocalDate testDate, LocalTime testTime, String testVenue) {
        this.modID = modID;
        this.actType = actType;
        this.testDescriiption = testDescriiption;
        this.testDate = testDate;
        this.testTime = testTime;
        this.testVenue = testVenue;
    }

    //constructor for Lecture Activity
    public Activity(String modID, Activity_Type actType, String lectureVenue, LocalTime lecStartTime, boolean isDuplicate) {
        this.modID = modID;
        this.actType = actType;
        this.lectureVenue = lectureVenue;
        this.lecStartTime = lecStartTime;
        this.isDuplicate = isDuplicate;
    }

    //GETTERS & SETTERS
    public String getModID() {
        return modID;
    }

    public Activity_Type getActType() {
        return actType;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public LocalDate getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public Assignment_Status getAssignmentStatus() {
        return assignmentStatus;
    }

    public String getTestDescriiption() {
        return testDescriiption;
    }

    public LocalDate getTestDate() {
        return testDate;
    }

    public String getTestVenue() {
        return testVenue;
    }

    public String getLectureVenue() {
        return lectureVenue;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public void setAssignmentDueDate(LocalDate assignmentDueDate) {
        this.assignmentDueDate = assignmentDueDate;
    }

    public void setAssignmentStatus(Assignment_Status assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public void setTestDescriiption(String testDescriiption) {
        this.testDescriiption = testDescriiption;
    }

    public void setTestDate(LocalDate testDate) {
        this.testDate = testDate;
    }

    public void setTestVenue(String testVenue) {
        this.testVenue = testVenue;
    }

    public LocalTime getLecStartTime() {
        return lecStartTime;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public LocalTime getAssignmentDueTime() {
        return assignmentDueTime;
    }

    public LocalTime getTestTime() {
        return testTime;
    }
}


