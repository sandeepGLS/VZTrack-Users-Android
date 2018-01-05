package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import io.ghyeok.stickyswitch.widget.StickySwitch;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.BaseActivity;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

import static android.content.ContentValues.TAG;

public class AddvehicleFragment extends Fragment {

    BaseActivity context;
    CheckConnection cc;
    StickySwitch mSwitch;
    boolean switchState;
    FancyButton btnAddButton;
    int vehicleType;
    public static EditText vehicleNumber;
    public static TextView no_Data;
    public static ListView vehicle_List;
    public static ListAdapter mAdapter;

    public AddvehicleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_add_vehicle, null);
        mSwitch = (StickySwitch) root.findViewById(R.id.vehicleTypeSwitch);
        btnAddButton = (FancyButton) root.findViewById(R.id.btnAddButton);
        vehicleNumber = (EditText) root.findViewById(R.id.vehicleNumber);
        no_Data = (TextView) root.findViewById(R.id.no_Data);
        vehicle_List = (ListView) root.findViewById(R.id.vehicle_List);

        cc = new CheckConnection(getActivity());
        vehicleType =2;
        if(cc.isConnectingToInternet()){
            new GetData(MainActivity.MainContext, CallFor.GET_VEHICLES,"").execute();
            //getFlatVehicles
        }else{
            Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }

        mSwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                Log.d(TAG, "Now Selected : " + direction.name() + ", Current Text : " + text);
                if(direction.name().equals("LEFT")){
                    vehicleType =2;
                }else{
                    vehicleType =4;
                }
            }
        });

        btnAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strVehicleNumber = vehicleNumber.getText().toString().trim();
                if(strVehicleNumber.equals("")){
                    Toasty.info(context, "Please enter vehicle number", Toast.LENGTH_SHORT, true).show();
                }else{
                    boolean flag = false;
                    String all_vehicle = SheredPref.getVehicleNo_for_add(context);
                    all_vehicle = all_vehicle.replace(",","\n");
                    String all_pattern = SheredPref.getVehiclePatternNo(context);
                    String[] all_pattern_array = all_pattern.split("@");
                    String all_flag[] = SheredPref.getVehicleFlag(context).split(",");

                    for(int i=0;i<all_pattern_array.length;i++){
                        if(all_flag[i].trim().equals("0")){
                            String strPattern = all_pattern_array[i].trim();
                            Pattern pattern = Pattern.compile(strPattern);
                            Matcher matcher = pattern.matcher(strVehicleNumber);
                            if (matcher.find()) {
                                flag = true;
                            }
                        }
                    }

                    if(flag){
                        if(cc.isConnectingToInternet()){
                            CleverTap.cleverTap_Record_Event(getActivity(), Events.event_add_vehicle_button);
                            String strVehicleNo = vehicleNumber.getText().toString().trim().replaceAll("\\s+","%20");
                            String parm = "?vehicleType="+vehicleType+"&vehicleNumber="+strVehicleNo;
                            new GetData(MainActivity.MainContext, CallFor.ADD_VEHICLE,""+parm).execute();
                        }else{
                            Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                        }
                    }else{
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Invalid Vehicle Number")
                                .setContentText("Please enter vehicle number in following format\n"+all_vehicle)
                                .show();
                    }
                }
            }
        });


        context = MainActivity.MainContext;
        return root;
    }
}