package vztrack.gls.com.vztrack_user.adapters;

/**
 * Created by sandeep on 14/3/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import vztrack.gls.com.vztrack_user.MainActivity;
import vztrack.gls.com.vztrack_user.R;
import vztrack.gls.com.vztrack_user.Visiters_Details;
import vztrack.gls.com.vztrack_user.beans.DataObjectVisitors;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.Constants;
import vztrack.gls.com.vztrack_user.utils.DbHelper;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.LoadImage;

public class Visitors_RecyclerViewAdapter extends RecyclerView.Adapter<Visitors_RecyclerViewAdapter.DataObjectHolder> {
    private static String LOG_TAG = "Visitors_RecyclerViewAdapter";
    private ArrayList<DataObjectVisitors> mDataset;
    private Context context;
    public String strName,strMobile,strDateTime,strPurpose,strFrom,strPhotUrl,strDocUrl;
    CheckConnection cc;
    int size;
    private final int TITLE = 0;
    private final int LOAD_MORE = 1;
    private boolean hasLoadButton = true;
    public static String photo[]=new String[10];
    DbHelper dbHelper;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView tvName;
        public TextView tvdateTime;
        public TextView tvPurpose;
        public TextView tvMobile, tvFrom;
        public ImageView imgProfilePic;
        public static CardView cardView;
        public ImageView imgMarkError;
        public LinearLayout linearLayout;
        public Button btnLoadMore;
        public LinearLayout cardview_Linear;
        public ImageView imgIncorrectLable;
        public LinearLayout imgMark;

        public DataObjectHolder(View itemView) {
            super(itemView);
            imgProfilePic = (ImageView)itemView.findViewById(R.id.circleView);
            tvName = (TextView) itemView.findViewById(R.id.tvTitle);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
            tvdateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
            tvPurpose = (TextView) itemView.findViewById(R.id.tvPurpose);
            tvFrom = (TextView) itemView.findViewById(R.id.tvFrom);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            imgMarkError = (ImageView) itemView.findViewById(R.id.imgMarkError);
            btnLoadMore = (Button) itemView.findViewById(R.id.load_more);
            cardview_Linear = (LinearLayout) itemView.findViewById(R.id.cardview_Linear);
            imgIncorrectLable = (ImageView) itemView.findViewById(R.id.imgIncorrectLable);
            imgMark = (LinearLayout) itemView.findViewById(R.id.imgMark);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }


    public Visitors_RecyclerViewAdapter(Context con, ArrayList<DataObjectVisitors> myDataset) {
        context = con;
        mDataset = myDataset;
        size = mDataset.size();
        dbHelper = new DbHelper(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount()-1) {
            return TITLE;
        } else {
            return LOAD_MORE;
        }
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        if (viewType == TITLE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.visiters_list, parent, false);
            CardView cv = (CardView) view.findViewById(R.id.card_view);
            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            cc = new CheckConnection(context);
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent I = new Intent(context, Visiters_Details.class);
                    context.startActivity(I);
                }
            });
            return dataObjectHolder;
        } else if (viewType == LOAD_MORE) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more_row, parent, false);
            Button Load_m_button = (Button) v.findViewById(R.id.load_more);
            if(mDataset.size()%10 !=0 || mDataset.size()==0) {
                Load_m_button.setVisibility(View.GONE);
            }
            else if(MainActivity.visitor_PageNo!=0 && MainActivity.visitorsArray!=null && MainActivity.visitorsArray.getVisitors().size()==0){
                Toasty.info(context, "You have reached last visitor...", Toast.LENGTH_SHORT, true).show();
                Load_m_button.setVisibility(View.GONE);
            }
            else
            {
                Load_m_button.setVisibility(View.VISIBLE);
            }
            return new DataObjectHolder(v);
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        final int pos = position;
        holder.setIsRecyclable(false);
        if(position >= getItemCount()-1) {
            holder.btnLoadMore.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    holder.btnLoadMore.setBackgroundColor(Color.parseColor("#013220"));
                    return false;
                }
            });
            holder.btnLoadMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.visitor_PageNo = MainActivity.visitor_PageNo+1;
                    if(cc.isConnectingToInternet())
                    {
                        new GetData(MainActivity.MainContext, CallFor.VISITORS, ""+MainActivity.visitor_PageNo).execute();
                    }
                    else
                    {
                        holder.btnLoadMore.setVisibility(View.GONE);
                        Toasty.info(context, "Unable to load more visitors, Please check internet connection", Toast.LENGTH_SHORT, true).show();
                    }
                }
            });
        } else {
            strName = mDataset.get(position).getmFirst_Name()+" "+mDataset.get(position).getmLast_Name();
            strMobile = mDataset.get(position).getmMobile();
            strDateTime = mDataset.get(position).getmTime();
            strPurpose = mDataset.get(position).getmPurpose();
            strFrom = mDataset.get(position).getmFrom();

            holder.tvName.setText(strName);
            holder.tvMobile.setText(strMobile);
            holder.tvdateTime.setText(strDateTime);
            holder.tvPurpose.setText(strPurpose);
            holder.tvFrom.setText(strFrom);

            if(mDataset.get(position).ismError_flag()){
                holder.cardview_Linear.setAlpha(.5f);
                holder.cardview_Linear.setBackgroundColor(Color.parseColor("#E8E8E8"));
                holder.imgIncorrectLable.setVisibility(View.VISIBLE);
            }
            else{
                holder.cardview_Linear.setAlpha(1f);
                holder.cardview_Linear.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.imgIncorrectLable.setVisibility(View.GONE);
            }

            if(position == 0){
                holder.imgMark.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.move);
                holder.imgMark.startAnimation(anim);
            }


            if(cc.isConnectingToInternet())
            {
                strPhotUrl = Constants.BASE_IMG_URL+"/"+mDataset.get(position).getmPhoto();
                strDocUrl = Constants.BASE_IMG_URL+"/"+mDataset.get(position).getmDocUrl();
                new LoadImage().loadImage(context, R.drawable.no_photo_icon, strPhotUrl ,  holder.imgProfilePic, null);
            }
            else
            {
                strPhotUrl = mDataset.get(position).getmPhoto();
                strDocUrl = mDataset.get(position).getmDocUrl();
                new LoadImage().loadImage(context, R.drawable.no_photo_icon, strPhotUrl ,  holder.imgProfilePic, null);
            }

            holder.cardView.setTag(position);
            holder.cardView.setOnClickListener(clickListener);
            holder.imgMarkError.setTag(position);

            holder.imgMarkError.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenu(holder.imgMarkError, mDataset.get(pos).ismError_flag(), mDataset.get(pos).getmVisitor_id(), mDataset.get(pos).getmFirst_Name()+" "+mDataset.get(pos).getmLast_Name());
                }
            });
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int pos= (int) view.getTag();

                if( cc.isConnectingToInternet())
                {
                    Intent I = new Intent(context, Visiters_Details.class);
                    I.putExtra("PHOTO_URL",Constants.BASE_IMG_URL+"/"+mDataset.get(pos).getmPhoto());
                    I.putExtra("NAME",mDataset.get(pos).getmFirst_Name()+" "+mDataset.get(pos).getmLast_Name());
                    I.putExtra("CONTACT_NO",mDataset.get(pos).getmMobile());
                    I.putExtra("DOC_URL",Constants.BASE_IMG_URL+"/"+mDataset.get(pos).getmDocUrl());
                    I.putExtra("POS",""+pos);
                    context.startActivity(I);
                }
                else
                {
                    Intent I = new Intent(context, Visiters_Details.class);
                    I.putExtra("PHOTO_URL",mDataset.get(pos).getmPhoto());
                    I.putExtra("NAME",mDataset.get(pos).getmFirst_Name()+" "+mDataset.get(pos).getmLast_Name());
                    I.putExtra("CONTACT_NO",mDataset.get(pos).getmMobile());
                    I.putExtra("DOC_URL",mDataset.get(pos).getmDocUrl());
                    I.putExtra("POS",""+pos);
                    context.startActivity(I);
                }
        }
    };

    View.OnClickListener errorClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int pos = (int) view.getTag();
            String name = mDataset.get(pos).getmFirst_Name()+" "+mDataset.get(pos).getmLast_Name();
            //final String visitorId = mDataset.get(pos).getmVisitor_id();
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Visitor "+ name+" Mark as error entry")
                    .setContentText("This visitor not visited to my home ")
                    .setCancelText("Cancel")
                    .setConfirmText("Yes, Mark Error")
                    .showCancelButton(true)
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                        }
                    })
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.cancel();
                            if (cc.isConnectingToInternet()) {
                                //new GetData(MainActivity.MainContext, CallFor.ERROR_ENTRY, ""+visitorId).execute();
                            }
                            else{
                                Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                            }

                        }
                    })
                    .show();
        }
    };


    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, Boolean errorFlag, String visitorId, String visitorName) {
        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.card_menu, popup.getMenu());
        if(errorFlag){
            popup.getMenu().findItem(R.id.action_mark_incorrect).setVisible(false);
            popup.getMenu().findItem(R.id.action_mark_correct).setVisible(true);
        }else{
            popup.getMenu().findItem(R.id.action_mark_incorrect).setVisible(true);
            popup.getMenu().findItem(R.id.action_mark_correct).setVisible(false);
        }
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(visitorId, visitorName));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        String visitorId;
        String visitorName;
        String heading;
        String subHeading;
        String buttonText;
        public MyMenuItemClickListener(String visitorId, String visitorName) {
            this.visitorId = visitorId;
            this.visitorName = visitorName;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_mark_correct:
                    heading = "Mark as Correct";
                    subHeading = visitorName+" is my visitor/guest";
                    buttonText = "Yes, Mark";
                    showAlert(visitorId, heading, subHeading, buttonText);
                    return true;
                case R.id.action_mark_incorrect:
                    heading = "Mark as Incorrect";
                    subHeading = visitorName+" is not my visitor/guest";
                    buttonText = "Yes, Mark";
                    showAlert(visitorId, heading, subHeading, buttonText);
                    return true;
                default:
            }
            return false;
        }
    }

    public  void showAlert(final String visitorId, final String heading, String subHeading, String buttonText){
        final String id = visitorId;
        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                .setTitleText(heading)
                .setContentText(subHeading)
                .setCancelText("Cancel")
                .setConfirmText(buttonText)
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                        if (cc.isConnectingToInternet()) {
                            if(heading.contains("Incorrect")){
                                new GetData(MainActivity.MainContext, CallFor.ERROR_ENTRY, "I-"+id).execute();
                            }else{
                                new GetData(MainActivity.MainContext, CallFor.ERROR_ENTRY, "C-"+id).execute();
                            }
                        }
                        else{
                            Toasty.info(context, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                        }

                    }
                })
                .show();
    }


    @Override
    public int getItemCount() {
        if (hasLoadButton) {
            return mDataset.size() + 1;
        } else {
            return mDataset.size();
        }
    }
}