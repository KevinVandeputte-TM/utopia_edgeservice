package fact.it.utopia_edgeservice.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Question {

    private int questionID;
    private String questiontoask;
    private String correctanswer;
    private String fOne;
    private String fTwo;
    private String fThree;
    private Station station;

    public Question() {
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getQuestiontoask() {
        return questiontoask;
    }

    public void setQuestiontoask(String questiontoask) {
        this.questiontoask = questiontoask;
    }

    public String getCorrectanswer() {
        return correctanswer;
    }

    public void setCorrectanswer(String correctanswer) {
        this.correctanswer = correctanswer;
    }

    public String getfOne() {
        return fOne;
    }

    public void setfOne(String fOne) {
        this.fOne = fOne;
    }

    public String getfTwo() {
        return fTwo;
    }

    public void setfTwo(String fTwo) {
        this.fTwo = fTwo;
    }

    public String getfThree() {
        return fThree;
    }

    public void setfThree(String fThree) {
        this.fThree = fThree;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
