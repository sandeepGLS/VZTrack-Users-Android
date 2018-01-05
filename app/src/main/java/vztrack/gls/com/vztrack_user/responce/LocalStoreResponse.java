package vztrack.gls.com.vztrack_user.responce;

import java.util.ArrayList;

import vztrack.gls.com.vztrack_user.beans.LocalStroreBean;

/**
 * Created by Santosh on 18-Dec-17.
 */

public class LocalStoreResponse {
    String message;
    String code;
    ArrayList<LocalStroreBean>  data = new ArrayList<>();

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

    public ArrayList<LocalStroreBean> getData() {
        return data;
    }

    public void setData(ArrayList<LocalStroreBean> data) {
        this.data = data;
    }
}
