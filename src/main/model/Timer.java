package model;

import ui.Draw;
import ui.SubjectManager;
import ui.TimerApp;

import java.io.IOException;
import java.util.ArrayList;

//A timer class that increments in seconds, also incrementing a subject's remaining time by -1 every second
public class Timer {

    private long remainingTime;
    private long startTime;
    private int prevSec;
    private boolean isRunning;
    private boolean isPaused;

    //modifies: this
    /* effects: constructs remaining time to inputted integer time and sets up
             necessary fields to their appropriate values */
    public Timer(int time) {
        this.remainingTime = time;
        this.isRunning = false;
        this.isPaused = false;
        startTime = System.currentTimeMillis();
        this.prevSec = 0;
    }

    /*effects: loops until remaining time is <= 0, incrementing the remaining seconds by -1,
    it also increments subject remaining times when it runs*/
    public void start(SubjectManager sm, Draw shape) {
        this.isRunning = true;
        while (this.remainingTime >= 0 && isRunning) {
            pauseLoop();

            long elapsedTime = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedTime / 1000;

            if (prevSec != elapsedSeconds) {
                this.remainingTime--;
                shape.getTimeTextField().setText(TimerApp.formatSeconds(this.remainingTime));
                shape.updateProgress();
                shape.update();
                this.prevSec = (int) elapsedSeconds;
                updateSubjects(sm.getIncSubjects(), sm.getComSubjects());

                if (this.remainingTime < 0) {
                    this.isRunning = false;
                }
            }
        }
    }

    //Effects: sleeps the current thread for as long as the user pauses the program for
    private void pauseLoop() {

        while (this.isPaused) {
            try {
                long pauseTracker = System.currentTimeMillis();
                Thread.sleep(100);
                startTime = startTime + (System.currentTimeMillis() - pauseTracker);
                if (!this.isRunning) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //modifies: this
    //effects: changes paused variable
    public void setPaused(Boolean isPaused) {
        this.isPaused = isPaused;
    }

    //modifies: TimerApp.subjects and TimerApp.completedSubjects
    //effects: removes subject from subjects if their remaining time is <= 0, adding it to completedSubjects
    private void updateSubjects(ArrayList<Subject> s, ArrayList<Subject> cs) {
        if (s.size() >= 1 && this.remainingTime >= 0) {
            s.get(0).countDown();
            TimerApp.updateSubjectsUI(s, cs);
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

    //effects: returns true if the timer is currently paused
    public boolean isPaused() {
        return this.isPaused;
    }

    //modifies: this
    //effects: sets remaining time to a specified value
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }
}