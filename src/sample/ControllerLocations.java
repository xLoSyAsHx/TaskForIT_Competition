package sample;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 19.04.2017.
 */
public class ControllerLocations {
    static private ArrayList<String[]> locations = null;

    static private void check(){
        if (locations == null){
            locations = new ArrayList<>();
            locations.add("Нижегородская,Нижний Новгород,Нижегородский".split(","));
            locations.add("Нижегородская,Нижний Новгород,Советский".split(","));
            locations.add("Нижегородская,Нижний Новгород,Канавинский".split(","));
            locations.add("Нижегородская,Нижний Новгород,Сормовский".split(","));
            locations.add("Нижегородская,Нижний Новгород,Автозаводский".split(","));
            locations.add("Нижегородская,Держинск, ".split(","));
            locations.add("Нижегородская,Бор, ".split(","));
        }
    }

    public static String getRegion(int index){
        check();
        return (locations.get(index))[0];
    }

    public static String getCity(int index){
        check();
        return (locations.get(index))[1];
    }

    public static String getDistrict(int index){
        check();
        return (locations.get(index))[2];
    }
}
