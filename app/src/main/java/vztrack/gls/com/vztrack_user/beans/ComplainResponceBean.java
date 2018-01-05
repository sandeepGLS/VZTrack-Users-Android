package vztrack.gls.com.vztrack_user.beans;

import java.util.ArrayList;

/**
 * Created by sandeep on 10/7/17.
 */

public class ComplainResponceBean {
    String message;
    String code;
    ArrayList<ComplainsBean> complains;
    ArrayList<String> societyComplainCategories;

    public ArrayList<String> getSocietyComplainCategories() {
        return societyComplainCategories;
    }

    public void setSocietyComplainCategories(ArrayList<String> societyComplainCategories) {
        this.societyComplainCategories = societyComplainCategories;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<ComplainsBean> getComplains() {
        return complains;
    }

    public void setComplains(ArrayList<ComplainsBean> complains) {
        this.complains = complains;
    }
}
