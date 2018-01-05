package vztrack.gls.com.vztrack_user.beans;

/**
 * Created by sandeep on 18/9/17.
 */

public class SearchVehicleBean {
    String flatNo_OR_name;
    String ownerType;
    String time_OR_vehicleType;
    String vehicleNumber;
    public String getFlatNo_OR_name() {
        return flatNo_OR_name;
    }
    public void setFlatNo_OR_name(String flatNo_OR_name) {
        this.flatNo_OR_name = flatNo_OR_name;
    }
    public String getOwnerType() {
        return ownerType;
    }
    public void setOwnerType(String ownerType) {
        this.ownerType = ownerType;
    }
    public String getTime_OR_vehicleType() {
        return time_OR_vehicleType;
    }
    public void setTime_OR_vehicleType(String time_OR_vehicleType) {
        this.time_OR_vehicleType = time_OR_vehicleType;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
