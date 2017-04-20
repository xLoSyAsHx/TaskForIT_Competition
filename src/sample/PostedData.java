package sample;


import com.google.gson.Gson;

/**
 * Created by denis on 19.04.17.
 */
class PostedData {
    private char kind_typename;
    private int location_id;
    private int deployment_id;
    private long time_start;
    private long time_end;

    public PostedData() {}

    public PostedData(char kind_typename, int location_id, int deployment_id, long time_start, long time_end) {
        this.kind_typename = kind_typename;
        this.location_id = location_id;
        this.deployment_id = deployment_id;
        this.time_start = time_start;
        this.time_end = time_end;
    }
}