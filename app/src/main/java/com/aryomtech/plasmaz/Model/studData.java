package com.aryomtech.plasmaz.Model;

public class studData {

    public String name;
    public String classroom;
    public String uid;

    public studData() {
    }

    public studData(String name, String classroom,String uid) {
        this.name = name;
        this.classroom = classroom;
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}

