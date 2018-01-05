package vztrack.gls.com.vztrack_user.beans;

import java.util.ArrayList;

public class MessageResponceBean {
	String code;
	String message;
	ArrayList<MessageBean> messageBeans = new ArrayList<MessageBean>();
	ArrayList<MessageGroupBean> messageGroupBeans = new ArrayList<MessageGroupBean>();
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
	public ArrayList<MessageBean> getMessageBeans() {
		return messageBeans;
	}
	public void setMessageBeans(ArrayList<MessageBean> messageBeans) {
		this.messageBeans = messageBeans;
	}
	public ArrayList<MessageGroupBean> getMessageGroupBeans() {
		return messageGroupBeans;
	}
	public void setMessageGroupBeans(ArrayList<MessageGroupBean> messageGroupBeans) {
		this.messageGroupBeans = messageGroupBeans;
	}	
	
	
}
