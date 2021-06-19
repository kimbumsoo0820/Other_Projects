package com.example.healthtimer;

class Event_spinner_info {
    private String events;
    private String publisher;


    public  Event_spinner_info(String events, String publisher) {
        this.events = events;
        this.publisher = publisher;
    }

    public String getEvents() {
        return this.events;
    }
    public void setEvents(String events){
        this.events = events;
    }
    public String publisher() {
        return this.publisher;
    }
    public void publisher(String publisher){
        this.publisher = publisher;
    }
}
