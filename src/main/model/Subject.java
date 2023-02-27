package model;

import java.util.ArrayList;

/*
A class that contains details pertaining to a specific subject of focus, including a descriptor, details,
 and time allotted */
public class Subject {
    private String description;
    private ArrayList<Detail> details;
    private int secondsRemaining;

    //modifies: this
    //effects: constructs a subject
    public Subject(String description, int secondsRemaining) {
        this.description = description;
        this.details = new ArrayList<>();
        this.secondsRemaining = Math.max(secondsRemaining, 0);
    }

    //modifies: this
    //effects: sets the description of a subject
    public void setDescription(String description) {
        this.description = description;
    }

    //effects: gets the description of a subject
    public String getDescription() {
        return description;
    }

    //effects: returns the list of details within the subject
    public ArrayList<Detail> getDetails() {
        return details;
    }

    //modifies: this
    //effects: adds a detail to the details arrayList
    public void addDetails(Detail d) {
        this.details.add(d);
//        this.secondsRemaining += d.getSecondsRemaining();
    }

    //effects: returns the seconds in the subject remaining
    public int getSecondsRemaining() {
        return this.secondsRemaining;
    }

    //requires: seconds > 0
    //modifies: this
    //effects: adds seconds to the remaining seconds
    public void addSeconds(int seconds) {
        this.secondsRemaining += seconds;
    }

    //modifies: this
    //effects: increments seconds remaining by -1
    public void countDown() {
        this.secondsRemaining--;
    }
}
