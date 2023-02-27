package model;

import java.util.ArrayList;

//A timer class that increments in seconds, also incrementing a subject's remaining time by -1 every second
public class Timer {

    private boolean cancel;
    private long remainingTime;
    private final long startTime;
    private int prevSec;

    //modifies: this
    /* effects: constructs remaining time to inputted integer time and sets up
             necessary fields to their appropriate values */
    public Timer(int time) {
        this.remainingTime = time;
        System.out.println("Time Starts Now!");
        startTime = System.currentTimeMillis();
        this.prevSec = 0;
        this.cancel = false;
    }

    /*effects: loops until remaining time is <= 0, incrementing the remaining seconds by -1,
    it also increments subject remaining times when it runs*/
    public void start(ArrayList<Subject> s, ArrayList<Subject> cs) {
        while (this.remainingTime >= 0) {

            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;

            if (prevSec != elapsedSeconds) {
                printSeconds(this.remainingTime);

                System.out.println();
                this.remainingTime--;
                this.prevSec = (int) elapsedSeconds;
                updateSubjects(s, cs);
            }

            if (cancel) {
                System.out.println("Timer Cancelled!");
                break;
            }
        }
        if (this.remainingTime <= 0) {
            System.out.println("Your Timer is Done!");
            //TODO: Delve into this rabbit hole
            System.out.println("(bug: you have to input anything to reset the scanner)");
        }
    }

    //modifies: TimerApp.subjects and TimerApp.completedSubjects
    //effects: removes subject from subjects if their remaining time is <= 0, adding it to completedSubjects
    private void updateSubjects(ArrayList<Subject> s, ArrayList<Subject> cs) {
        if (s.size() >= 1 && remainingTime != 0) {
            s.get(0).countDown();
            if (s.get(0).getSecondsRemaining() <= 0) {
                cs.add(s.get(0));
                s.remove(0);
            }
        }
    }

    //effects: a print function that formats and prints seconds into hh:mm:ss
    public static void printSeconds(long time) {
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

        for (int i = 0; i < output.size() - 1; i++) {
            System.out.print(output.get(i) + ":");
        }
        System.out.printf(format, output.get(output.size() - 1));
    }

    //effects: returns the time remaining in seconds
    public long getRemainingTime() {
        return remainingTime;
    }

    //modifies: this
    //effects: stops the timer by breaking the loop initiated by start()
    public void stop() {
        this.cancel = true;
    }

    //modifies: this
    //effects: sets remaining time to a specified value
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}