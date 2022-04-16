package com.aryomtech.plasmaz.Model;

public class TimeData {


    public  String text;
    public  String date;
    public  String key;

    public TimeData() {
    }

    public TimeData(String text, String date, String key) {
        this.text = text;
        this.date = date;
        this.key = key;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}

