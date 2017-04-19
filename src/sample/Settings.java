package sample;

import java.time.LocalDate;

/**
 * Created by User on 19.04.2017.
 */
public class Settings {
    private String controllerType;
    private Integer location;
    private Integer indoorOutdoor;
    private LocalDate From;
    private int f_hr, f_min, f_sec;
    private LocalDate To;
    private int t_hr, t_min, t_sec;

    public Settings(String controllerType, Integer location, Integer indoorOutdoor,
                    LocalDate from, int f_hr, int f_min, int f_sec,
                    LocalDate to, int t_hr, int t_min, int t_sec) {
        this.controllerType = controllerType;
        this.location = location;
        this.indoorOutdoor = indoorOutdoor;
        From = from;
        this.f_hr = f_hr;
        this.f_min = f_min;
        this.f_sec = f_sec;
        To = to;
        this.t_hr = t_hr;
        this.t_min = t_min;
        this.t_sec = t_sec;
    }

    public String getControllerType() {
        return controllerType;
    }

    public Integer getLocation() {
        return location;
    }

    public Integer getIndoorOutdoor() {
        return indoorOutdoor;
    }

    public LocalDate getFrom() {
        return From;
    }

    public LocalDate getTo() {
        return To;
    }

    public int getF_hr() {
        return f_hr;
    }

    public int getF_min() {
        return f_min;
    }

    public int getF_sec() {
        return f_sec;
    }

    public int getT_hr() {
        return t_hr;
    }

    public int getT_min() {
        return t_min;
    }

    public int getT_sec() {
        return t_sec;
    }
}
