package model;

import org.json.JSONObject;
import persistence.Writable;

//Class that works as a descriptor for subject //TODO: finish time implementation of Detail
public class Detail implements Writable {
    private String description;
//    private int secondsRemaining;

    public Detail(String description) {
        this.description = description;
//        secondsRemaining = 0;
    }

/*    public Detail(String description, int secondsRemaining) {
        this.description = description;
        this.secondsRemaining = secondsRemaining;
    }

    public int getSecondsRemaining() {
        return this.secondsRemaining;
    }*/ //TODO: Implement this

    public String getDescription() {
        return this.description;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        return json;
    }


    /* public void printDetail() {
        Timer.printSeconds(this.secondsRemaining);
    }*/
}
