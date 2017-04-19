package sample;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by User on 17.04.2017.
 */
public class CountersDate {
    public LocalDate starttime;
    public HashMap<LocalDate, Double> timestamp;
    public HashMap<String, String> meta;

    public CountersDate(LocalDate starttime, HashMap<LocalDate, Double> timestamp, HashMap<String, String> meta) {
        this.starttime = starttime;
        this.timestamp = timestamp;
        this.meta = meta;
    }

    public void set(LocalDate starttime, HashMap<LocalDate, Double> timestamp, HashMap<String, String> meta) {
        this.starttime = starttime;
        this.timestamp = timestamp;
        this.meta = meta;
    }

    public Set<LocalDate> getTimes(){
        return timestamp.keySet();
    }

    public String getValAsString(LocalDate localDate){
        return timestamp.get(localDate).toString();
    }
}
