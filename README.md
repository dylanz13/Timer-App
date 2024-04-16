# Study Application

**An application providing the Pomodoro and Flowtime Study Techniques**

## Overview

This application is designed to help users improve their focus and productivity when tackling tasks that require sustained effort. It implements two popular study techniques:

* **Pomodoro Technique:** Breaks down work into intervals traditionally 25 minutes long, separated by short breaks.
* **Flowtime Technique:** Encourages longer periods of uninterrupted focus with customizable work and break intervals.

## Target Audience

* Students
* Professionals
* Anyone who needs to concentrate on challenging work

## Motivation

As a student, I've personally benefited from the Pomodoro and Flowtime techniques. This application aims to make these methods more accessible and convenient.

## User Stories

* As a user, I want to be able to add a subject of focus.
* As a user, I want to be able to add a subject to a list of subjects.
* As a user, I want to be able to add a time to the end of any selected subject.
* As a user, I want to be able to see the past subjects that I have completed.
* As a user, I want to be able to select a customizable timer that will count down as I work and deduct time from my assigned subject.
* As a user, I want to be able to save my subject list and current timer state to a file.
* As a user, I want to be able to load my subject list and current timer state from a file.

## Instructions for Grader

1. **Adding/Modifying Subjects:**
   * Use the "Modify" button to add subjects with a description and time allocation (format: HH:MM:SS).
   * Edit subjects using the same "Modify" button.

2. **Visual Timer**
   * Enter a time next to "Enter a Time:" to start the timer and its visual representation. 

3. **Saving/Loading State:**
   * Click "Save to File" to save the application state.
   * Click "Load from File" to restore a previous session.

## EventLog Example

Mon Apr 10 11:37:24 PDT 2023
Loaded Previous TimerApp State from File.

Mon Apr 10 11:37:29 PDT 2023
Added Subject: Math

... [Additional Events]

## Technical Notes

* **Language:** Java
* **Features:** JSON storage, timer threading

## Potential Improvements

* **Reduce Coupling:** Explore ways to decrease coupling between classes, particularly between the `TimerApp` and `Timer` classes. This may involve rethinking how UI elements are updated. 
