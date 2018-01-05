package vztrack.gls.com.vztrack_user.adapters;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.beans.SearchVehicleBean;
import vztrack.gls.com.vztrack_user.fragment.SearchVehicleFragment;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class Search_Vehicles_RecyclerViewAdapter extends RecyclerView.Adapter<Search_Vehicles_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    private String strOwnerType;
    private String strFlatNoAndOwnerName;
    private String strVehicleTypeAndTime, strVehicleNumber;
    CheckConnection cc;
    private ArrayList<SearchVehicleBean> searchVehicleBean;


    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvFlatNoAndOwnerName;
        public TextView tvVehicleTypeAndTime, tvVehicleNumber;
        public ImageView imgType;

        public MyViewHolder(View view) {
            super(view);
            tvFlatNoAndOwnerName = (TextView) view.findViewById(R.id.tvFlatNo_OwnerName);
            tvVehicleTypeAndTime = (TextView) view.findViewById(R.id.tvVehicleType_Time);
            tvVehicleNumber = (TextView) view.findViewById(R.id.tvVehicleNumber);
            imgType = (ImageView) view.findViewById(R.id.imgType);

        }
    }

    public Search_Vehicles_RecyclerViewAdapter(Context con, ArrayList<SearchVehicleBean> searchVehicleBeen) {
        this.searchVehicleBean = searchVehicleBeen;
        this.context = con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_vehicle_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        strOwnerType = searchVehicleBean.get(position).getOwnerType();
        strFlatNoAndOwnerName = searchVehicleBean.get(position).getFlatNo_OR_name();
        strVehicleTypeAndTime = searchVehicleBean.get(position).getTime_OR_vehicleType();
        strVehicleNumber = searchVehicleBean.get(position).getVehicleNumber();
        SearchVehicleFragment.card_view.setVisibility(View.VISIBLE);
        boolean type = SheredPref.getType(context);
        // If type true then this is Company
        if(strOwnerType.equals("Visitor")){
            if(type){
                holder.tvFlatNoAndOwnerName.setText("Employee Id : "+strFlatNoAndOwnerName);
            }else{
                holder.tvFlatNoAndOwnerName.setText("Flat No : "+strFlatNoAndOwnerName);
            }
            holder.tvVehicleTypeAndTime.setText("Visited Date And Time : "+strVehicleTypeAndTime);
            holder.imgType.setVisibility(View.GONE);
        }else{
            if(type){
                holder.tvFlatNoAndOwnerName.setText("Employee Id : "+strFlatNoAndOwnerName);
            }else{
                holder.tvFlatNoAndOwnerName.setText("Flat No : "+strFlatNoAndOwnerName);
            }
            holder.tvVehicleTypeAndTime.setText("Vehicle Type : ");

            holder.imgType.setVisibility(View.VISIBLE);
            if(strVehicleTypeAndTime.equals("2")){
                holder.imgType.setBackgroundResource(R.drawable.ic_action_bike);
            }else{
                holder.imgType.setBackgroundResource(R.drawable.ic_action_car);
            }

            boolean typeSoc = SheredPref.getType(context);
            // If type true then this is Company
        }
        holder.tvVehicleNumber.setText("Vehicle Number : "+strVehicleNumber);
    }

    @Override
    public int getItemCount() {
        return searchVehicleBean.size();
    }

}