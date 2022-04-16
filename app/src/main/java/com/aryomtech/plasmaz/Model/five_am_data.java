package com.aryomtech.plasmaz.Model;

public class five_am_data {

    String name;
    String dp;
    Long points;
    String legend;
    String uid;
    String streak;
    String liner;

    public five_am_data(String name, String dp, Long points, String legend, String uid,String liner,String streak) {
        this.name = name;
        this.dp = dp;
        this.points = points;
        this.legend = legend;
        this.uid = uid;
        this.liner=liner;
        this.streak=streak;
    }

    public five_am_data() {
    }

    public String getStreak() {
        return streak;
    }

    public void setStreak(String streak) {
        this.streak = streak;
    }

    public String getLiner() {
        return liner;
    }

    public void setLiner(String liner) {
        this.liner = liner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
