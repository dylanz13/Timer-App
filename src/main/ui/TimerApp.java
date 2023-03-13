package ui;

import model.Detail;
import model.Timer;
import model.Subject;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

//Main UI interface for my application
public class TimerApp {

    private static final String JSON_STORE = "./data/SaveState.json";
    private boolean keepGoing = true;
    private boolean isRunning;
    private SubjectManager subjectManager;
    private static Timer timer;
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private Scanner input;

    //Constructor, starts the loop for user commands
    public TimerApp() {
        subjectManager = new SubjectManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        isRunning = false;
        while (keepGoing) {
            input.useDelimiter("\n");
            if (timer != null && timer.getRemainingTime() <= 1) {
                timer = null;
            }
            displayMenu();
            try {
                processCommand(input.next().toLowerCase());
                input.reset();
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
        input.close();
    }

    //displays the first set of user commands
    private void displayMenu() {
        if (timer == null && !isRunning) {
            System.out.println("press s to start a timer");
        } else if (timer != null && !isRunning) {
            System.out.println("press r to resume the timer");
        } else {
            System.out.println("press c to cancel the timer");
        }
        System.out.println("press m to manage subjects of focus");
        System.out.println("press sf to save current app state to file");
        System.out.println("press lf to load previous app state from file");
        System.out.println("press q to quit");
    }

    // modifies: this
    // effects: processes the first set of user commands
    private void processCommand(String command) {
        if (command.equals("s") && timer == null) {
            System.out.println("Enter the duration, in seconds, of the timer");
            startTimer(input.nextInt());
        } else if (command.equals("r") && timer != null) {
            timer.start(subjectManager.getIncSubjects(), subjectManager.getIncSubjects());
        } else if (command.equals("c") && timer != null) {
            System.out.println("Timer Cancelled!");
            timer.setRemainingTime(1);
            timer.stop();
            isRunning = false;
        } else if (command.equals("m")) {
            displaySubjectListMenu();
            manageSubjects(input.next());
        } else if (command.equals("sf")) {
            saveToFile();
        } else if (command.equals("lf")) {
            loadFromFile();
        } else if (command.equals("q")) {
            endApp();
        } else {
            System.out.println("Unknown Command");
        }
        breifPause();
    }

    private void breifPause() {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    //modifies: "SubjectsArray.json"
    //Effects: writes all current subjects to SubjectsArray.json
    private void saveToFile() {
        try {
            jsonWriter.open();
            jsonWriter.add(subjectManager.getIncSubjects(), "inc");
            jsonWriter.add(subjectManager.getComSubjects(), "com");
            if (timer != null) {
                jsonWriter.add(timer);
            }
            jsonWriter.write();
            jsonWriter.close();
            System.out.println("Saved Current Timer State to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadFromFile() {
        HashMap<String, Object> objects = new HashMap<>();
        try {
            objects = jsonReader.read();
            System.out.println("Loaded Previous Timer State from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

        subjectManager = new SubjectManager((ArrayList<Subject>) objects.get("incSub"),
                (ArrayList<Subject>) objects.get("comSub"));

        try {
            if ((boolean) objects.get("run?")) {
                resumeTimer((int) objects.get("secs"));
            } else {
                TimerApp.timer = new Timer((int) objects.get("secs"));
                System.out.println("Timer Loaded with "
                        + formatSeconds(timer.getRemainingTime()) + " remaining.");
            }
            isRunning = (boolean) objects.get("run?");
        } catch (Exception e) {
            //Oh, well.
        }
    }

    private void endApp() {
        System.out.println("Goodbye!");
        if (timer != null) {
            timer.stop();
        }
        keepGoing = false;
    }

    // modifies: this
    // effects: starts a timer, then a new thread to accept user commands again
    private void startTimer(int time) {
        if (time > 0) {
            Runnable r = () -> {
                TimerApp.timer = new Timer(time);
                System.out.println("Time Starts Now!");
                timer.start(subjectManager.getIncSubjects(), subjectManager.getIncSubjects());
                isRunning = false;
            };
            new Thread(r).start();
        } else {
            System.out.println("A negative timer duration makes no sense. Try Again.");
        }
    }

    // modifies: this
    // effects: starts a timer, then a new thread to accept user commands again
    private void resumeTimer(int time) {
        Runnable r = () -> {
            TimerApp.timer = new Timer(time);
            System.out.println("Time Resumes Now!");
            timer.start(subjectManager.getIncSubjects(), subjectManager.getIncSubjects());
            isRunning = false;
        };
        new Thread(r).start();
    }

    //effects: display a second set of commands after the user pressed "m"
    private void displaySubjectListMenu() {
        System.out.println("press a to add a subject of focus");
        System.out.println("press d to delete subjects of focus");
        System.out.println("press e to edit a select subject of focus");
        System.out.println("press v to view current subjects of focus");
        System.out.println("press p to view previously completed subjects of focus");
    }

    //modifies: this
    //effects: processes the second set of user commands after "m" was pressed
    private void manageSubjects(String command) {
        switch (command) {
            case "a":
                addSubject();
                break;
            case "d":
                deleteSubject();
                break;
            case "e":
                editSubject();
                break;
            case "v":
                viewIncompleteSubjects();
                break;
            case "p":
                viewCompleteSubjects();
                break;
            default:
                System.out.println("Unknown Command");
                break;
        }
    }

    private void addSubject() {
        System.out.println("what is the description of your subject?");
        String description = input.next();

        System.out.println("How much time (in seconds) will you spend on " + description + "?");
        int remainingTime = input.nextInt();

        subjectManager.addSubject(new Subject(description, remainingTime));
    }

    private void deleteSubject() {
        System.out.println("what is the description of the subject that will be deleted?");
        String description = input.next();
        if (subjectManager.hasSubject(description)) {
            subjectManager.removeSubject(description);
        } else {
            System.out.println("No such Subject");
        }
    }

    private void editSubject() {
        System.out.println("what is the description of the subject that will be edited?");
        String description = input.next();
        if (subjectManager.hasSubject(description)) {
            Subject s = subjectManager.getSubject(description);
            displaySubjectMenu();
            manageSubject(input.next(), s);
        } else {
            System.out.println("No such Subject");
        }
    }

    private void viewIncompleteSubjects() {
        System.out.println();
        for (Subject s : subjectManager.getIncSubjects()) {
            printSubject(s);
        }
        if (subjectManager.getIncSubjects().isEmpty()) {
            System.out.println("No Subjects Left!\n");
        }
    }

    private void viewCompleteSubjects() {
        System.out.println();
        for (Subject s : subjectManager.getComSubjects()) {
            printSubject(s);
        }
        if (subjectManager.getComSubjects().isEmpty()) {
            System.out.println("Nothing to see here!\n");
        }
    }

    //effects: prints out the subject, and the allotted time given to said subject
    private void printSubject(Subject s) { //TODO: re-write into a toString() method
        System.out.println("Subject: " + s.getDescription() + ",");
        System.out.print("Time: ");
        System.out.print(formatSeconds(s.getSecondsRemaining()));
        System.out.println(".");
        if (s.getDetails().size() != 0) {
            System.out.println(s.getDescription() + " Details:");
            for (int i = 0; i < s.getDetails().size() - 1; i++) {
                System.out.println(" " + s.getDetails().get(i).getDescription() + ",");
            }
            System.out.println(" " + s.getDetails().get(s.getDetails().size() - 1).getDescription() + ".");
        }
        System.out.println();
    }

    //effects: a third set of menus after the user pressed "m", "e" to edit a subject
    private void displaySubjectMenu() {
        System.out.println("press n to change the name of a subject");
        System.out.println("press t to change the remaining time of a Subject");
        System.out.println("press a to add a description to a subject");
    }

    //processes this third set of commands
    private void manageSubject(String command, Subject s) {
        switch (command) {
            case "n":
                System.out.println("What is the new subject name?");
                s.setDescription(input.next());
                break;
            case "t":
                System.out.println("What is the new subject time? (Current Time: "
                        + formatSeconds(s.getSecondsRemaining()) + ")");
                s.setSecondsRemaining(input.nextInt());
                break;
            case "a":
                System.out.println("What description would you like to add?");
                s.addDetails(new Detail(input.next()));
                break;
            default:
                System.out.println("Unknown Command");
                break;
        }
    }

    //effects: a print function that formats and prints seconds into hh:mm:ss
    public static String formatSeconds(long time) {
        StringBuilder returnStr = new StringBuilder();
        String format = "%02d";
        ArrayList<Long> output = new ArrayList<>();

        long remainingHours = time / 3600;
        long remainingMinutes = (time - remainingHours * 3600) / 60;
        long remainingSeconds = (time - remainingHours * 3600 - remainingMinutes * 60);

        if (remainingHours > 0) {
            output.add(remainingHours);
            output.add(remainingMinutes);
        } else if (remainingMinutes > 0) {
            output.add(remainingMinutes);
        }
        output.add(remainingSeconds);

        int i = 0;
        if (output.size() == 2) {
            returnStr.append(output.get(0)).append(":");
            i = 1;
        } else if (output.size() == 3) {
            returnStr.append(output.get(0)).append(":");
            returnStr.append(String.format(format, output.get(1))).append(":");
            i = 2;
        }
        return returnStr.append(String.format(format, output.get(i))).toString();
    }
}