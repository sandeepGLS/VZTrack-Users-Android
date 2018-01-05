package vztrack.gls.com.vztrack_user.beans;

/**
 * Created by sandeep on 18/9/17.
 */

import java.util.ArrayList;

public class VehicleResponce {
    String code;
    String message;
    ArrayList<VehicleBean> vehicleBeans = new ArrayList<VehicleBean>();
    ArrayList<SearchVehicleBean> searchVehicleBeans =  new ArrayList<SearchVehicleBean>();
    ArrayList<String> vehiclePattern = new ArrayList<String>();
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public ArrayList<VehicleBean> getVehicleBeans() {
        return vehicleBeans;
    }
    public void setVehicleBeans(ArrayList<VehicleBean> vehicleBeans) {
        this.vehicleBeans = vehicleBeans;
    }
    public ArrayList<String> getVehiclePattern() {
        return vehiclePattern;
    }
    public void setVehiclePattern(ArrayList<String> vehiclePattern) {
        this.vehiclePattern = vehiclePattern;
    }
    public ArrayList<SearchVehicleBean> getSearchVehicleBeans() {
        return searchVehicleBeans;
    }
    public void setSearchVehicleBeans(ArrayList<SearchVehicleBean> searchVehicleBeans) {
        this.searchVehicleBeans = searchVehicleBeans;
    }
}
