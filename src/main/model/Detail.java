package model;

//Class that works as a descriptor for subject //TODO: finish time implementation of Detail
public class Detail {
    private String description;
//    private int secondsRemaining;

    public Detail(String description) {
        this.description = description;
//        secondsRemaining = 0;
    }

/*    public Detail(String description, int secondsRemaining) {
        this.description = description;
        this.secondsRemaining = secondsRemaining;
    }

    public int getSecondsRemaining() {
        return this.secondsRemaining;
    }*/ //TODO: Implement this

    public String getDescription() {
        return this.description;
    }

    public void printDetail() {

        //Timer.printSeconds(this.secondsRemaining);
    }
}
