package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Objects;

/*
A class that contains details pertaining to a specific subject of focus, including a descriptor, details,
 and time allotted */
public class Subject implements Writable {
    private String description;
    private int secondsRemaining;
    private int secondsDone;

    //modifies: this
    //effects: constructs a subject
    public Subject(String description, int secondsRemaining) {
        this.description = description;
        this.secondsRemaining = Math.max(secondsRemaining, 0);
        this.secondsDone = 0;
    }

    //modifies: this
    //effects: constructs a subject
    public Subject(String description, int secondsRemaining, int secsElapsed) {
        this.description = description;
        this.secondsRemaining = Math.max(secondsRemaining, 0);
        this.secondsDone = secsElapsed;
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

    //effects: returns the seconds already worked on a subject of focus
    public int getSecondsDone() {
        return this.secondsDone;
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

    //requires: seconds > 0
    //modifies: this
    //effects: sets seconds to the remaining seconds
    public void setSecondsRemaining(int secondsRemaining) {
        this.secondsRemaining = secondsRemaining;
    }

    //modifies: this
    //effects: increments seconds remaining by -1
    public void countDown() {
        this.secondsRemaining--;
        this.secondsDone++;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", description);
        json.put("time left", secondsRemaining);
        json.put("time elapsed", secondsDone);
        return json;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subject)) return false;
        Subject subject = (Subject) o;
        return Objects.equals(description, subject.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, secondsRemaining, secondsDone);
    }
}
