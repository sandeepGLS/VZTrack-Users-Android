package vztrack.gls.com.vztrack_user.beans;

/**
 * Created by sandeep on 18/9/17.
 */

public class VehicleBean {
    String societyId;
    String familyId;
    String vehicleType;
    String vehicleNumber;
    public String getSocietyId() {
        return societyId;
    }
    public void setSocietyId(String societyId) {
        this.societyId = societyId;
    }
    public String getFamilyId() {
        return familyId;
    }
    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
    public String getVehicleType() {
        return vehicleType;
    }
    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    public String getVehicleNumber() {
        return vehicleNumber;
    }
    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }
}
