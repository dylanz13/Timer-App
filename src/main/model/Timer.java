package model;

import ui.TimerApp;

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
        startTime = System.currentTimeMillis();
        this.prevSec = 0;
    }

    /*effects: loops until remaining time is <= 0, incrementing the remaining seconds by -1,
    it also increments subject remaining times when it runs*/
    public void start(ArrayList<Subject> s, ArrayList<Subject> cs) {
        while (this.remainingTime >= 0) {

            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;

            if (prevSec != elapsedSeconds) {
                System.out.print(TimerApp.formatSeconds(this.remainingTime));

                System.out.println();
                this.remainingTime--;
                this.prevSec = (int) elapsedSeconds;
                updateSubjects(s, cs);
            }

            if (cancel) {
                break;
            }
        }
        if (this.remainingTime <= 0) {
            System.out.println("Your Timer is Done!");
            //TODO: Delve into this rabbit hole
            this.cancel = true;
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

    //effects: returns the time remaining in seconds
    public long getRemainingTime() {
        return remainingTime;
    }

    //modifies: this
    //effects: stops the timer by breaking the loop initiated by start()
    public void stop() {
        this.cancel = true;
    }

    //effects: returns true if the timer is currently running
    public boolean isRunning() {
        return (getRemainingTime() <= 1);
    }

    //modifies: this
    //effects: sets remaining time to a specified value
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}