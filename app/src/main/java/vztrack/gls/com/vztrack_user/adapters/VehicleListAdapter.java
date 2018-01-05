package vztrack.gls.com.vztrack_user.adapters;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.beans.VehicleBean;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.GetData;

public class VehicleListAdapter extends ArrayAdapter<VehicleBean> implements View.OnClickListener{

    private ArrayList<VehicleBean> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView tvVehicleNumber;
        ImageView tvVehicleType;
        ImageView imgDelete;
    }

    public VehicleListAdapter(ArrayList<VehicleBean> data, Context context) {
        super(context, R.layout.vehicles_list, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {
        int position=(Integer) v.getTag();
        Object object= getItem(position);
        VehicleBean dataModel=(VehicleBean)object;
        switch (v.getId())
        {
            case R.id.imgDelete:
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final VehicleBean dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.vehicles_list, parent, false);
            viewHolder.tvVehicleNumber = (TextView) convertView.findViewById(R.id.vehicleNumber);
            viewHolder.tvVehicleType = (ImageView) convertView.findViewById(R.id.vehicleType);
            viewHolder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvVehicleNumber.setText(dataModel.getVehicleNumber());
        if(dataModel.getVehicleType().equals("2")){
            viewHolder.tvVehicleType.setBackgroundResource(R.drawable.ic_action_bike);
        }else{
            viewHolder.tvVehicleType.setBackgroundResource(R.drawable.ic_action_car);
        }

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String param = "?vehicleNumber="+dataModel.getVehicleNumber().replaceAll("\\s+","%20");
                new GetData(MainActivity.MainContext, CallFor.DELETE_VEHICLE,""+param).execute();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }
}