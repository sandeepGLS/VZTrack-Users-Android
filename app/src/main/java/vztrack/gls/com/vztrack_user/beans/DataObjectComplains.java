package vztrack.gls.com.vztrack_user.beans;

/**
 * Created by sandeep on 14/3/16.
 */
public class DataObjectComplains {
    private int mTextComplainId;
    private String mTextCategory;
    private String mTextdescription;
    private String mTextDate;
    private String mTextStatus;
    private String mTextClosedBy;


    public DataObjectComplains(int id, String mTextCategory, String mTextdescription, String mTextDate, String mTextStatus, String mTextClosedBy) {
        this.mTextComplainId = id;
        this.mTextCategory = mTextCategory;
        this.mTextdescription = mTextdescription;
        this.mTextDate = mTextDate;
        this.mTextStatus = mTextStatus;
        this.mTextClosedBy = mTextClosedBy;
    }

    public String getmTextCategory() {
        return mTextCategory;
    }

    public void setmTextCategory(String mTextCategory) {
        this.mTextCategory = mTextCategory;
    }

    public String getmTextdescription() {
        return mTextdescription;
    }

    public void setmTextdescription(String mTextdescription) {
        this.mTextdescription = mTextdescription;
    }

    public String getmTextDate() {
        return mTextDate;
    }

    public void setmTextDate(String mTextDate) {
        this.mTextDate = mTextDate;
    }

    public int getmTextComplainId() {
        return mTextComplainId;
    }

    public void setmTextComplainId(int mTextComplainId) {
        this.mTextComplainId = mTextComplainId;
    }

    public String getmTextStatus() {
        return mTextStatus;
    }

    public void setmTextStatus(String mTextStatus) {
        this.mTextStatus = mTextStatus;
    }

    public String getmTextClosedBy() {
        return mTextClosedBy;
    }

    public void setmTextClosedBy(String mTextClosedBy) {
        this.mTextClosedBy = mTextClosedBy;
    }
}