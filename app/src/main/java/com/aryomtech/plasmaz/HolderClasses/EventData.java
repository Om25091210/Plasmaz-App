package com.aryomtech.plasmaz.HolderClasses;


public class EventData {

    String title,subtitle,imageurl,body,website;

    public EventData(String title, String subtitle, String imageurl, String body, String website) {
        this.title = title;
        this.subtitle = subtitle;
        this.imageurl = imageurl;
        this.body = body;
        this.website = website;
    }

    public EventData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
