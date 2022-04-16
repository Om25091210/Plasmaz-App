package com.aryomtech.plasmaz.Model;

public class AssignmentData {

    public  String subject;
    public  String chapter;
    public  String date;
    public  String link;
    public  String teacher;

    public AssignmentData() {
    }

    public AssignmentData(String subject, String chapter, String date, String link, String teacher) {
        this.subject = subject;
        this.chapter = chapter;
        this.date = date;
        this.link = link;
        this.teacher = teacher;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
