# Study Application

### An application providing the Pomodoro and Flowtime Study Techniques

**What will the application do**?
<ul> The application will make focusing on any type of work more painless
by utilizing the pomodoro and flowtime study techniques.
</ul>

**Who will use it**?
<ul> Anybody who needs to get some work done - anything that requires time and effort - can 
appreciate the convenience this application brings.
</ul>

**Why is this project of interest to me**?

<ul> As a full-time student, I have relied heavily on both the pomodoro and flowtime studying
techniques, so I thought it would be great if to make an application that allows these methods
to become more convenient for anybody to use.
</ul>


## User Stories
<ul>
As a user, I want to be able to add a subject of focus <br>
As a user, I want to be able to add a description to any selected subject <br>
As a user, I want to be able to add a time to the end of any selected subject <br>
As a user, I want to be able to see the past subjects that I have completed<br>
As a user, I want to be able to select a time that will count down as I do my work. This timer should also deduct time from the time I have
assigned any given subject of focus, printing out an alert when this happens.<br>
As a user, I want to be able to save my subject list and current timer state to file (if I so choose) <br>
As a user, I want to be able to be able to load my subject list and current timer state from file (if I so choose) <br>
</ul>

# Instructions for Grader

- You can generate the first required action related to adding Xs to a Y by clicking the "Modify" button and adding the required 'description' and 'time' remaining (beware that the time entered must be formatted in 'HH:MM:SS)
- You can generate the second required action related to adding Xs to a Y by clicking the "Modify" button and changing the edit fields
- You can locate my visual component by entering a time in the main application, right next to "Enter a Time: ", where a timer alongside a visual representation of said timer will start.
- You can save the state of my application by clicking the "Save to File" button
- You can reload the state of my application by click the "Load to File" button

## Phase 4: Task 2
<ul>
An example of EventLog printing out in the console:

Mon Apr 10 11:37:24 PDT 2023 <br>
Loaded Previous TimerApp State from File.

Mon Apr 10 11:37:29 PDT 2023 <br>
Added Subject: Math

Mon Apr 10 11:37:41 PDT 2023 <br>
Math Description Changed to: Linguistics

Mon Apr 10 11:37:51 PDT 2023 <br>
Linguistics Time Remaining Changed From: 2:00 to: 1:50

Mon Apr 10 11:37:58 PDT 2023 <br>
Removed Subject: Linguistics

Mon Apr 10 11:38:07 PDT 2023 <br>
Started Timer for: 20

Mon Apr 10 11:38:09 PDT 2023 <br>
Paused Timer.

Mon Apr 10 11:38:14 PDT 2023 <br>
Resumed Timer.

Mon Apr 10 11:38:32 PDT 2023 <br>
Finished Timer.

Mon Apr 10 11:38:34 PDT 2023 <br>
Saved Current TimerApp State to File.

Mon Apr 10 11:38:40 PDT 2023 <br>
Started Timer for: 20

Mon Apr 10 11:38:41 PDT 2023 <br>
Cancelled Timer.
</ul>