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

    private String modID;
    private String actType;
    private int actID;

    //Assignment variables
    private String assignmentTitle;
    private LocalDate assignmentDueDate; //This includes the Due Time According to How Date works
    private LocalTime assignmentDueTime;
    private int assignmentStatus;

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
    private int isDuplicate;
    private String typeOfLecture;
    private LocalDate startDate;
    private LocalDate endDate;

    //Constructor for Assignment Activity
    public Activity(String modID, String actType, String assignmentTitle, LocalDate assignmentDueDate, LocalTime assignmentDueTime, int assignmentStatus) {
        this.modID = modID;
        this.actType = actType;
        this.assignmentTitle = assignmentTitle;
        this.assignmentDueDate = assignmentDueDate;
        this.assignmentDueTime = assignmentDueTime;
        this.assignmentStatus = assignmentStatus;
    }

    //Constructor for Test Activity
    public Activity(String modID, String actType, String testDescriiption, LocalDate testDate, LocalTime testTime, String testVenue) {
        this.modID = modID;
        this.actType = actType;
        this.testDescriiption = testDescriiption;
        this.testDate = testDate;
        this.testTime = testTime;
        this.testVenue = testVenue;
    }

    //constructor for Lecture Activity
    public Activity(String modID, String actType, String lectureVenue, LocalTime lecStartTime, String dayOfWeek, int isDuplicate, String typeOfLecture, LocalDate startDate, LocalDate endDate) {
        this.modID = modID;
        this.actType = actType;
        this.lectureVenue = lectureVenue;
        this.lecStartTime = lecStartTime;
        this.dayOfWeek = dayOfWeek;
        this.isDuplicate = isDuplicate;
        this.typeOfLecture = typeOfLecture;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //GETTERS & SETTERS
    public String getModID() {
        return modID;
    }

    public String getActType() {
        return actType;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public LocalDate getAssignmentDueDate() {
        return assignmentDueDate;
    }

    public int getAssignmentStatus() {
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

    public int getIsDuplicate() {
        return isDuplicate;
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

    public void setAssignmentStatus(int assignmentStatus) {
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

    public String getAssignmentDueDateString(){
        String dueDate = getAssignmentDueDate().toString();
        String[] temp = dueDate.split("-");
        dueDate = temp[2] + "-" + temp[1] + "-" + temp[0];
        return dueDate;
    }

    public String getTestDateString(){
        String dueDate = getTestDate().toString();
        String[] temp = dueDate.split("-");
        dueDate = temp[2] + "-" + temp[1] + "-" + temp[0];
        return dueDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getStartDateString()
    {
        String startDate = getStartDate().toString();
        String[] temp = startDate.split("-");
        startDate = temp[2] + "-" + temp[1] + "-" + temp[0];
        return startDate;
    }

    public String getEndDateString()
    {
        String endDate = getEndDate().toString();
        String[] temp = endDate.split("-");
        endDate = temp[2] + "-" + temp[1] + "-" + temp[0];
        return endDate;
    }

    public LocalTime getTestTime() {
        return testTime;
    }

    public int getActID() {
        return actID;
    }

    public void setActID(int actID) {
        this.actID = actID;
    }

    public String getTypeOfLecture() {
        return typeOfLecture;
    }
}


