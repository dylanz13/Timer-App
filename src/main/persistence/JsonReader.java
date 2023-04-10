package persistence;

import model.Event;
import model.EventLog;
import model.Subject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public HashMap<String, Object> read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseSubjects(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private HashMap<String, Object> parseSubjects(JSONObject jsonObject) {
        HashMap<String, Object> tempList = new HashMap<>();
        try {
            tempList.put("incSub", toSubjectArray(jsonObject.getJSONArray("Subjects of Focus")));
            tempList.put("comSub", toSubjectArray(jsonObject.getJSONArray("Completed Subjects")));
        } catch (Exception e) {
            System.out.println("Nothing in JSON file!");
        }
        try {
            JSONObject tempTimer = jsonObject.getJSONObject("Timer");
            tempList.put("secs", tempTimer.getInt("Time Remaining"));
            tempList.put("run?", tempTimer.getBoolean("is Running?"));
        } catch (Exception e) {
            //Oh, well.
        }
        EventLog.getInstance().logEvent(new Event("Loaded Previous TimerApp State from File."));
        return tempList;
    }

    // EFFECTS: helper function that parses JSONArray to Subject Array
    private ArrayList<Subject> toSubjectArray(JSONArray jsonArray) {
        ArrayList<Subject> tempSubjectArray = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                tempSubjectArray.add(new Subject(jsonArray.getJSONObject(i).getString("name"),
                        jsonArray.getJSONObject(i).getInt("time left"),
                        jsonArray.getJSONObject(i).getInt("time elapsed")));
            }
        }
        return tempSubjectArray;
    }
}
