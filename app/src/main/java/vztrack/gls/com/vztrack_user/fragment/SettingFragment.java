package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.BaseActivity;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.SettingDetails;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;

public class SettingFragment extends Fragment {

    BaseActivity context;
    CheckConnection cc;

    private LinearLayout password;
    private LinearLayout notification;
    private LinearLayout info;


    public SettingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_setting, null);

        CleverTap.cleverTap_Record_Event(getActivity(), Events.event_setting_screen);

        context = MainActivity.MainContext;
        cc = new CheckConnection(context);

        password = (LinearLayout) root.findViewById(R.id.password);
        notification = (LinearLayout) root.findViewById(R.id.notification);
        info = (LinearLayout) root.findViewById(R.id.info);


        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cc.isConnectingToInternet())
                {
                    Intent I = new Intent(context, SettingDetails.class);
                    I.putExtra("FLAG", "PASS");
                    context.startActivity(I);
                }else{
                    Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent I = new Intent(context, SettingDetails.class);
                    I.putExtra("FLAG", "NOTI");
                    context.startActivity(I);
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent I = new Intent(context, SettingDetails.class);
                    I.putExtra("FLAG", "INFO");
                    context.startActivity(I);
            }
        });

        return root;
    }
}