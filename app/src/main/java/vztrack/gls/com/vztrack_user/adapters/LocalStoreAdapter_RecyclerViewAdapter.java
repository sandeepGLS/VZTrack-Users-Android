package vztrack.gls.com.vztrack_user.adapters;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.beans.LocalStroreBean;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.LoadImage;

public class LocalStoreAdapter_RecyclerViewAdapter extends RecyclerView.Adapter<LocalStoreAdapter_RecyclerViewAdapter.MyViewHolder> {

    private ArrayList<LocalStroreBean> mDataset;
    Context context;
    String strShopName;
    String strShopDescription;
    String strShopMobileNumber;
    String strShopCategory;
    String strShopPhotoUrl;
    CheckConnection cc;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView tvShopName, tvShopCategory, tvShopMobileNumber;
        public ExpandableTextView description;
        public LinearLayout CallLayout;
        public ImageView imageViewShop;
        public View viewColorBar;

        public MyViewHolder(View view) {
            super(view);
            tvShopName = (TextView) view.findViewById(R.id.shopName);
            tvShopCategory = (TextView) view.findViewById(R.id.shopCategory);
            tvShopMobileNumber = (TextView) view.findViewById(R.id.shopMobile);
            description = (ExpandableTextView) view.findViewById(R.id.expand_text_view_shop_desc);
            viewColorBar = (View) view.findViewById(R.id.View1);
            CallLayout = (LinearLayout) view.findViewById(R.id.callLL);
            imageViewShop = (ImageView) view.findViewById(R.id.shopImage);
        }
    }

    public LocalStoreAdapter_RecyclerViewAdapter(Context con, ArrayList<LocalStroreBean> myDataset) {
        this.mDataset = myDataset;
        this.context = con;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        cc = new CheckConnection(context);
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if(mDataset.size()!=0){
            strShopName = mDataset.get(position).getStoreName();
            strShopDescription = mDataset.get(position).getSpecialDescription();
            strShopMobileNumber = mDataset.get(position).getContactNumber();
            strShopCategory = mDataset.get(position).getStoreCategoryName();
            strShopPhotoUrl = mDataset.get(position).getStoreImageUrl();

            if(strShopDescription.equals("")){
                holder.viewColorBar.setVisibility(View.GONE);
            }
            else{
                holder.viewColorBar.setVisibility(View.VISIBLE);
            }

            if(cc.isConnectingToInternet()){
                new LoadImage().loadImage(context, R.drawable.no_photo_icon, strShopPhotoUrl, holder.imageViewShop, null);
            }
            else{
                Toasty.info(context, "Unable to load Shop photo. Please check internet connection", Toast.LENGTH_SHORT, true).show();
            }
            holder.tvShopName.setText(strShopName);
            holder.tvShopCategory.setText(strShopCategory);
            holder.description.setText(strShopDescription);
            holder.tvShopMobileNumber.setText(strShopMobileNumber);

            holder.CallLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:" +strShopMobileNumber));
                    context.startActivity(call);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}