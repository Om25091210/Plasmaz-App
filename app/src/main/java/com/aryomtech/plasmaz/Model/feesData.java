package com.aryomtech.plasmaz.Model;

public class feesData {

    public String link;
    public String date;
    public String note;

    public feesData() {
    }

    public feesData(String link, String date, String note) {
        this.link = link;
        date = date;
        this.note = note;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
