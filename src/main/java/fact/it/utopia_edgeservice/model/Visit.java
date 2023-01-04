package fact.it.utopia_edgeservice.model;

import java.time.LocalDate;
import java.util.Map;

public class Visit {
    private String id;
    private int stationID;
    private Map<Integer, Integer> countPerInterests;
    private LocalDate date;
    private int total;

    public Visit() {
    }

    public Visit(String id, int stationID, int interestID, LocalDate date, int total, Map<Integer, Integer> countPerInterests) {
        this.id = id;
        this.stationID = stationID;
        this.countPerInterests = countPerInterests;
        this.date = date;
        this.total = total;
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

    public Map<Integer, Integer> getCountPerInterests() {
        return countPerInterests;
    }

    public void setCountPerInterests(Map<Integer, Integer> countPerInterests) {
        this.countPerInterests = countPerInterests;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int count) {
        this.total = count;
    }

    public void incrementTotal() {
        this.total += 1;
    }
}
