package vztrack.gls.com.vztrack_user.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import vztrack.gls.com.vztrack_user.BaseActivity;

/**
 * Created by Santosh on 06-Oct-15.
 */
public class PostData extends AsyncTask {
    String url,url_rating;
    String result = "";
    String callFor = "";
    String data = "";
    BaseActivity activity;
    ProgressDialog progressDialog;
    String check;
   // LoginResult response;

    public PostData(String data, BaseActivity activity, String callFor){
        this.activity = activity;
        this.callFor = callFor;
        this.data = data;
        url = createURL(callFor);
    }

    private String createURL(String callFor) {
        url = Constants.BASE_URL;
        url_rating = Constants.BASE_URL_FOR_RATING;

        if(callFor==CallFor.LOGIN){
            url = url+ URL.LOGIN;
        }
        if(callFor==CallFor.LOGOUT){
            url = url+URL.LOGOUT;
        }
        if(callFor==CallFor.PENDING_RATING){
            url = url_rating+URL.PENDING_RATINGS;

        }
        if(callFor==CallFor.CANCLE_RATING){
            url = url_rating+URL.CANCLE_RATING;
        }
        if(callFor==CallFor.SAVE_RATING){
            url = url_rating+URL.SAVE_RATINGS;
        }
        if(callFor==CallFor.PROVIDERS_DATA){
            url = url_rating+URL.PROVIDERS_DATA;
        }
        if(callFor==CallFor.PROVIDER_DEATILS_DATA){
            url = url_rating+URL.PROVIDER_DETAIL_DATA;
        }
        if(callFor==CallFor.SEND_NOTICE){
            url = url+URL.SEND_NOTICE;
        }
        if(callFor==CallFor.SOS){
            url = url+URL.SOS;
        }
        if(callFor==CallFor.FEEDBACK){
            url = url+URL.FEEDBACK;
        }
        if(callFor==CallFor.ADD_COMPLAIN){
            url = url+URL.ADD_COMPLAINS;
        }
        if(callFor==CallFor.SEND_MESSAGE){
            url = url+URL.SEND_MESSAGE;
        }
        if(callFor==CallFor.SEND_MESSAGE_NOTIFICATION){
            url = url+URL.SEND_MESSAGE_NOTIFICATION;
        }

        return url;
    }

    @Override
    protected void onPreExecute() {

        check = SheredPref.getRun(activity);

        if(check==null)
        {
            check="";
        }
        if(check.equals("RUN"))
        {
            progressDialog = ProgressDialog.show(activity,"","Loading...");
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e("POST URL ===>",url);
        Log.e("POST Data ===>",data);
        try {
            result = ServerConnection.giveResponse(url,data);

            Log.e("POST Result ===>",result);

        } catch (Exception e){
            Log.e("Error doInBackground",e.toString());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {

        try
        {
            super.onPostExecute(o);

            if(check==null)
            {
                check="";
            }
            if(check.equals("RUN"))
            {
                progressDialog.dismiss();
            }
            activity.onGetResponse(result, callFor);
        }catch (Exception ex)
        {
            View rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            final Snackbar snackBar = Snackbar.make(rootView, "Something Went Wrong" , 2000);
            snackBar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackBar.dismiss();
                }
            });
            snackBar.show();
            Log.e("EX "," "+ex);
        }

    }
}
