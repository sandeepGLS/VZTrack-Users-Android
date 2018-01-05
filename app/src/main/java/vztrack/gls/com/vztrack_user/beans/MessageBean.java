package vztrack.gls.com.vztrack_user.beans;

public class MessageBean {
	int messageId;
	String message;
	int[] family_id;
	String flatNo;
	int[] groupId;
	String[] groupName;
	String sent_date;
	boolean messageToAll;
	String societyId;
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}	
	public String getSent_date() {
		return sent_date;
	}
	public void setSent_date(String sent_date) {
		this.sent_date = sent_date;
	}
	public int[] getFamily_id() {
		return family_id;
	}
	public void setFamily_id(int[] family_id) {
		this.family_id = family_id;
	}
	public int[] getGroupId() {
		return groupId;
	}
	public void setGroupId(int[] groupId) {
		this.groupId = groupId;
	}
	public String[] getGroupName() {
		return groupName;
	}
	public void setGroupName(String[] groupName) {
		this.groupName = groupName;
	}

	public boolean isMessageToAll() {
		return messageToAll;
	}

	public void setMessageToAll(boolean messageToAll) {
		this.messageToAll = messageToAll;
	}

	public String getSocietyId() {
		return societyId;
	}

	public void setSocietyId(String societyId) {
		this.societyId = societyId;
	}
}