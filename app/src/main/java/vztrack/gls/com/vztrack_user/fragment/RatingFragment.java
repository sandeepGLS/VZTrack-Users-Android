package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.adapters.Rating_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.beans.DataObjectRating;
import vztrack.gls.com.vztrack_user.beans.RatingResponseBean;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.DbHelper;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class RatingFragment extends Fragment {
    ArrayList searched_results = new ArrayList<DataObjectRating>();
    Context context;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList results;
    DbHelper dbHelper;
    String img_URL,heading,description,noticeStartdate,noticeEndDate;
    CheckConnection cc;
    public static SwipeRefreshLayout mSwipeRefreshLayout;
    public static int clearFlag = 0;
    public RatingFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_rating, null);

        CleverTap.cleverTap_Record_Event(getActivity(), Events.event_rating_screen);

        dbHelper = new DbHelper(getActivity());
        cc = new CheckConnection(getActivity());
        setHasOptionsMenu(true);
        context = getActivity();
        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.activity_main_swipe_refresh_layout);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.rating_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (clearFlag == 1) {
            MainActivity.Updated_result_rating.clear();
            clearFlag = 0;
        }
        try
        {
            mAdapter = new Rating_RecyclerViewAdapter(context, getDataSet("All"));
        }catch (Exception ex)
        {
            Log.e("Ex in Rating Frag "," "+ex);
        }

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(cc.isConnectingToInternet())
                {
                    clearFlag = 1;
                    MainActivity.fragment_flag=1;
                    RatingResponseBean ratingResponseBean = new RatingResponseBean();
                    ratingResponseBean.setSocietyId(Integer.parseInt(SheredPref.getSocietyId(context)));
                    ratingResponseBean.setFamilyId(Integer.parseInt(SheredPref.getFamilyId(context)));
                    new PostData(new Gson().toJson(ratingResponseBean), MainActivity.MainContext, CallFor.PENDING_RATING).execute();
                }
                else
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toasty.info(context, "Unable to refresh, Please check internet connection", Toast.LENGTH_SHORT, true).show();
                   // Toast.makeText(context,"Unable To Refresh , No Internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.main, menu);
        setHasOptionsMenu(true);
        menu.findItem(R.id.action_call).setVisible(false);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setQueryHint("Search By Name");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                query = query.toLowerCase();
                searched_results.clear();
                ArrayList filteredList;
                filteredList = getDataSet(query);
                try
                {
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(context);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mAdapter = new Rating_RecyclerViewAdapter(context,filteredList);
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
                catch (Exception ex)
                {
                    Log.e("Ex in Rating Frag "," "+ex);
                }
                return true;
            }
        });
    }


    private ArrayList<DataObjectRating> getDataSet(String startWith) {
        results = new ArrayList<DataObjectRating>();
        if (startWith.equals("All") || startWith.equals("")) {
            if (startWith.equals("")) {
                MainActivity.Updated_result_rating.addAll(results);
                return MainActivity.Updated_result_rating;
            } else {
                if (cc.isConnectingToInternet()) {
                    for (int index = 0; index < MainActivity.ratingBeanArrayList.size(); index++) {
                        DataObjectRating obj = new DataObjectRating(
                                MainActivity.ratingBeanArrayList.get(index).getVisitorName(),
                                MainActivity.ratingBeanArrayList.get(index).getVisitorMobile(),
                                MainActivity.ratingBeanArrayList.get(index).getVisitorPurpose(),
                                MainActivity.ratingBeanArrayList.get(index).getInTime(),
                                MainActivity.ratingBeanArrayList.get(index).getVisitorPhoto()
                        );
                        results.add(obj);
                    }
                } else {
                }
                MainActivity.Updated_result_rating.addAll(results);
                return MainActivity.Updated_result_rating;
            }
        } else {
            if (cc.isConnectingToInternet()) {
                for (int index = 0; index < MainActivity.Updated_result_rating.size(); index++) {
                    DataObjectRating obj = MainActivity.Updated_result_rating.get(index);
                    String check_name = obj.getMvisitorName();
                    if (check_name.toLowerCase().startsWith(startWith) || check_name.toLowerCase().contains(startWith)) {
                        DataObjectRating objNew = new DataObjectRating(
                                obj.getMvisitorName(),
                                obj.getMvisitorMobile(),
                                obj.getMvisitorPurpose(),
                                obj.getMinTime(),
                                obj.getMvisitorPhoto()
                        );
                        results.add(objNew);
                    }
                }
            } else {
            }
            searched_results.addAll(results);
            return searched_results;
        }
    }
}
