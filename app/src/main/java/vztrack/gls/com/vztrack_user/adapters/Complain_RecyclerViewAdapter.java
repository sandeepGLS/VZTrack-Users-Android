package vztrack.gls.com.vztrack_user.adapters;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.beans.DataObjectComplains;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;

public class Complain_RecyclerViewAdapter extends RecyclerView.Adapter<Complain_RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<DataObjectComplains> mDataset;
    Context context;
    int complain_id;
    String strCategory;
    String strDescription;
    String strStatus;
    String strCloasedBy;
    String strDate;
    CheckConnection cc;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView category, date;
        public ExpandableTextView description;
        public FancyButton btnClose;
        public TextView btnDisableClose;
        public View viewColorBar;

        public MyViewHolder(View view) {
            super(view);
            category = (TextView) view.findViewById(R.id.tvCategory);
            description = (ExpandableTextView) view.findViewById(R.id.expand_text_view);
            date = (TextView) view.findViewById(R.id.tvDate);
            btnClose = (FancyButton) view.findViewById(R.id.btnCloseButton);
            btnDisableClose = (TextView) view.findViewById(R.id.btnCloseDisableButton);
            viewColorBar = (View) view.findViewById(R.id.viewColorBar);
        }
    }

    public Complain_RecyclerViewAdapter(Context con, ArrayList<DataObjectComplains> myDataset) {
        this.mDataset = myDataset;
        this.context = con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.complaint_card, parent, false);
        cc = new CheckConnection(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        strCategory = mDataset.get(position).getmTextCategory();
        strDescription = mDataset.get(position).getmTextdescription();
        strDate = mDataset.get(position).getmTextDate();
        strStatus = mDataset.get(position).getmTextStatus();
        strCloasedBy = mDataset.get(position).getmTextClosedBy();

        if(strStatus.equals("CLOSE")){
            holder.btnClose.setVisibility(View.GONE);
            holder.btnDisableClose.setVisibility(View.VISIBLE);
            holder.btnDisableClose.setText("Closed By : "+strCloasedBy);
            holder.viewColorBar.setBackgroundColor(Color.parseColor("#ff4d4d"));
        }
        else{
            holder.btnClose.setVisibility(View.VISIBLE);
            holder.btnDisableClose.setVisibility(View.GONE);
            holder.viewColorBar.setBackgroundColor(Color.parseColor("#1F96F2"));
        }

        holder.category.setText(strCategory);
        holder.description.setText(strDescription);
        holder.date.setText(strDate);
        holder.btnClose.setTag(position);

        int intPos= position+1;
        int intPage= MainActivity.pageNo+1;
        int intmodVal = intPage*10;
        if((intPos)%intmodVal==0 && mDataset.get(position).getmTextStatus().equals("OPEN")){
            MainActivity.pageNo++;
            new GetData(MainActivity.MainContext, CallFor.GET_COMPLAIN, MainActivity.pageNo+"").execute();
        }

        if(cc.isConnectingToInternet()){
            holder.btnClose.setOnClickListener(clickListener);
        }
        else{
            Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CleverTap.cleverTap_Record_Event(context, Events.event_close_complaint_button);
            int pos= (int) view.getTag();
            complain_id = mDataset.get(pos).getmTextComplainId();
            new GetData(MainActivity.MainContext, CallFor.CLOSE_COMPLAIN,""+complain_id).execute();
        }
    };

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}