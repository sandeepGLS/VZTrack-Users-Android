package vztrack.gls.com.vztrack_user.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.adapters.LocalStoreAdapter_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.beans.LocalStroreBean;

public class LocalStoreFragment extends Fragment {
    public static RecyclerView local_store_recycler_view;
    public static ArrayList<LocalStroreBean> localStores =new ArrayList<>();
    public static RecyclerView.Adapter localStoreAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public static LinearLayout NoDataLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_local_store, null);
        local_store_recycler_view = (RecyclerView) root.findViewById(R.id.local_store_recycler_view);
        NoDataLayout = (LinearLayout) root.findViewById(R.id.NoDataLayout);
        localStores = new ArrayList<>();
        localStoreAdapter = new LocalStoreAdapter_RecyclerViewAdapter(getActivity(), localStores);
        mLayoutManager = new LinearLayoutManager(getActivity());
        local_store_recycler_view.setLayoutManager(mLayoutManager);
        local_store_recycler_view.setItemAnimator(new DefaultItemAnimator());
        local_store_recycler_view.setAdapter(localStoreAdapter);
        return  root;
    }
}
