package com.example.healthtimer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.IgnoreExtraProperties;


public class Add_event  {
    private String events;
    private  String logo;

    public Add_event() {}

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }


}

