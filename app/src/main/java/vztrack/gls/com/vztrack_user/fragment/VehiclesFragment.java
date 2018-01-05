package vztrack.gls.com.vztrack_user.fragment;

/**
 * Created by sandeep on 14/3/16.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.adapters.ViewPagerAdapter;

public class VehiclesFragment extends Fragment {

    //This is our tablayout
    private TabLayout tabLayout;

    //This is our viewPager
    private ViewPager viewPager;

    //Fragments

    SearchVehicleFragment searchVehicleFragment;
    AddvehicleFragment addvehicleFragment;

    public VehiclesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_vehicles, null);


        //Initializing viewPager
        viewPager = (ViewPager) root.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);

        //Initializing the tablayout
        tabLayout = (TabLayout) root.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSaveFromParentEnabled(false);
        setupViewPager(viewPager);
        return root;
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        searchVehicleFragment=new SearchVehicleFragment();
        addvehicleFragment=new AddvehicleFragment();
        adapter.addFragment(searchVehicleFragment,"Search Vehicle");
        adapter.addFragment(addvehicleFragment,"Add Vehicle");
        viewPager.setAdapter(adapter);
    }
}