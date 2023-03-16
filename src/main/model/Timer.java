package model;

import ui.TimerApp;

import java.util.ArrayList;

//A timer class that increments in seconds, also incrementing a subject's remaining time by -1 every second
public class Timer {

    private long remainingTime;
    private final long startTime;
    private int prevSec;
    private boolean isRunning;

    //modifies: this
    /* effects: constructs remaining time to inputted integer time and sets up
             necessary fields to their appropriate values */
    public Timer(int time) {
        this.remainingTime = time;
        this.isRunning = false;
        startTime = System.currentTimeMillis();
        this.prevSec = 0;
    }

    /*effects: loops until remaining time is <= 0, incrementing the remaining seconds by -1,
    it also increments subject remaining times when it runs*/
    public void start(ArrayList<Subject> s, ArrayList<Subject> cs) {
        this.isRunning = true;
        while (this.remainingTime >= 0 && isRunning) {

            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;

            if (prevSec != elapsedSeconds) {
                System.out.print(TimerApp.formatSeconds(this.remainingTime));

                System.out.println();
                this.remainingTime--;
                this.prevSec = (int) elapsedSeconds;
                updateSubjects(s, cs);

                if (this.remainingTime < 0) {
                    System.out.println("Your Timer is Done!");
                    //TODO: Delve into this rabbit hole
                    this.isRunning = false;
                    System.out.println("(bug: you have to input anything to reset the scanner)");
                }
            }
        }

    }

    //modifies: TimerApp.subjects and TimerApp.completedSubjects
    //effects: removes subject from subjects if their remaining time is <= 0, adding it to completedSubjects
    private void updateSubjects(ArrayList<Subject> s, ArrayList<Subject> cs) {
        if (s.size() >= 1 && this.remainingTime >= 0) {
            s.get(0).countDown();
            if (s.get(0).getSecondsRemaining() <= 0) {
                cs.add(s.get(0));
                s.remove(0);
            }
        }
    }

    //effects: returns the time remaining in seconds
    public long getRemainingTime() {
        return remainingTime;
    }

    //modifies: this
    //effects: stops the timer by breaking the loop initiated by start()
    public void stop() {
        this.isRunning = false;
    }

    //effects: returns true if the timer is currently running
    public boolean isRunning() {
        return this.isRunning;
    }

    //modifies: this
    //effects: sets remaining time to a specified value
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}