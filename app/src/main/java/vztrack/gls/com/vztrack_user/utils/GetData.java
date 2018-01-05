package vztrack.gls.com.vztrack_user.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import vztrack.gls.com.vztrack_user.BaseActivity;
import vztrack.gls.com.vztrack_user.MainActivity;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class GetData extends AsyncTask {
    String url,url_rating;
    String result = "";
    String callFor = "";
    String extendedUrl = "";
    BaseActivity activity;
    ProgressDialog progressDialog;
    String check;

    public GetData(BaseActivity activity, String callFor, String extendedUrl){
        this.activity = activity;
        this.callFor = callFor;
        this.extendedUrl = extendedUrl;
        url = createURL(callFor);
    }


    private String createURL(String callFor) {
        url = Constants.BASE_URL;
        url_rating = Constants.BASE_URL_FOR_RATING;

        if(callFor==CallFor.NOTICES){
            url = url+URL.NOTICES;
        }
        if(callFor==CallFor.VISITORS){
            url = url+URL.VISITORS+extendedUrl;
        }
        if(callFor==CallFor.VISITOR_LIST){
            url = url+URL.VISIT_LIST+extendedUrl;
        }
        if(callFor==CallFor.CHANGE_PASSWORD){
            url = url+URL.CHANGE_PASSWORD+extendedUrl;
        }

        // COMPLAIN URL
        if(callFor==CallFor.GET_COMPLAIN){
            url = url+URL.GET_COMPLAINS+extendedUrl;
        }
        if(callFor==CallFor.CLOSE_COMPLAIN){
            url = url+URL.CLOSE_COMPLAINS+extendedUrl;
        }
        if(callFor==CallFor.GET_COMPLAIN_CATEGORY){
            url = url+URL.GET__COMPLAIN_CATEGORY;
        }
        if(callFor==CallFor.ERROR_ENTRY){
            url = url+URL.MARK_ERROR+extendedUrl;
        }
        // RATTING URL
        if(callFor==CallFor.PROVIDER_LIST){
            url = url_rating+URL.PROVIDER_LIST+extendedUrl;
        }

        // SEARCH VEHCILE
        if(callFor==CallFor.VEHICLENOPATTERN){
            url = url+URL.VEHICLE_NO_PATTERN;
        }
        if(callFor==CallFor.SEARCH_VEHICLE){
            url = url+URL.SEARCH_VEHICLE+extendedUrl;
        }
        if(callFor==CallFor.ADD_VEHICLE){
            url = url+URL.ADD_VEHICLE+extendedUrl;
        }
        if(callFor==CallFor.GET_VEHICLES){
            url = url+URL.GET_FLAT_VEHICLES;
        }
        if(callFor==CallFor.DELETE_VEHICLE){
            url = url+URL.DELETE_FLAT_VEHICLES+extendedUrl;
        }
        if(callFor==CallFor.MESSAGE){
            url = url+URL.MESSAGE+extendedUrl;
        }
        if(callFor==CallFor.GET_GROUPS){
            url = url+URL.GET_ALL_GROUPS;
        }
        if(callFor==CallFor.GET_FLATS){
            url = url+URL.GET_ALL_FLATS;
        }
        if(callFor == CallFor.LOCAL_STORES){
            url = url+URL.LOCAL_STORES;
        }
        return url;
    }

    @Override
    protected void onPreExecute() {
        check = SheredPref.getRun(activity);
        if(check==null) {
            check="";
        }
        if(check.equals("RUN")) {
            if(MainActivity.fragment_flag==1){

            } else {
                progressDialog = ProgressDialog.show(activity,"","Loading...");
            }
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("GET URL ===>",url);
        try {
            result = ServerConnection.giveResponse(url,"");
           Log.e("GET RESULT "," "+result);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        try {
            super.onPostExecute(o);
            if(check==null) {
                check="";
            }
            if(check.equals("RUN")) {
                if(MainActivity.fragment_flag==1) {

                } else {
                    progressDialog.dismiss();
                }
            }
            activity.onGetResponse(result,callFor);
        }catch (Exception ex) {
            Log.e("Exception In GetData "," "+ex);
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            final Snackbar snackBar = Snackbar.make(rootView, "Something Went Wrong" , 2000);
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();
        }

    }
}
