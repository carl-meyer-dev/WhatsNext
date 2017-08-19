package com.a2pt.whatsnext.models;

import java.io.Serializable;

/**
 * Created by Carl on 2017-08-19.
 */

public class Teaches implements Serializable{

    private int sessionID;
    private String userID;
    private String modID;

    public Teaches(int sessionID, String userID, String modID) {
        this.sessionID = sessionID;
        this.userID = userID;
        this.modID = modID;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getModID() {
        return modID;
    }

    public void setModID(String modID) {
        this.modID = modID;
    }
}
