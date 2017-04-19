package sample;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by User on 17.04.2017.
 */
public class Settings {

    private HashMap<String, HashMap<String, HashMap<String, String[]>>> setFromTyteToDistrict;

    // { type(Temperature, ...): { region(NijObl, ...): { city(NN, ...): [ VerhPechery, Avtaz, ... ] } } }
    // type == types in dialog

    public Settings(HashMap<String, HashMap<String, HashMap<String, String[]>>> setFromTyteToDistrict) {
        this.setFromTyteToDistrict = setFromTyteToDistrict;
    }

    public Set<String> getAvalibleControllerTypes(){
        return setFromTyteToDistrict.keySet();
    }

    public String[] getAvalibleRegions(String type){
        return setFromTyteToDistrict.get(type).keySet().toArray(new String[setFromTyteToDistrict.get(type).size()]);
    }

    public String[] getAvalibleCities(String type, String region){
        return setFromTyteToDistrict.get(type).get(region).keySet().toArray(
                new String[setFromTyteToDistrict.get(type).get(region).size()]
        );
    }

    public String[] getAvalibleDistricts(String type, String region, String city){
        return setFromTyteToDistrict.get(type).get(region).get(city);
    }
}
