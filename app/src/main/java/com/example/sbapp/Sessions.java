package com.example.sbapp;

public class Sessions {

    // variables for storing our image and name.
    private String session;
    private String time;
    private String location;

    public Sessions() {
        // empty constructor required for firebase.
    }

    // constructor for our object class.
    public Sessions(String session, String time, String location) {
        this.session = session;
        this.time = time;
        this.location = location;
    }

    // getter and setter methods
    public String getSession() {
        return session;
    }

    public void setName(String session) {
        this.session = session;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
