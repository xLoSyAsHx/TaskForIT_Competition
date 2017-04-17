package sample;

import java.time.LocalDate;
import java.util.Vector;

/**
 * Created by User on 17.04.2017.
 */
public class Settings {
    private Vector<String> dataTypes;
    private Vector<String> regions;
    private Vector<String> controllerTypes;
    private Vector<LocalDate> times;

    public Settings(Vector<String> dataTypes, Vector<String> regions, Vector<String> controllerTypes, Vector<LocalDate> times) {
        this.dataTypes = dataTypes;
        this.regions = regions;
        this.controllerTypes = controllerTypes;
        this.times = times;
    }

    public Vector<String> getDataTypes() {
        return dataTypes;
    }

    public Vector<String> getRegions() {
        return regions;
    }

    public Vector<String> getControllerTypes() {
        return controllerTypes;
    }

    public Vector<LocalDate> getTimes() {
        return times;
    }

    public void set(Vector<String> dataTypes, Vector<String> regions, Vector<String> controllerTypes, Vector<LocalDate> times) {
        this.dataTypes = dataTypes;
        this.regions = regions;
        this.controllerTypes = controllerTypes;
        this.times = times;
    }
}
