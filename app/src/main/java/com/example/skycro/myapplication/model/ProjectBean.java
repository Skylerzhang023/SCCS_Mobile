package com.example.skycro.myapplication.model;

import java.io.Serializable;

/**
 * Created by konie on 2017/4/8.
 */

public class ProjectBean implements Serializable {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
