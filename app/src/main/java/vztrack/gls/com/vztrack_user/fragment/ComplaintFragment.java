package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.adapters.Complain_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.beans.ComplainsBean;
import vztrack.gls.com.vztrack_user.beans.DataObjectComplains;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.DbHelper;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class ComplaintFragment extends Fragment {

    Context context;
    public static RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results;
    DbHelper dbHelper;
    String img_URL,heading,description,noticeStartdate,noticeEndDate;
    CheckConnection cc;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton fab;
    LinearLayout NoDataLayout;
    public  static TextView NoDataText;
    String strSelectProvider="  --- SELECT CATEGORY ---  ";
    String selectedItem;
    int position;
    Dialog dialog;
    public ComplaintFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_complaint, null);

        dbHelper = new DbHelper(getActivity());
        cc = new CheckConnection(getActivity());
        setHasOptionsMenu(true);
        context = getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.complain_recycler_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        NoDataLayout = (LinearLayout) root.findViewById(R.id.NoDataLayout);
        NoDataText = (TextView) root.findViewById(R.id.NoDataText);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());


        // get the provoder list from SharedPreference
        SharedPreferences prefs = context.getSharedPreferences("COMP_LIST",0);
        int size = prefs.getInt("comp_array_size", 0);
        final String[] list = new String[size+1];
        for(int i=0; i<size; i++){
            if(i==0){
                list[i] = strSelectProvider;
                list[i+1] = prefs.getString("comp_array_" + i, null);
            }
            else{
                list[i+1] = prefs.getString("comp_array_" + i, null);
            }
        }

        if(!SheredPref.getComplain(context)){
            NoDataLayout.setVisibility(View.VISIBLE);
            NoDataText.setText("This is disable for your society");
            mRecyclerView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }

        if(MainActivity.complainResponceBean == null){
            NoDataLayout.setVisibility(View.VISIBLE);
            NoDataText.setText("No complaints to display");
            mRecyclerView.setVisibility(View.GONE);
        }
        else{
            if(MainActivity.complainResponceBean.getComplains().size()==0){
                NoDataLayout.setVisibility(View.VISIBLE);
                NoDataText.setText("No complaints to display");
                mRecyclerView.setVisibility(View.GONE);
            }
            else{
                NoDataLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        }
        if(!cc.isConnectingToInternet())
        {
            NoDataLayout.setVisibility(View.VISIBLE);
            NoDataText.setText("Please check internet connection");
            mRecyclerView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }

        try
        {
           mAdapter = new Complain_RecyclerViewAdapter(context, getDataSet());

        }catch (Exception ex)
        {
            Log.e("Ex in Comp Frag "," "+ex);
        }

        mRecyclerView.setAdapter(mAdapter);
        if (MainActivity.pageNo != 0) {
            mRecyclerView.scrollToPosition(MainActivity.pageNo * 9);
        }
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cc.isConnectingToInternet())
                {
                    MainActivity.complian_result.clear();
                    MainActivity.pageNo = 0;
                    new GetData(MainActivity.MainContext, CallFor.GET_COMPLAIN, MainActivity.pageNo+"").execute();
                }
                else
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toasty.info(context, "Unable to refresh, Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cc.isConnectingToInternet()) {
                    // Create custom dialog object
                    dialog = new Dialog(context);
                    dialog.setCancelable(true);

                    // Include dialog.xml file
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_add_complain);
                    AppCompatSpinner comp_category_spinner = (AppCompatSpinner) dialog.findViewById(R.id.spinner_comp_providers_list);
                    final EditText description = (EditText) dialog.findViewById(R.id.description);
                    final TextView counter = (TextView) dialog.findViewById(R.id.tvCounterDesc);
                    FancyButton fancyButton = (FancyButton) dialog.findViewById(R.id.btn_Add_Comp);
                    ArrayAdapter<String> adapter =new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    comp_category_spinner.setAdapter(adapter);
                    dialog.show();
                    comp_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                            position = pos;
                            selectedItem = adapterView.getItemAtPosition(pos).toString();
                        }

                        public void onNothingSelected(AdapterView<?> adapterView) {
                            return;
                        }
                    });

                    description.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            counter.setText(editable.toString().length() + " / max 500");
                        }
                    });

                    fancyButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(position==0){
                                Toasty.info(getActivity(), "Please select category", Toast.LENGTH_SHORT, true).show();
                            }else if(description.getText().toString().trim().equals("")){
                                Toasty.info(getActivity(), "Description should not be blank", Toast.LENGTH_SHORT, true).show();
                            }else {
                                CleverTap.cleverTap_Record_Event(getActivity(), Events.event_send_complaint_button);
                                ComplainsBean complainsBean = new ComplainsBean();
                                complainsBean.setCategory(list[position]);
                                complainsBean.setDescription(description.getText().toString());
                                complainsBean.setSocity_id(Integer.parseInt(SheredPref.getSocietyId(context)));
                                complainsBean.setFamily_id(Integer.parseInt(SheredPref.getSocietyId(context)));
                                new PostData(new Gson().toJson(complainsBean), MainActivity.MainContext, CallFor.ADD_COMPLAIN).execute();
                                dialog.hide();
                            }
                        }
                    });

                    Window window = dialog.getWindow();
                    window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                            if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP)
                                dialog.dismiss();
                            return false;
                        }
                    });
                }else{
                    Toasty.info(getActivity(), "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
        return root;
    }


    private ArrayList<DataObjectComplains> getDataSet() {
        results = new ArrayList<DataObjectComplains>();
            if(cc.isConnectingToInternet())
            {
                for (int index = 0; index < MainActivity.complainResponceBean.getComplains().size(); index++) {
                    DataObjectComplains obj = new DataObjectComplains(
                            MainActivity.complainResponceBean.getComplains().get(index).getVz_comp_id(),
                            MainActivity.complainResponceBean.getComplains().get(index).getCategory(),
                            MainActivity.complainResponceBean.getComplains().get(index).getDescription(),
                            MainActivity.complainResponceBean.getComplains().get(index).getCreated_date(),
                            MainActivity.complainResponceBean.getComplains().get(index).getStatus(),
                            MainActivity.complainResponceBean.getComplains().get(index).getClosed_by()
                    );
                    results.add(index, obj);
                }
            }
        MainActivity.complian_result.addAll(results);

        return MainActivity.complian_result;
    }
}
