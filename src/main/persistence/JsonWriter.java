package persistence;

import model.Event;
import model.EventLog;
import model.Subject;
import model.Timer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private JSONObject output;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
        output = new JSONObject();
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of TimerApp subjects to output
    public void add(ArrayList<Subject> subjects, String comparator) {
        JSONArray json = new JSONArray();
        for (Subject s : subjects) {
            json.put(s.toJson());
        }
        if (comparator.equals("inc")) {
            output.put("Subjects of Focus",json);
        } else if (comparator.equals("com")) {
            output.put("Completed Subjects", json);
        }
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of Timer to output
    public void add(Timer t) {
        JSONObject json = new JSONObject();
        json.put("Time Remaining", t.getRemainingTime());
        json.put("is Running?", t.isRunning());
        output.put("Timer",json);
    }

    // EFFECTS: saves output to file
    public void write() {
        EventLog.getInstance().logEvent(new Event("Saved Current TimerApp State to File."));
        saveToFile(output.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
        output.clear();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
