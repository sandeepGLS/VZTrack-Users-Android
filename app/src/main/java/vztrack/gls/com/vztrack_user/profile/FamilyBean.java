package vztrack.gls.com.vztrack_user.profile;

/**
 * Created by sandeep on 31/3/16.
 */
public class FamilyBean {
    int buildingId;
    String flatNo;
    String flatOwnerName;
    String flatOwnerMobile;
    int flatFamilySize;
    int flatVehickeSize;
    String flatVehicles;
    String flatUserName;
    String flatPassword;
    boolean active;
    int societyId;
    int familyId;
    String wing;
    boolean extraAccess;
    String soc_phoneNo;
    boolean sosAccess;
    boolean type;
    boolean complaint;

    public boolean isSosAccess() {
        return sosAccess;
    }

    public void setSosAccess(boolean sosAccess) {
        this.sosAccess = sosAccess;
    }

    public String getSoc_phoneNo() {
        return soc_phoneNo;
    }

    public void setSoc_phoneNo(String soc_phoneNo) {
        this.soc_phoneNo = soc_phoneNo;
    }

    public boolean isExtraAccess() {
        return extraAccess;
    }

    public void setExtraAccess(boolean extraAccess) {
        this.extraAccess = extraAccess;
    }

    public void setWing(String wing) {
        this.wing = wing;
    }

    public String getWing() {
        return wing;
    }

    public String getFlatUserName() {
        return flatUserName;
    }
    public void setFlatUserName(String flatUserName) {
        this.flatUserName = flatUserName;
    }
    public int getBuildingId() {
        return buildingId;
    }
    public void setBuildingId(int buildingId) {
        this.buildingId = buildingId;
    }
    public String getFlatNo() {
        return flatNo;
    }
    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
    public String getFlatOwnerName() {
        return flatOwnerName;
    }
    public void setFlatOwnerName(String flatOwnerName) {
        this.flatOwnerName = flatOwnerName;
    }
    public String getFlatOwnerMobile() {
        return flatOwnerMobile;
    }
    public void setFlatOwnerMobile(String flatOwnerMobile) {
        this.flatOwnerMobile = flatOwnerMobile;
    }
    public int getFlatFamilySize() {
        return flatFamilySize;
    }
    public void setFlatFamilySize(int flatFamilySize) {
        this.flatFamilySize = flatFamilySize;
    }
    public int getFlatVehickeSize() {
        return flatVehickeSize;
    }
    public void setFlatVehickeSize(int flatVehickeSize) {
        this.flatVehickeSize = flatVehickeSize;
    }
    public String getFlatVehicles() {
        return flatVehicles;
    }
    public void setFlatVehicles(String flatVehicles) {
        this.flatVehicles = flatVehicles;
    }
    public String getFlatPassword() {
        return flatPassword;
    }
    public void setFlatPassword(String flatPassword) {
        this.flatPassword = flatPassword;
    }
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    public int getSocietyId() {
        return societyId;
    }
    public void setSocietyId(int societyId) {
        this.societyId = societyId;
    }
    public int getFamilyId() {
        return familyId;
    }
    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
    public boolean isType() {
        return type;
    }
    public void setType(boolean type) {
        this.type = type;
    }

    public boolean isComplaint() {
        return complaint;
    }

    public void setComplaint(boolean complaint) {
        this.complaint = complaint;
    }
}

