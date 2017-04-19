package sample;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by User on 19.04.2017.
 */
public class DateFromServer {
    private HashMap<LocalDate, Double> timeVal;

    private LocalDate minDate;
    private LocalDate maxDate;
    private Double minVal;
    private Double maxVal;
    private Double averageVal;

    public DateFromServer(HashMap<LocalDate, Double> timeVal) {
        this.timeVal = timeVal;

        LocalDate timeArray[] = timeVal.keySet().toArray(new LocalDate[timeVal.size()]);
        minDate = timeArray[0];
        maxDate = timeArray[0];
        minVal = timeVal.get(timeArray[0]);
        maxVal = timeVal.get(timeArray[0]);
        averageVal = 0.0;


        for (LocalDate date : timeVal.keySet()){
            if (date.isBefore(minDate))
                minDate = date;
            if (date.isAfter(maxDate))
                maxDate = date;
            Double curVal = timeVal.get(date);
            if (curVal < minVal)
                minVal = curVal;
            if (curVal < maxVal)
                maxVal = curVal;
            averageVal += curVal;
        }
        averageVal /= timeVal.size();
    }

    public LocalDate getMinDate() {
        return minDate;
    }

    public LocalDate getMaxDate() {
        return maxDate;
    }

    public Double getMinVal() {
        return minVal;
    }

    public Double getMaxVal() {
        return maxVal;
    }

    public Double getAverageVal() {
        return averageVal;
    }
}
