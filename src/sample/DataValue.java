package sample;

import javafx.beans.property.*;

import java.time.LocalDateTime;

/**
 * Created by User on 20.04.2017.
 */
public class DataValue {
    private DoubleProperty val;
    private ObjectProperty<LocalDateTime> localDateTime;

    DataValue(LocalDateTime dateTime, Double val) {
        this.localDateTime = new SimpleObjectProperty<LocalDateTime>(dateTime);
        this.val = new SimpleDoubleProperty(val);
    }

    public DoubleProperty getValProp(){
        return val;
    }

    public ObjectProperty<LocalDateTime> getDTProp(){
        return localDateTime;
    }

    public Double getVal(){
        return val.get();
    }

    public LocalDateTime getDT(){
        return localDateTime.get();
    }

}
