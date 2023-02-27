package ui;

import model.Detail;
import model.Timer;
import model.Subject;

import java.util.Scanner;
import java.util.ArrayList;

//Main UI interface for my application
public class TimerApp {

    private static boolean keepGoing = true;
    private static ArrayList<Subject> subjects;
    private static ArrayList<Subject> completedSubjects;
    private static Timer timer;
    Scanner input;

    //Constructor, starts the loop for user commands
    public TimerApp() {
        subjects = new ArrayList<>();
        completedSubjects = new ArrayList<>();
        input = new Scanner(System.in);
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
        if (timer == null) {
            System.out.println("press s to start a timer");
        } else {
            System.out.println("press c to cancel the timer");
        }
        System.out.println("press m to manage subjects of focus");
        System.out.println("press q to quit");
    }

    // modifies: this
    // effects: processes the first set of user commands
    private void processCommand(String command) {
        if (command.equals("s") && timer == null) {
            System.out.println("Enter the duration, in seconds, of the timer");
            startTimer(input.nextInt());
        } else if (command.equals("c") && timer != null) {
            timer.setRemainingTime(1);
            timer.stop();
        } else if (command.equals("m")) {
            displaySubjectListMenu();
            manageSubjects(input.next());
        } else if (command.equals("q")) {
            System.out.println("Goodbye!");
            timer.stop();
            keepGoing = false;
        } else {
            System.out.println("Unknown Command");
        }

        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // modifies: this
    // effects: starts a timer, then a new thread to accept user commands again
    private void startTimer(int time) {
        if (time > 0) {
            Runnable r = () -> {
                TimerApp.timer = new Timer(time);
                timer.start(subjects, completedSubjects);
            };
            new Thread(r).start();
        } else {
            System.out.println("A negative timer duration makes no sense. Try Again.");
        }
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
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void manageSubjects(String command) {
        String description;
        int remainingTime;
        if (command.equals("a")) {
            System.out.println("what is the description of your subject?");
            description = input.next();

            System.out.println("How much time (in seconds) will you spend on " + description + "?");
            remainingTime = input.nextInt();

            subjects.add(new Subject(description, remainingTime));
        } else if (command.equals("d")) {
            System.out.println("what is the index of the subject that will be deleted?");
            subjects.remove(input.nextInt());
        } else if (command.equals("e")) {
            System.out.println("what is the index of the subject that will be edited?");
            Subject s = subjects.get(input.nextInt());
            displaySubjectMenu();
            manageSubject(input.next(), s);
        } else if (command.equals("v")) {
            System.out.println();
            for (Subject s : subjects) {
                s.printSubject();
            }
            if (subjects.isEmpty()) {
                System.out.println("Subjects all done!\n");
            }
        } else if (command.equals("p")) {
            System.out.println();
            for (Subject s : completedSubjects) {
                s.printSubject();
            }
            if (completedSubjects.isEmpty()) {
                System.out.println("\nNothing to see here!");
            }
        } else {
            System.out.println("Unknown Command");
        }

    }

    //effects: a third set of menus after the user pressed "m", "e" to edit a subject
    private void displaySubjectMenu() {
        System.out.println("press n to change the name of a subject");
        System.out.println("press t to change the remaining time of a Subject");
        System.out.println("press a to add time to a subject");
        System.out.println("press d to add a description to a subject");
    }

    //processes this third set of commands
    private void manageSubject(String command, Subject s) {
        switch (command) {
            case "n":
                System.out.println("What is the new subject name?");
                s.setDescription(input.next());
                break;
            case "t":
                System.out.println("What is the new subject time?");
                s.setDescription(input.next());
                break;
            case "a":
                System.out.println("How many seconds would you like to add?");
                s.addSeconds(input.nextInt());
                break;
            case "d":
                System.out.println("What description would you like to add?");
                s.addDetails(new Detail(input.next()));
                break;
            default:
                System.out.println("Unknown Command");
                break;
        }
    }


}