package fact.it.utopia_edgeservice.model;

import java.time.LocalDate;
import java.util.Map;

public class Visit {
    private String id;
    private int stationID;
    private int interestID;
    private LocalDate date;
    private int count;

    public Visit() {
    }

    public Visit(String id, int stationID, int interestID, LocalDate date, int count) {
        this.id = id;
        this.stationID = stationID;
        this.interestID = interestID;
        this.date = date;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStationID() {
        return stationID;
    }

    public void setStationID(int stationID) {
        this.stationID = stationID;
    }

    public int getInterestID() {
        return interestID;
    }

    public void setInterestID(int interestID) {
        this.interestID = interestID;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
