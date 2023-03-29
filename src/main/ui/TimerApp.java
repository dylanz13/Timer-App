package ui;

import model.Timer;
import model.Subject;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.ArrayList;

//Main UI interface for my application
public class TimerApp extends JFrame {

    private static final String JSON_STORE = "./data/SaveState.json";
    private SubjectManager subjectManager;
    private static Timer timer;
    private final JsonReader jsonReader;
    private final JsonWriter jsonWriter;

    private Draw shape;
    private JTextField time;
    private JPanel pane;
    private JToggleButton playPause;
    private static DefaultTableModel incSubModel;
    private static DefaultTableModel comSubModel;


    //Constructor, starts the loop for user commands
    public TimerApp() {
        super("Timer App"); //JFrame Initialization
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(500, 600));
        setSize(500, 500);

        //Variable Initializations
        subjectManager = new SubjectManager();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        // Creating JPanel and Sets its Layout to GridBagLayout
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(3, 4, 3, 4);

        //Adding all the UI Elements
        addShape(c, p);
        addPlayPause(playPause);
        addTimeUI(c, p);
        addSaveLoad(c, p);
        addSubjectsUI(c, p);
        addManageSubjectsUI(c, p);

        getContentPane().add(p);
        setVisible(true);
    }

    private void setGridDetails(int x, int y, int width, int height, GridBagConstraints c) {
        c.gridx = x;
        c.gridy = y;
        c.ipadx = width;
        c.ipady = height;
    }

    private void addShape(GridBagConstraints c, JPanel p) {
        setGridDetails(0, 0, 125,125, c);
        shape = new Draw("00:00");
        p.add(shape, c);
        shape.update();
    }

    private void addPlayPause(JToggleButton playPause) {
        playPause = new JToggleButton("Pause");
        addItemListener(playPause);

        JButton cancel = new JButton("Cancel");
        JToggleButton finalPlayPause1 = playPause;
        cancel.addActionListener(e -> {
            if (timer != null) {
                timer.stop();
                finalPlayPause1.setSelected(false);
            }
        });

        pane = new JPanel();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.setSize(60,20);
        pane.add(playPause);
        pane.add(cancel);
    }

    private void addItemListener(JToggleButton pp) {
        pp.addItemListener(e -> {
            int state = e.getStateChange();

            if (state == ItemEvent.SELECTED) {
                pp.setText(" Play ");
                timer.setPaused(true);
            } else {
                pp.setText("Pause ");
                timer.setPaused(false);
            }
        });
    }

    private void addTimeUI(GridBagConstraints c, JPanel p) {
        setGridDetails(1, 0, 20, 20, c);
        time = new JTextField("HH:MM:SS");
        time.setColumns(6);
        customFocusListener(time, "HH:MM:SS ");
        addComplexTimeListener(time);
        JLabel msg = new JLabel("Enter a time: ");
        msg.setHorizontalAlignment(SwingConstants.RIGHT);
        msg.setVerticalAlignment(SwingConstants.CENTER);
        msg.setFont(new Font("Dialog", Font.BOLD, 12));
        p.add(pane, c);
        pane.setVisible(false);
        p.add(msg, c);

        setGridDetails(2, 0, 80, 20, c);
        p.add(time, c);
    }

    private void addComplexTimeListener(JTextField time) {
        time.addActionListener(e -> {
            try {
                String[] split = time.getText().split(":",3);

                int i = getTimeFromString(split);
                startTimer(i, shape);
                shape.setTotTime(i);
                shape.getTimeTextField().setText(formatSeconds(i));
                time.setText("HH:MM:SS");
                time.setVisible(false);
                pane.setVisible(true);
            } catch (Exception exc) {
                time.setText("");
            }
        });
    }

    private void addSaveLoad(GridBagConstraints c, JPanel p) {
        JButton save = new JButton("Save to File");
        save.addActionListener(e -> saveToFile());
        setGridDetails(0, 1, 20, 20, c);
        p.add(save, c);

        JButton load = new JButton("Load From File");
        load.addActionListener(e -> loadFromFile());
        setGridDetails(1, 1, 20, 20, c);
        p.add(load, c);
    }

    private void addSubjectsUI(GridBagConstraints c, JPanel p) {
        String[] incSubjectsColumns = {"description", "time remaining"};
        incSubModel = new DefaultTableModel(incSubjectsColumns, 0);
        JTable incTable = new JTable(incSubModel);
        TableColumn column = incTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(90);
        column = incTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(30);

        String[] comSubjectsColumns = {"description", "time elapsed"};
        comSubModel = new DefaultTableModel(comSubjectsColumns, 0);
        JTable comTable = new JTable(comSubModel);
        column = comTable.getColumnModel().getColumn(0);
        column.setPreferredWidth(90);
        column = comTable.getColumnModel().getColumn(1);
        column.setPreferredWidth(30);

        JTabbedPane both = new JTabbedPane();
        both.add("Subjects of Focus", (new JScrollPane(incTable)));
        both.add("Completed Subjects", (new JScrollPane(comTable)));
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.ipady = 100;
        p.add(both, c);
    }

    private void addManageSubjectsUI(GridBagConstraints c, JPanel p) {
        JButton manageSubjects = new JButton("Modify");
        c.gridwidth = 1;
        setGridDetails(2, 2, 20, 20, c);
        manageSubjects.addActionListener(e -> {
            try {
                popUpModify(p);
            } catch (Exception ex) {
                //
            }
        });
        p.add(manageSubjects, c);
    }

    private void popUpModify(JPanel p) {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JTextField description = new JTextField("Description");
        JTextField time = new JTextField("HH:MM:SS");
        addDescription(description, time, myPanel, c);

        JTextField removeSubject = new JTextField("Description of Subject to be Removed");
        addRemoveSubject(removeSubject, myPanel, c);

        JTextField editSubject = new JTextField("Subject Description");
        JTextField newDesc = new JTextField("New Description (OR)");
        JTextField newTime = new JTextField("New Time");
        addEditSubject(editSubject, newDesc, newTime, myPanel, c);

        JOptionPane.showMessageDialog(p, myPanel, "ScreenPreview", JOptionPane.PLAIN_MESSAGE, null);
        try {
            tryEditSubject(editSubject, newDesc, newTime);
        } catch (Exception e) {
            // Oh, well!
        }
        tryRemoveSubject(removeSubject);
        try {
            tryAddSubject(description, time);
        } catch (Exception e) {
            //Oh, well!
        }

        myPanel.setVisible(false);
    }

    private void addDescription(JTextField description, JTextField time, JPanel myPanel, GridBagConstraints c) {
        c.insets = new Insets(4,4,4,4);

        setGridDetails(0, 0, 20, 5, c);
        myPanel.add((new JLabel("Add a Subject")));

        description.setColumns(6);
        customFocusListener(description, "Description");
        setGridDetails(2, 0, 20, 5, c);
        myPanel.add(description, c);

        time.setColumns(6);
        customFocusListener(time, "HH:MM:SS");
        setGridDetails(3, 0, 20, 5, c);
        myPanel.add(time, c);
    }

    private void addRemoveSubject(JTextField removeSubject, JPanel myPanel, GridBagConstraints c) {
        removeSubject.setColumns(21);
        customFocusListener(removeSubject, "Description of Subject to be Removed");
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.ipady = 5;
        myPanel.add(removeSubject, c);
        c.gridwidth = 1;

        setGridDetails(0, 1, 20, 5, c);
        myPanel.add((new JLabel("Remove a Subject:")), c);
    }

    private void addEditSubject(JTextField editSubject, JTextField newDesc, JTextField newTime, JPanel myPanel,
                                GridBagConstraints c) {
        editSubject.setColumns(10);
        customFocusListener(editSubject, "Subject Description");
        setGridDetails(2, 2, 20, 5, c);
        myPanel.add(editSubject, c);


        newDesc.setColumns(12);
        customFocusListener(newDesc, "New Description (OR)");
        setGridDetails(3, 2, 20, 5, c);
        myPanel.add(newDesc, c);


        newTime.setColumns(5);
        customFocusListener(newTime, "New Time");
        setGridDetails(3, 3, 20, 5, c);
        myPanel.add(newTime, c);

        setGridDetails(0, 2, 20, 5, c);
        myPanel.add((new JLabel("Edit a Subject:")), c);
    }

    private void tryEditSubject(JTextField editSubject, JTextField newDesc, JTextField newTime) throws Exception {
        if (!editSubject.getText().equals("Subject Description") && !newDesc.getText().equals("New Description (OR)")) {
            if (subjectManager.getSubject(editSubject.getText()) != null) {
                subjectManager.getSubject(editSubject.getText()).setDescription(newDesc.getText());
                for (int i = 0; i < incSubModel.getRowCount(); i++) {
                    if (incSubModel.getValueAt(i, 0).equals(editSubject.getText())) {
                        incSubModel.setValueAt(newDesc.getText(), i, 0);
                    }
                }
            }
        } else if (!newTime.getText().equals("New Time")) {
            if (subjectManager.getSubject(editSubject.getText()) != null) {
                subjectManager.getSubject(editSubject.getText())
                        .setSecondsRemaining(getTimeFromString(newTime.getText().split(":", 3)));
                for (int i = 0; i < incSubModel.getRowCount(); i++) {
                    if (incSubModel.getValueAt(i, 0).equals(editSubject.getText())) {
                        incSubModel.setValueAt(newTime.getText(), i, 1);
                    }
                }
            }
        }
    }

    private void tryRemoveSubject(JTextField removeSubject) {
        if (!removeSubject.getText().equals("Enter the Description of Subject to be Removed")) {
            subjectManager.removeSubject(removeSubject.getText());
            for (int i = 0; i < incSubModel.getRowCount(); i++) {
                if (incSubModel.getValueAt(i, 0).equals(removeSubject.getText())) {
                    incSubModel.removeRow(i);
                }
            }
        }
    }

    private void tryAddSubject(JTextField description, JTextField time) throws Exception {
        if (!description.getText().equals("Description") && !time.getText().equals("HH:MM:SS")) {
            subjectManager.addSubject(new Subject(description.getText(),
                    getTimeFromString(time.getText().split(":", 3))));
            Object[] row = {description.getText(), time.getText()};
            incSubModel.addRow(row);
        }
    }

    public static int getTimeFromString(String[] split) throws Exception {
        for (String s: split) {
            if (s.length() > 2) {
                throw new Exception();
            }
        }
        if (split.length > 3 || split.length < 1) {
            throw new Exception();
        }

        int i = 0;
        if (split.length == 3) {
            i = 3600 * Integer.parseInt(split[0])
                    + 60 * Integer.parseInt(split[1])
                    + Integer.parseInt(split[2]);
        } else if (split.length == 2) {
            i = 60 * Integer.parseInt(split[0]) + Integer.parseInt(split[1]);
        } else {
            i = Integer.parseInt(split[0]);
        }
        return i;
    }

    private void customFocusListener(JTextField f, String s) {
        f.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                f.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) {
                    f.setText(s);
                }
            }
        });
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
            //System.out.println("Saved Current Timer State to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadFromFile() {
        HashMap<String, Object> objects = new HashMap<>();
        try {
            objects = jsonReader.read();
            //System.out.println("Loaded Previous Timer State from " + JSON_STORE);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE);
        }

        subjectManager = new SubjectManager((ArrayList<Subject>) objects.get("incSub"),
                (ArrayList<Subject>) objects.get("comSub"));
        updateLoadSubjectUI(subjectManager.getIncSubjects(), subjectManager.getComSubjects());

        try {
            updateTimerLoadingUI((boolean) objects.get("run?"), (int) objects.get("secs"));
        } catch (Exception e) {
            if (timer != null) {
                timer.stop();
            }
        }
    }

    //effects: helper for timer ui from loading in a timer
    private void updateTimerLoadingUI(Boolean b, int i) {
        if (timer != null) {
            timer.stop();
        }

        if (b && timer == null) {
            shape.setTotTime(i);
            shape.getTimeTextField().setText(formatSeconds(i));
            startTimer(i, shape);
            time.setVisible(false);
            pane.setVisible(true);
        } else if (timer == null) {
            TimerApp.timer = new Timer(i);
            System.out.println("Timer Loaded with "
                    + formatSeconds(timer.getRemainingTime()) + " remaining.");
        }
    }

    // modifies: this
    // effects: starts a timer, then a new thread to accept user commands again
    private void startTimer(int time, Draw shape) {
        if (timer == null) {
            Runnable r = () -> {
                TimerApp.timer = new Timer(time);
                try {
                    timer.start(subjectManager, shape);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                timer = null;
                updateTimerUI();
            };
            new Thread(r).start();
        }
    }

    //effects: Helper function that resets relevant UI elements
    private void updateTimerUI() {
        shape.getTimeTextField().setText("00:00");
        shape.setTotTime(0);
        shape.resetProgress();
        shape.update();
        this.time.setVisible(true);
        this.pane.setVisible(false);
    }

    //effects: Helper function that updates relevant subject UI elements
    public static void updateSubjectsUI(ArrayList<Subject> s, ArrayList<Subject> cs) {
        if (incSubModel.getValueAt(0, 1) != null) {
            if (s.get(0).getSecondsRemaining() > 0) {
                incSubModel.setValueAt(formatSeconds(s.get(0).getSecondsRemaining()), 0, 1);
            } else {
                incSubModel.removeRow(0);
                Object[] row = {s.get(0).getDescription(), formatSeconds(s.get(0).getSecondsDone())};
                comSubModel.addRow(row);
            }
        }
    }

    private void updateLoadSubjectUI(ArrayList<Subject> sub, ArrayList<Subject> csub) {
        incSubModel.setRowCount(0);
        comSubModel.setRowCount(0);
        for (Subject s : sub) {
            Object[] row = {s.getDescription(), formatSeconds(s.getSecondsRemaining())};
            incSubModel.addRow(row);
        }
        for (Subject cs : csub) {
            Object[] row = {cs.getDescription(), formatSeconds(cs.getSecondsDone())};
            comSubModel.addRow(row);
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

    private void briefPause() {
        try {
            Thread.sleep(900);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}