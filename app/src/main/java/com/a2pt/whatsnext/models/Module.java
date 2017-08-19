package com.a2pt.whatsnext.models;

import java.io.Serializable;

/**
 * Created by Carl on 2017-08-19.
 */

public class Module implements Serializable {

    private String modID;
    private String modName;

    public Module(String modID, String modName) {
        this.modID = modID;
        this.modName = modName;
    }
}
