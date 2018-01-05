package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.BaseActivity;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class SearchVehicleFragment extends Fragment {

    BaseActivity context;
    CheckConnection cc;
    public static EditText vehicleNumber;
    ImageView searchIcon;
    public static RecyclerView searched_vehicle_recycler_view;
    public static TextView noData;
    public static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static CardView card_view;
    public static TextView res;

    public SearchVehicleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_search_vehicle, null);
        context = MainActivity.MainContext;

        vehicleNumber = (EditText) root.findViewById(R.id.vehicleNumber);
        searchIcon = (ImageView) root.findViewById(R.id.searchIcon);
        card_view = (CardView) root.findViewById(R.id.card_view);
        res = (TextView) root.findViewById(R.id.res);
        res.setVisibility(View.GONE);
        card_view.setVisibility(View.GONE);

        searched_vehicle_recycler_view = (RecyclerView) root.findViewById(R.id.searched_vehicle_recycler_view);
        noData = (TextView) root.findViewById(R.id.noResultLayout);

        cc = new CheckConnection(getActivity());

        String currentDateString = DateFormat.getDateInstance().format(new Date());
        if(SheredPref.getDateForVehiclePattern(context).equals("")){
            new GetData(MainActivity.MainContext, CallFor.VEHICLENOPATTERN,"").execute();
            SheredPref.setDateForVehiclePattern(context,currentDateString);
        }else if(!currentDateString.equals(SheredPref.getDateForVehiclePattern(context))){
            new GetData(MainActivity.MainContext, CallFor.VEHICLENOPATTERN,"").execute();
            SheredPref.setDateForVehiclePattern(context,currentDateString);
        }

        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strVehicleNumber =  vehicleNumber.getText().toString().trim();
                if(strVehicleNumber.equals("")){
                    Toasty.info(context, "Please enter vehicle number", Toast.LENGTH_SHORT, true).show();
                }else{
                    boolean flag = false;
                    String all_vehicle = SheredPref.getVehicleNo_for_search(context);
                    all_vehicle = all_vehicle.replace(",","\n");
                    String all_pattern = SheredPref.getVehiclePatternNo(context);
                    String[] all_pattern_array = all_pattern.split("@");
                    for(int i=0;i<all_pattern_array.length;i++){
                        String strPattern =all_pattern_array[i].trim();
                        Pattern pattern = Pattern.compile(strPattern);
                        Matcher matcher = pattern.matcher(strVehicleNumber);
                        if (matcher.find()) {
                            flag = true;
                        }
                    }

                    if(flag){
                        if(cc.isConnectingToInternet()){
                            CleverTap.cleverTap_Record_Event(getActivity(), Events.event_search_vehicle_button);
                            String strVehicleNo =  vehicleNumber.getText().toString().trim().replaceAll("\\s+","%20");
                            new GetData(MainActivity.MainContext, CallFor.SEARCH_VEHICLE,""+strVehicleNo).execute();
                            res.setText("Searched result for vehicle number '"+vehicleNumber.getText().toString().trim()+"'");
                            vehicleNumber.setText("");
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
        return root;
    }
}