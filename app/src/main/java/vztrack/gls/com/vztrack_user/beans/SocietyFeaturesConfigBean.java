package vztrack.gls.com.vztrack_user.beans;

public class SocietyFeaturesConfigBean {
	boolean outTime;
	boolean company;
	boolean sos;
	boolean complain;
	boolean localStore;
	public boolean isOutTime() {
		return outTime;
	}
	public void setOutTime(boolean outTime) {
		this.outTime = outTime;
	}
	public boolean isCompany() {
		return company;
	}
	public void setCompany(boolean company) {
		this.company = company;
	}
	public boolean isSos() {
		return sos;
	}
	public void setSos(boolean sos) {
		this.sos = sos;
	}
	public boolean isComplain() {
		return complain;
	}
	public void setComplain(boolean complain) {
		this.complain = complain;
	}

	public boolean isLocalStore() {
		return localStore;
	}

	public void setLocalStore(boolean localStore) {
		this.localStore = localStore;
	}
}
