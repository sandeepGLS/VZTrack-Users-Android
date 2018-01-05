package vztrack.gls.com.vztrack_user;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.exceptions.CleverTapMetaDataNotFoundException;
import com.clevertap.android.sdk.exceptions.CleverTapPermissionsNotSatisfied;
import com.google.gson.Gson;
import com.livechatinc.inappchat.ChatWindowActivity;

import java.util.ArrayList;
import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.beans.UserBean;
import vztrack.gls.com.vztrack_user.gcm.GCMClientManager;
import vztrack.gls.com.vztrack_user.responce.LoginResponse;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;
import za.co.riggaroo.materialhelptutorial.TutorialItem;
//import za.co.riggaroo.materialhelptutorial.tutorial.MaterialTutorialActivity;

public class LoginScreenActivity extends BaseActivity {

    EditText etPassword,etUsername;
    private int InternetCheckFlag = 0;
    private String strSocietyName,strFlatNo,wingName,strUsername, strPassword;
    private int strSocietyId,strFamilyId;
    private String reg_id;
    private String device_id;
    String PROJECT_NUMBER="1082591809215";
    FancyButton etSubmit;
    LinearLayout LoginLayout, SplashLayout;
    public static boolean splashFlag = true;
    private static final int REQUEST_CODE = 1234;
    private ImageView imageViewBackground;
    private TextView instr;
    CheckConnection cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_login_screen_new);

        SheredPref.setSound(this,"ENABLE");
        SheredPref.setVibration(this,"ENABLE");
        SheredPref.setNotification(this,"ENABLE");

        imageViewBackground = (ImageView) findViewById(R.id.bgImage);
        instr = (TextView) findViewById(R.id.instruction1);
        instr.setSelected(true);

        LoginLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        SplashLayout = (LinearLayout) findViewById(R.id.splash);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etUsername = (EditText) findViewById(R.id.etUsername);

        etSubmit = (FancyButton) findViewById(R.id.etSubmit);

        cc = new CheckConnection(getApplicationContext());

        CleverTap.cleverTap_Record_Event(this, Events.event_login_screen);

        Boolean isConnectingToInternet = cc.isConnectingToInternet();

        SplashLayout.setVisibility(View.VISIBLE);

        String userName = SheredPref.getUsername(this);
        try{
            if(userName.equals("")){
                etUsername.setText("");
            }
            else{
                etUsername.setText(userName);
            }
        }catch (Exception ex){
            Log.e("Exception "," "+ex);
        }

        if(splashFlag==true)
        {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LoginLayout.setVisibility(View.VISIBLE);
                    SplashLayout.setVisibility(View.GONE);

                }
            }, 2500);
        }
        else
        {
            LoginLayout.setVisibility(View.VISIBLE);
            SplashLayout.setVisibility(View.GONE);
        }

        if(isConnectingToInternet)
        {
            CheckConnection ccAccess = new CheckConnection(getApplicationContext());

            Boolean isInternetAvailable = ccAccess.isConnectingToInternet();

            if(isInternetAvailable)
            {
                InternetCheckFlag = 1;
            }
            else
            {
                InternetCheckFlag = 0;
                Intent ConnCheck = new Intent(this,NoInternetConnection.class);
                startActivity(ConnCheck);
            }
        }
        else
        {
            InternetCheckFlag = 0;
            Intent ConnCheck = new Intent(this,NoInternetConnection.class);
            startActivity(ConnCheck);
        }

        if(InternetCheckFlag==1)
        {
            // Generate Device Id
            device_id = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
            // GCM ID
            GCMClientManager pushClientManager = new GCMClientManager(this, PROJECT_NUMBER);
            pushClientManager.registerIfNeeded(new GCMClientManager.RegistrationCompletedHandler() {
                @Override
                public void onSuccess(String registrationId, boolean isNewRegistration) {
                    reg_id = registrationId;
                    //send this registrationId to your server
                }
                @Override
                public void onFailure(String ex) {
                    super.onFailure(ex);
                }
            });
        }
        getSupportActionBar().hide();
    }

    public void Submit(View v)
    {
        etSubmit.setEnabled(false);
        if(etUsername.getText().toString().equalsIgnoreCase(""))
        {
            Toasty.warning(this, "Username should not be blank", Toast.LENGTH_LONG, true).show();
            //Toast.makeText(this,"User Name required",Toast.LENGTH_LONG).show();
            etSubmit.setEnabled(true);
        }
        else if(etPassword.getText().toString().equalsIgnoreCase(""))
        {
            Toasty.warning(this, "Password should not be blank", Toast.LENGTH_LONG, true).show();
            //Toast.makeText(this,"Password required",Toast.LENGTH_LONG).show();
            etSubmit.setEnabled(true);
        }
        else
        {
            if(InternetCheckFlag == 1)
            {
                SheredPref.setDateForApi(this,"");
                UserBean userBean = new UserBean();
                userBean.setUser_name(etUsername.getEditableText().toString().trim());
                userBean.setUser_password(etPassword.getEditableText().toString().trim());
                userBean.setUser_dev_id(device_id);
                userBean.setUser_gcm_id(reg_id);
                userBean.setDevice_os("AND");
                new PostData(new Gson().toJson(userBean), LoginScreenActivity.this, CallFor.LOGIN).execute();
                etSubmit.setEnabled(true);
            }
        }
    }

    public void chat(View v)
    {
        if (cc.isConnectingToInternet()) {
            // CleverTap
            CleverTapAPI cleverTap = null;
            try {
                cleverTap = CleverTapAPI.getInstance(this);
                HashMap<String, Object> loginAction = new HashMap<String, Object>();
                cleverTap.event.push(Events.event_login_chat, loginAction);
            } catch (CleverTapMetaDataNotFoundException e) {
                e.printStackTrace();
            } catch (CleverTapPermissionsNotSatisfied cleverTapPermissionsNotSatisfied) {
                cleverTapPermissionsNotSatisfied.printStackTrace();
            }
            Intent intent = new Intent(this, com.livechatinc.inappchat.ChatWindowActivity.class);
            intent.putExtra(com.livechatinc.inappchat.ChatWindowActivity.KEY_GROUP_ID, "");
            intent.putExtra(ChatWindowActivity.KEY_LICENCE_NUMBER, "9226615");
            this.startActivity(intent);
        }else{
            Toasty.info(this, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(LoginScreenActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setMessage("Are you sure exit from application?");
        builder.setPositiveButton("Yes, Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void loadTutorial() {
        //this.finish();
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);
    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(R.string.slide_1_vistor_title, R.string.slide_1_visitor_subtitle,
                R.color.slide_1, R.drawable.slide1,  R.drawable.slide1);

        TutorialItem tutorialItem2 = new TutorialItem(R.string.slide_2_notice_title, R.string.slide_2_notice_subtitle,
                R.color.slide_2,  R.drawable.slide2,  R.drawable.slide2);



        TutorialItem tutorialItem3 = new TutorialItem(R.string.slide_3_feedback_title, R.string.slide_3_feedback_subtitle,
                R.color.slide_3,  R.drawable.slide3,  R.drawable.slide3);

       /* TutorialItem tutorialItem3 = new TutorialItem(R.string.slide_3_feedback_title,R.string.slide_3_feedback_subtitle ,
                R.color.slide_3, R.drawable.tut_page_3_foreground);*/

        TutorialItem tutorialItem4 = new TutorialItem(R.string.slide_4_search_provider_title, R.string.slide_4_search_provider_subtitle,
                R.color.slide_4,  R.drawable.slide4, R.drawable.slide4);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);
        return tutorialItems;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            //SheredPref.setTutorialFlag(this,false);
            StartMainActivity();
        }
    }

    public void StartMainActivity(){
        Intent intent = new Intent(LoginScreenActivity.this,MainActivity.class);
        intent.putExtra("CALL","FROM_LOGIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        SheredPref.setTutorialFlag(LoginScreenActivity.this,true);
    }

    public void openWebView(View v){

        CheckConnection ccAccess = new CheckConnection(getApplicationContext());
        Boolean isInternetAvailable = ccAccess.isConnectingToInternet();

        if(isInternetAvailable)
        {
            Intent I = new Intent(this, WebviewActivity.class);
            startActivity(I);
        }
        else
        {
            Toasty.info(this, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        LoginResponse loginResponse=new Gson().fromJson(response,LoginResponse.class);
        if (response == null) {
            return;
        }
        if (callFor.equals(CallFor.LOGIN)) {
            etSubmit.setEnabled(true);
            try {
                if (loginResponse.getCode().equals("SUCCESS")){
                    strSocietyName = loginResponse.getSocity().getSocity_name();
                    strFlatNo = loginResponse.getFamily().getFlatNo();
                    wingName = loginResponse.getFamily().getWing();
                    strSocietyId = loginResponse.getFamily().getSocietyId();
                    strFamilyId = loginResponse.getFamily().getFamilyId();
                    strUsername=etUsername.getEditableText().toString().trim();
                    strPassword=etPassword.getEditableText().toString().trim();

                    SheredPref.setLoginInfo(this,"LoggedIn");
                    SheredPref.setUSername(this,strUsername);
                    SheredPref.setPassword(this,strPassword);
                    SheredPref.setSociety_Name(this,strSocietyName);
                    SheredPref.setFlat_No(this,strFlatNo);
                    SheredPref.setWingName(this,wingName);
                    SheredPref.setSocietyId(this,""+strSocietyId);
                    SheredPref.setFamilyId(this,""+strFamilyId);
                    SheredPref.setNotification(this,"ENABLE");
                    SheredPref.setAdminAccess(this,loginResponse.getFamily().isExtraAccess());
                    SheredPref.setSOSAccess(this,loginResponse.getConfigBean().isSos());
                    SheredPref.setType(this,loginResponse.getConfigBean().isCompany());
                    SheredPref.setComplaint(this,loginResponse.getConfigBean().isComplain());
                    SheredPref.setCompany(this, loginResponse.getConfigBean().isCompany());
                    SheredPref.setLocalStores(this, loginResponse.getConfigBean().isLocalStore());
                    SheredPref.setOwnerName(this,loginResponse.getFamily().getFlatOwnerName());
                    if(!SheredPref.getType(this)){
                        loadTutorial();
                    }
                    else{
                        StartMainActivity();
                    }

                    // CleverTap
                    CleverTapAPI cleverTap = null;
                    try {
                        cleverTap = CleverTapAPI.getInstance(this);
                        HashMap<String, Object> loginAction = new HashMap<String, Object>();
                        loginAction.put(Events.event_key_society_name, strSocietyName);
                        cleverTap.event.push(Events.event_login_action, loginAction);
                    } catch (CleverTapMetaDataNotFoundException e) {
                        e.printStackTrace();
                    } catch (CleverTapPermissionsNotSatisfied cleverTapPermissionsNotSatisfied) {
                        cleverTapPermissionsNotSatisfied.printStackTrace();
                    }

                    etUsername.setText("");
                    etPassword.setText("");

                }
                else if (loginResponse.getCode().equals("ERROR")) {
                    Toasty.error(this, loginResponse.getMessage(), Toast.LENGTH_LONG, true).show();
                    //Toast.makeText(this,loginResponse.getMessage(),Toast.LENGTH_SHORT).show();
                }
                else {
                    Toasty.error(this, "Please check login details", Toast.LENGTH_LONG, true).show();
                    //Toast.makeText(this,"Invalid Details",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Log.e("EXCP "," "+e);
                Toasty.error(this, "Server Error", Toast.LENGTH_LONG, true).show();
                //Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
