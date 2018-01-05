package vztrack.gls.com.vztrack_user;

/**
 * Created by sandeep on 14/3/16.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.beans.UserBean;
import vztrack.gls.com.vztrack_user.responce.LoginResponse;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class SettingDetails extends BaseActivity {

    BaseActivity context;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results;
    CheckConnection cc;
    public static EditText OldPassword,NewPassword1,NewPassword2;
    FancyButton Submit;
    public static String old_password,new_password,confirm_password;
    private CheckBox notification_checkbox;
    private AppCompatCheckBox sound_checkbox;
    private AppCompatCheckBox vibration_checkbox;
    private RelativeLayout sound_layout;
    private RelativeLayout vibrate_layout;
    private TextView username, flat_no, soc_name, owenr_name;
    private TextView flatNumber, socORcompanyName, ringtoneTitle;

    private TextView tv_version;

    private LinearLayout password, notification, information;

    String flag;
    boolean type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_details);

        getSupportActionBar().setTitle("Setting");

        context = SettingDetails.this;

        password = (LinearLayout) findViewById(R.id.layout_password);
        notification = (LinearLayout) findViewById(R.id.layout_notification);;
        information= (LinearLayout) findViewById(R.id.layout_info);

        OldPassword = (EditText) findViewById(R.id.etPasswordOld);
        NewPassword1 = (EditText) findViewById(R.id.etPasswordNew1);
        NewPassword2 = (EditText) findViewById(R.id.etPasswordNew2);
        notification_checkbox = (CheckBox) findViewById(R.id.notification_checkbox);
        sound_checkbox = (AppCompatCheckBox) findViewById(R.id.notification_sount_checkbox);
        vibration_checkbox = (AppCompatCheckBox) findViewById(R.id.notification_vibration_checkbox);
        Submit = (FancyButton) findViewById(R.id.btnChangePassword);
        vibrate_layout = (RelativeLayout) findViewById(R.id.rel_lay_vibrate);
        sound_layout = (RelativeLayout) findViewById(R.id.rel_lay_sound);
        tv_version = (TextView) findViewById(R.id.version);

        flatNumber = (TextView) findViewById(R.id.flatNumber);
        socORcompanyName = (TextView) findViewById(R.id.socORcompanyName);

        username = (TextView) findViewById(R.id.tv_username);
        flat_no =(TextView) findViewById(R.id.tv_flat_no) ;
        soc_name = (TextView) findViewById(R.id.tv_soc_name);
        owenr_name = (TextView) findViewById(R.id.tv_owner_name);
        ringtoneTitle = (TextView) findViewById(R.id.ringtoneTitle);


        if(!SheredPref.getRingtoneTitle(context).equals("")){
            ringtoneTitle.setText(SheredPref.getRingtoneTitle(context));
        }

        soc_name.setText(SheredPref.getSociety_Name(this));
        flat_no.setText(SheredPref.getFlat_No(this));
        username.setText(SheredPref.getUsername(this));
        owenr_name.setText(SheredPref.getOwnerName(this));

        type = SheredPref.getType(this);
        if(type){
            flatNumber.setText("Employee Id");
            socORcompanyName.setText("Company Name");
        }
        else{
            flatNumber.setText("Flat Number");
            socORcompanyName.setText("Society Name");
        }

        //int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        tv_version.setText("Current Version : "+versionName);


        flag = getIntent().getStringExtra("FLAG");

        if(flag.equals("PASS")){
            password.setVisibility(View.VISIBLE);
            notification.setVisibility(View.GONE);
            information.setVisibility(View.GONE);
        }
        else if(flag.equals("NOTI")){
            password.setVisibility(View.GONE);
            notification.setVisibility(View.VISIBLE);
            information.setVisibility(View.GONE);
        }
        else if(flag.equals("INFO")){
            password.setVisibility(View.GONE);
            notification.setVisibility(View.GONE);
            information.setVisibility(View.VISIBLE);
        }else{
            Toasty.info(this, "Invalid selection", Toast.LENGTH_SHORT, true).show();
        }


        // Check Internet Connection
        cc = new CheckConnection(this);


        if(SheredPref.getNotification(this).equals("ENABLE"))
        {
            notification_checkbox.setChecked(true);
        }
        else {
            notification_checkbox.setChecked(false);
        }

        if(SheredPref.getSound(this).equals("ENABLE"))
        {
            sound_checkbox.setChecked(true);
        }
        else {
            sound_checkbox.setChecked(false);
        }

        if(SheredPref.getVibration(this).equals("ENABLE"))
        {
            vibration_checkbox.setChecked(true);
        }
        else {
            vibration_checkbox.setChecked(false);
        }

        notification_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    SheredPref.setNotification(context,"ENABLE");
                    sound_checkbox.setChecked(true);
                    vibration_checkbox.setChecked(true);
                    SheredPref.setSound(context,"ENABLE");
                    SheredPref.setVibration(context,"ENABLE");
                    sound_layout.setVisibility(View.VISIBLE);
                    vibrate_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Notification");
                    builder.setMessage("This will stop VZ Track notification,\nDo you want to stop receiving notifications?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            notification_checkbox.setChecked(false);
                            sound_checkbox.setChecked(false);
                            vibration_checkbox.setChecked(false);

                            sound_layout.setVisibility(View.GONE);
                            vibrate_layout.setVisibility(View.GONE);

                            SheredPref.setNotification(context,"DISABLE");
                            SheredPref.setSound(context,"DISABLE");
                            SheredPref.setVibration(context,"DISABLE");
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {
                            notification_checkbox.setChecked(true);
                            //Log.e("DISMISS "," DIALOG");
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
            }
        });
        sound_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    SheredPref.setSound(context,"ENABLE");
                }
                else
                {
                    SheredPref.setSound(context,"DISABLE");
                }


            }
        });
        vibration_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    SheredPref.setVibration(context,"ENABLE");
                }
                else
                {
                    SheredPref.setVibration(context,"DISABLE");
                }
            }
        });

        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //SOME CODE
            }
        };


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cc.isConnectingToInternet())
                {
                    old_password = OldPassword.getText().toString().trim();
                    new_password = NewPassword1 .getText().toString().trim();
                    confirm_password = NewPassword2.getText().toString().trim();

                    if(old_password.equals("")){
                        Toasty.info(context, "Old password should not be blank", Toast.LENGTH_SHORT, true).show();
                    }
                    else if(new_password.equals("")){
                        Toasty.info(context, "New password should not be blank", Toast.LENGTH_SHORT, true).show();
                    }
                    else if(confirm_password.equals("")){
                        Toasty.info(context, "Confirm password password should not be blank", Toast.LENGTH_SHORT, true).show();
                    }
                    else if( SheredPref.getPassword(context).equals(old_password))
                    {
                        if( new_password.equals(confirm_password))
                        {

                            if(old_password.equals(new_password) && old_password.equals(confirm_password)){
                                Toasty.info(context, "Old password and new password should not be same", Toast.LENGTH_SHORT, true).show();
                            }else{
                                if( new_password.length()>=6)
                                {
                                    new GetData(context, CallFor.CHANGE_PASSWORD, new_password).execute();
                                }
                                else
                                {
                                    Toasty.info(context, "Password length should be greater than 5", Toast.LENGTH_SHORT, true).show();
                                }
                            }
                        }
                        else
                        {
                            Toasty.error(context, "New password mismatch", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                    else
                    {
                        Toasty.error(context, "Old password mismatch", Toast.LENGTH_SHORT, true).show();
                    }
                }
                else
                {
                    Toasty.error(context, "Unable to change password, Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

    }

    public void customeNotificationSound(View v){
        final Uri currentTone= Uri.parse(SheredPref.getNotificationRingtone(this));
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_NOTIFICATION);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Tone For VZTrack");
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentTone);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true);
        this.startActivityForResult(intent, 5);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent intent)
    {
        if (resultCode == Activity.RESULT_OK && requestCode == 5)
        {
            Uri uri = intent.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone tone = RingtoneManager.getRingtone(this, uri);
            String title = tone.getTitle(this);

            if (uri != null)
            {
                String ringtone = uri.toString();
                SheredPref.setNotificationRingtone(this ,ringtone);
                SheredPref.setRingtoneTitle(this ,title);
                ringtoneTitle.setText(title);
            }
            else
            {
                Toasty.error(this, "Unable to set notification ringtone", Toast.LENGTH_SHORT, true).show();
            }
        }
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        LoginResponse loginResponse = null;
        if (callFor.equals(CallFor.LOGIN)) {
            loginResponse = new Gson().fromJson(response, LoginResponse.class);
            try {
                if (loginResponse.getCode().equals("SUCCESS")) {
                    new_password = NewPassword1 .getText().toString().trim();
                    new GetData(context, CallFor.CHANGE_PASSWORD, new_password).execute();
                }
                else{
                    Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Setting Details "," "+ex);
            }
        }
        if (callFor.equals(CallFor.CHANGE_PASSWORD)) {
            loginResponse=new Gson().fromJson(response,LoginResponse.class);
            try {
                if (loginResponse.getCode().equals("SUCCESS"))
                {
                    CleverTap.cleverTap_Record_Event(this, Events.event_setting_change_password);
                    Toasty.success(this, "Password changed successfully", Toast.LENGTH_SHORT, true).show();
                    SheredPref.setPassword(this,new_password);
                    OldPassword.setText("");
                    NewPassword1.setText("");
                    NewPassword2.setText("");
                    getSupportActionBar().setTitle("Visitors");
                    this.finish();
                    //new GetData(MainActivity.this, CallFor.VISITORS, ""+visitor_PageNo).execute();
                }
                else if(loginResponse.getCode().equals("NEED_LOGIN"))
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                }
                else
                {
                    Toasty.error(this, "Error in password change, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex","");
            }
        }

    }

}