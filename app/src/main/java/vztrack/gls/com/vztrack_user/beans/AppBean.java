package vztrack.gls.com.vztrack_user.beans;

/**
 * Created by sandeep on 1/2/17.
 */


public class AppBean {
    int appVersion;
    String forceUpdate;
    String versionInfo;
    public int getAppVersion() {
        return appVersion;
    }
    public void setAppVersion(int appVersion) {
        this.appVersion = appVersion;
    }
    public String getForceUpdate() {
        return forceUpdate;
    }
    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
    public String getVersionInfo() {
        return versionInfo;
    }
    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }


}
