package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

/*
A class that contains details pertaining to a specific subject of focus, including a descriptor, details,
 and time allotted */
public class Subject implements Writable {
    private String description;
    private ArrayList<Detail> details;
    private int secondsRemaining;
    private int secondsDone;

    //modifies: this
    //effects: constructs a subject
    public Subject(String description, int secondsRemaining) {
        this.description = description;
        this.details = new ArrayList<>();
        this.secondsRemaining = Math.max(secondsRemaining, 0);
        this.secondsDone = 0;
    }

    //modifies: this
    //effects: constructs a subject
    public Subject(String description, ArrayList<Detail> details, int secondsRemaining, int secsElapsed) {
        this.description = description;
        this.details = details;
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

    //effects: returns the list of details within the subject
    public ArrayList<Detail> getDetails() {
        return details;
    }

    //effects: returns the seconds already worked on a subject of focus
    public int getSecondsDone() {
        return this.secondsDone;
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
        json.put("details", detailsToJson());
        json.put("time left", secondsRemaining);
        json.put("time elapsed", secondsDone);
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray detailsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Detail d : details) {
            jsonArray.put(d.toJson());
        }

        return jsonArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return Objects.equals(description, subject.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, details, secondsRemaining, secondsDone);
    }
}
