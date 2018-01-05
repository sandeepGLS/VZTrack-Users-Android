package vztrack.gls.com.vztrack_user.beans;

public class ComplainsBean {
	
	int socity_id;
	int family_id;
	String category;
	String description;
	String status;
	String created_date;
	String modify_date;
	String seen_date;
	String closed_by;
	int close_by_id;
	int vz_comp_id;
	
	
	public int getVz_comp_id() {
		return vz_comp_id;
	}
	public void setVz_comp_id(int vz_comp_id) {
		this.vz_comp_id = vz_comp_id;
	}
	public int getSocity_id() {
		return socity_id;
	}
	public void setSocity_id(int socity_id) {
		this.socity_id = socity_id;
	}
	
	public int getFamily_id() {
		return family_id;
	}
	public void setFamily_id(int family_id) {
		this.family_id = family_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public String getSeen_date() {
		return seen_date;
	}
	public void setSeen_date(String seen_date) {
		this.seen_date = seen_date;
	}
	public String getClosed_by() {
		return closed_by;
	}
	public void setClosed_by(String closed_by) {
		this.closed_by = closed_by;
	}
	public int getClose_by_id() {
		return close_by_id;
	}
	public void setClose_by_id(int close_by_id) {
		this.close_by_id = close_by_id;
	}
	

}
