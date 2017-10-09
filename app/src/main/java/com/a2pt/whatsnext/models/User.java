package com.a2pt.whatsnext.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Carl on 2017-08-19.
 */

public class User implements Serializable{



    private String userID;
    private String userName; //We need to add User Name and Surname so we can Display it on the Nav Header
    private String userEmail;
    private String userPassword;
    private String userType;
    private String courseInfo; //we need to add this, as this will be either "BSc Computer Science" for Students or "WRAP302, WRAP301, WRA301" for lecturers
    private String[] modules;
    private String moduleString;


    public User(String userID, String userName, String userEmail, String userPassword, String userType, String courseInfo, String moduleInfo) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userType = userType;
        this.courseInfo = courseInfo;
        this.moduleString = moduleInfo;
        this.modules = moduleInfo.split(",");
        this.moduleString = moduleInfo;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserType() {
        return userType;
    }

    public String getModuleString() {
        return moduleString;
    }

    public String[] getModules() {
        return modules;
    }

    public String getCourseInfo() {
        return courseInfo;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userType='" + userType + '\'' +
                ", courseInfo='" + courseInfo + '\'' +
                '}';
    }

    public String getModuleInfo()
    {
        return this.moduleString;
    }
}
