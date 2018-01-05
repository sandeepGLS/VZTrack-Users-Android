package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.SendMessage;
import vztrack.gls.com.vztrack_user.adapters.Message_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.beans.DataObjectMessage;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class MessageFragment extends Fragment {

    Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results;
    CheckConnection cc;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    private FloatingActionButton fab;
    LinearLayout NoDataLayout;
    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_message, null);

        CleverTap.cleverTap_Record_Event(getActivity(), Events.event_message_screen);

        cc = new CheckConnection(getActivity());
        setHasOptionsMenu(true);
        context = getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.notice_recycler_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);
        NoDataLayout = (LinearLayout) root.findViewById(R.id.NoDataLayout);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        if(MainActivity.messageResponceBean == null || MainActivity.messageResponceBean.getMessageBeans().size()==0){
            if(MainActivity.message_PageNo==0){
                NoDataLayout.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }
        else{
            NoDataLayout.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

        try
        {
            mAdapter = new Message_RecyclerViewAdapter(context, getDataSet("All"));
        }catch (Exception ex)
        {
            Log.e("Ex in Message Frag"," "+ex);
        }

        mRecyclerView.setAdapter(mAdapter);

        if (MainActivity.message_PageNo != 0) {
            mRecyclerView.scrollToPosition(MainActivity.message_PageNo * 10);
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cc.isConnectingToInternet())
                {
                    MainActivity.fragment_flag=1;
                    MainActivity.updatedMessageResponceBean.clear();
                    MainActivity.message_PageNo = 0;
                    new GetData(MainActivity.MainContext, CallFor.MESSAGE, ""+0).execute();
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
                // Click action
                if(cc.isConnectingToInternet())
                {
                    Intent intent = new Intent(context, SendMessage.class);
                    startActivity(intent);
                }
                else{
                    Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });

        if(!SheredPref.getAdminAccess(getActivity())){
            fab.setVisibility(View.GONE);
        }
        return root;
    }


    private ArrayList<DataObjectMessage> getDataSet(String startWith) {
        results = new ArrayList<DataObjectMessage>();
        for (int index = 0; index < MainActivity.messageResponceBean.getMessageBeans().size(); index++) {
            DataObjectMessage obj = new DataObjectMessage(
                    MainActivity.messageResponceBean.getMessageBeans().get(index).getMessage(),
                    MainActivity.messageResponceBean.getMessageBeans().get(index).getGroupName(),
                    MainActivity.messageResponceBean.getMessageBeans().get(index).getSent_date()
            );
            results.add(obj);
        }
        MainActivity.updatedMessageResponceBean.addAll(results);
        return MainActivity.updatedMessageResponceBean;
    }
}
