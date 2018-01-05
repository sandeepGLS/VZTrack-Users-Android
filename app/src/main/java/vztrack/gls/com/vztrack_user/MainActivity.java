package vztrack.gls.com.vztrack_user;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.marcoscg.headerdialog.HeaderDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.adapters.Search_Provider_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.adapters.Search_Vehicles_RecyclerViewAdapter;
import vztrack.gls.com.vztrack_user.adapters.VehicleListAdapter;
import vztrack.gls.com.vztrack_user.beans.ComplainResponceBean;
import vztrack.gls.com.vztrack_user.beans.DataObjectComplains;
import vztrack.gls.com.vztrack_user.beans.DataObjectMessage;
import vztrack.gls.com.vztrack_user.beans.DataObjectRating;
import vztrack.gls.com.vztrack_user.beans.DataObjectVisitors;
import vztrack.gls.com.vztrack_user.beans.MessageResponceBean;
import vztrack.gls.com.vztrack_user.beans.OutputBeanSearchProvider;
import vztrack.gls.com.vztrack_user.beans.RatingBean;
import vztrack.gls.com.vztrack_user.beans.RatingResponseBean;
import vztrack.gls.com.vztrack_user.beans.ResponceBean;
import vztrack.gls.com.vztrack_user.beans.UserBean;
import vztrack.gls.com.vztrack_user.beans.VehicleResponce;
import vztrack.gls.com.vztrack_user.fragment.AddvehicleFragment;
import vztrack.gls.com.vztrack_user.fragment.ComplaintFragment;
import vztrack.gls.com.vztrack_user.fragment.LocalStoreFragment;
import vztrack.gls.com.vztrack_user.fragment.MessageFragment;
import vztrack.gls.com.vztrack_user.fragment.NoInternetFragment;
import vztrack.gls.com.vztrack_user.fragment.NoticesFragment;
import vztrack.gls.com.vztrack_user.fragment.RatingFragment;
import vztrack.gls.com.vztrack_user.fragment.SearchProviderFragment;
import vztrack.gls.com.vztrack_user.fragment.SearchVehicleFragment;
import vztrack.gls.com.vztrack_user.fragment.SettingFragment;
import vztrack.gls.com.vztrack_user.fragment.SupportFragment;
import vztrack.gls.com.vztrack_user.fragment.VehiclesFragment;
import vztrack.gls.com.vztrack_user.fragment.VisitorsFragment;
import vztrack.gls.com.vztrack_user.profile.FamilyBean;
import vztrack.gls.com.vztrack_user.profile.VisitorsArray;
import vztrack.gls.com.vztrack_user.responce.LocalStoreResponse;
import vztrack.gls.com.vztrack_user.responce.LoginResponse;
import vztrack.gls.com.vztrack_user.responce.LogoutResponse;
import vztrack.gls.com.vztrack_user.responce.NoticesResponse;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Constants;
import vztrack.gls.com.vztrack_user.utils.DbHelper;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.ServerConnection;
import vztrack.gls.com.vztrack_user.utils.SheredPref;
import vztrack.gls.com.vztrack_user.utils.URL;

//import org.jsoup.helper.StringUtil;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TextView tvFlatno,tvSocietyName,tvWing;
    private String strValidation;
    private NavigationView navigationView, navigationView1;
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction ft;
    String title="";
    public static NoticesResponse noticesResponse;
    public static MessageResponceBean messageResponceBean;
    public static ArrayList<DataObjectMessage> updatedMessageResponceBean = new ArrayList<>();
    public  static VisitorsArray visitorsArray  = null;
    public  static ComplainResponceBean complainResponceBean  = null;
    public  static VehicleResponce vehicleResponce = null;
    public  static ResponceBean responceBean  = null;
    public  static ArrayList<RatingBean> ratingBeanArrayList = new ArrayList<RatingBean>();
    public  static ArrayList<OutputBeanSearchProvider> outputBeanSearchProviders = new ArrayList<OutputBeanSearchProvider>();
    public  static int fragment_flag=0;
    public ImageView imgHiddenImage;
    public static BaseActivity MainContext;
    CheckConnection cc;
    DbHelper dbHelper;
    LinearLayout splashLayout;
    DrawerLayout drawer;
    String strSplashCheck;
    public static String Notiifcation_flag;
    public static int showFlag;
    LinearLayout ShowLayout;
    public  static FrameLayout NoVisiterLayout;
    public  static TextView NoDataText;
    public  static LinearLayout NoDataLayout;
    public static ArrayList<DataObjectVisitors> Updated_result = new ArrayList<>();
    public static ArrayList<DataObjectRating> Updated_result_rating = new ArrayList<>();
    public static ArrayList<DataObjectComplains> complian_result = new ArrayList<>();
    public static int visitor_PageNo = 0;
    public static int message_PageNo = 0;
    boolean doubleBackToExitPressedOnce = false;
    int drFlag = 0;
    public  static int backPressFlag = 1;
    private boolean mIsInForegroundMode;
    public static String RATING_FLAG = "0";
    public static String[] prov_list;
    public static String[] complain_purpose_list;
    int app_version_code = BuildConfig.VERSION_CODE;
    String date;
    int appVersion;
    FancyButton btnSOS;
    boolean type;
    boolean complaint;
    boolean localStores;
    String shareBody;
    public static boolean isNoticeZero;
    public static int pageNo=0;
    //TextView cnt_visitor,cnt_notice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title="Visitors";
        try
        {
            Bundle extras = getIntent().getExtras();
            strSplashCheck= extras.getString("CALL");
            Notiifcation_flag= extras.getString("NOT_FLAG");
        }
        catch (Exception ex)
        {
            Log.e("Exception "," "+ex);
        }
        if(Notiifcation_flag==null)
        {
            Notiifcation_flag="1";
        }

        SheredPref.setExecuteOffline(getApplication(),"Yes");
        SheredPref.setExecute(getApplication(),"");
        visitor_PageNo=0;
        backPressFlag=1;
        NoVisiterLayout = (FrameLayout) findViewById(R.id.main);
        NoDataText = (TextView) findViewById(R.id.NoDataText);
        NoDataLayout= (LinearLayout) findViewById(R.id.NoDataLayout);

        getSupportActionBar().setTitle("Visitors");

        MainContext = MainActivity.this;
        dbHelper = new DbHelper(this);

        imgHiddenImage = (ImageView) findViewById(R.id.imgHiddenImage);

        splashLayout = (LinearLayout) findViewById(R.id.splash);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView1 = (NavigationView) findViewById(R.id.nav_view1);
        navigationView.setNavigationItemSelectedListener(this);
        View header=navigationView1.getHeaderView(0);

        // ----------         Set programatically Drawer values
        navigationView.inflateMenu(R.menu.activity_main_drawer);

        tvFlatno = (TextView)header.findViewById(R.id.tvFLatNo);
        tvSocietyName = (TextView)header.findViewById(R.id.tvSocietyName);
        tvWing = (TextView)header.findViewById(R.id.tvWing);
        btnSOS = (FancyButton) findViewById(R.id.btnSOS);

        tvSocietyName.setSelected(true);
        tvWing.setSelected(true);
        tvFlatno.setSelected(true);

        type = SheredPref.getType(this);
        // If type true then this is Company
        if(!type){
            tvFlatno.setText("Flat No. "+SheredPref.getFlat_No(getApplicationContext()));
            tvWing.setText("Wing : "+SheredPref.getWingName(getApplicationContext()));
            shareBody = "\t\t\tVZ Track - Smart Digital Solution to manage visitors for societies and commercial complex.\n" +
                    "--------------------------------------\n"+
                    "What do we provide to get started?" +
                    "\n1. 7 inches android tablet." +
                    "\n2. 1GB internet data per month " +
                    "\n3. Training to security guards" +
                    "\n4. Multiple user login per flat" +
                    "\n5. Ongoing support & maintenance " +
                    "\n6. Instant notification on owner's mobile with visitors details"+
                    "\n7. Analytics and Reports of visitors visited the premise"+
                    "\n\nContact us : \n" +
                    "Call us: +91 90750 16367\n" +
                    "Email us: sales@vztrack.in\n"+
                    "Website : https://www.vztrack.net\n"
                    +"follow us on Twitter : @VzTrack";
        }else{
            tvFlatno.setText("Employee No. "+SheredPref.getFlat_No(getApplicationContext()));
            tvWing.setText("Department : "+SheredPref.getWingName(getApplicationContext()));
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_more_rattings).setVisible(false);
            shareBody = "\t\t\tVZ Track - Smart Digital Solution to manage visitors for commercial complex.\n" +
                    "--------------------------------------\n"+
                    "What do we provide to get started in commercial complex?" +
                    "\n1. 7 inches android tablet." +
                    "\n2. Training to security guards" +
                    "\n3. User login for employee" +
                    "\n4. Ongoing support & maintenance " +
                    "\n5. Instant notification on employee's mobile with visitors details"+
                    "\n6. Analytics and Reports of visitors visited the premise"+
                    "\n\nContact us : \n" +
                    "Call us: +91 90750 16367\n" +
                    "Email us: sales@vztrack.in\n"+
                    "Website : http://www.vztrack.in\n"
                    +"follow us on Twitter : @VzTrack";
        }

        tvSocietyName.setText(SheredPref.getSociety_Name(getApplicationContext()));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        date = df.format(c.getTime());
        if(date.compareTo(SheredPref.getDateForApi(this))!=0){
            String device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
            SheredPref.setDateForApi(this,date);
            new GetData(MainActivity.MainContext, CallFor.PROVIDER_LIST, "?data="+device_id+"&versionCode="+app_version_code).execute();
        }

        SheredPref.setRun(MainContext,"DONT RUN");
        if(strSplashCheck==null)
        {
            strSplashCheck="";
        }
        if(strSplashCheck.equals("FROM_LOGIN") || Notiifcation_flag.equals("3"))
        {
            SheredPref.setRun(MainContext,"RUN");
            splashLayout.setVisibility(View.INVISIBLE);
            drawer.setVisibility(View.VISIBLE);
        }
        if(strSplashCheck.equals(""))
        {
            if(Notiifcation_flag.equals("1"))
            {
                drFlag = 0;
                if(RATING_FLAG.equals("0")){
                    splashLayout.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            splashLayout.setVisibility(View.INVISIBLE);
                            drawer.setVisibility(View.VISIBLE);
                            SheredPref.setRun(MainContext,"RUN");
                        }
                    }, 2500);
                }
                else{
                    SheredPref.setRun(MainContext,"RUN");
                    splashLayout.setVisibility(View.INVISIBLE);
                    drawer.setVisibility(View.VISIBLE);
                }
            }
            if(Notiifcation_flag.equals("2"))
            {
                drFlag = 1;
                if(RATING_FLAG.equals("0")){
                    splashLayout.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            splashLayout.setVisibility(View.INVISIBLE);
                            drawer.setVisibility(View.VISIBLE);
                            SheredPref.setRun(MainContext,"RUN");
                        }
                    }, 2500);
                }
                else{
                    SheredPref.setRun(MainContext,"RUN");
                    splashLayout.setVisibility(View.INVISIBLE);
                    drawer.setVisibility(View.VISIBLE);
                }
            }

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                navigationView.getMenu().clear();
                navigationView.inflateMenu(R.menu.activity_main_drawer);
                setMenuCounter(R.id.nav_notice,SheredPref.getNoticeConut(MainContext));
                Menu nav_Menu = navigationView.getMenu();
                if(type){
                    nav_Menu.findItem(R.id.nav_more_rattings).setVisible(false);
                }
                complaint = SheredPref.getComplain(MainContext);
                localStores = SheredPref.getLocalStores(MainContext);
                if(!complaint){
                    nav_Menu.findItem(R.id.nav_complaint).setVisible(false);
                }
                if(!localStores || SheredPref.isCompany(MainContext)){
                    nav_Menu.findItem(R.id.nav_local_stores).setVisible(false);
                }
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                //super.onDrawerOpened(drawerView);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        btnSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawers();
                if (cc.isConnectingToInternet()) {

                    final AlertDialog.Builder builder =
                            new AlertDialog.Builder(MainContext, R.style.AppCompatAlertDialogStyle);
                    builder.setTitle("Need Help");
                    builder.setMessage("Raise an alarm with security?");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //String data = "?socity_id=" + SheredPref.getSocietyId(MainContext) + "&familyId=" + SheredPref.getFamilyId(MainContext);
                            //new GetData(MainActivity.this, CallFor.SOS, data).execute();
                            FamilyBean familyBean = new FamilyBean();
                            familyBean.setSocietyId(Integer.parseInt(SheredPref.getSocietyId(MainContext)));
                            familyBean.setFamilyId(Integer.parseInt(SheredPref.getFamilyId(MainContext)));
                            familyBean.setWing(SheredPref.getWingName(MainContext));
                            familyBean.setFlatOwnerName(SheredPref.getOwnerName(MainContext));
                            familyBean.setFlatNo(SheredPref.getFlat_No(MainContext));
                            new PostData(new Gson().toJson(familyBean),MainActivity.this , CallFor.SOS).execute();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.show();
                } else {
                    Toasty.info(MainContext, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mIsInForegroundMode = true;
        cc = new CheckConnection(getApplicationContext());
        strValidation = SheredPref.getLoginInfo(getApplicationContext());
        if(strValidation.equals("LoggedIn"))
        {
            if(cc.isConnectingToInternet())
            {
                if (backPressFlag==1)
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(getApplicationContext()));
                    userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                    new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGIN).execute();
                }
                if(Notiifcation_flag.equals("3")){
                    drFlag = 3;
                    Updated_result_rating.clear();
                    try{
                        RatingResponseBean ratingResponseBean = new RatingResponseBean();
                        ratingResponseBean.setSocietyId(Integer.parseInt(SheredPref.getSocietyId(MainContext)));
                        ratingResponseBean.setFamilyId(Integer.parseInt(SheredPref.getFamilyId(MainContext)));
                        new PostData(new Gson().toJson(ratingResponseBean), MainActivity.this, CallFor.PENDING_RATING).execute();

                    }catch (Exception ex){
                        Log.e("Exception in onResume"," "+ex);
                    }
                }
            }
            else
            {
                if(title.equals("Visitors")){
                    if(SheredPref.getExecuteOffline(getApplicationContext()).equals(""))
                    {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                        SheredPref.setExecuteOffline(getApplication(),"Not");
                    }
                    else if(SheredPref.getExecuteOffline(getApplicationContext()).equals("Yes"))
                    {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                        SheredPref.setExecuteOffline(getApplication(),"Not");
                    }
                    else
                    {
                        SheredPref.setExecuteOffline(MainActivity.this,"NotExecute");
                    }
                }
                if(title.equals("Notices")){
                    ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.main, new NoticesFragment(), "Data").commit();
                }

                if(title.equals("Feedback")){
                    ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.main, new RatingFragment(), "Data").commit();
                    NoVisiterLayout.setVisibility(View.GONE);
                    NoDataLayout.setVisibility(View.VISIBLE);
                    NoDataText.setText("Please check internet connection");
                }
            }
        }
        else
        {
            Intent I = new Intent(MainActivity.this,LoginScreenActivity.class);
            startActivity(I);
            I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce){
                super.onBackPressed();
                //finish();
                onStop();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toasty.info(this,"Press again to exit",Toast.LENGTH_SHORT, true).show();
            //Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search)
        {
            return true;
        }

       if (id == R.id.action_call)
        {
            try{
                if(SheredPref.getPhoneNo(this).equals(""))
                {
                    Toasty.info(this,"No phone number added, Ask your admin to add security gate phone number").show();
                }
                else{
                    String[] phone_numbers_info = SheredPref.getPhoneNo(this).trim().split("-");
                    String[] gates = phone_numbers_info[0].trim().split(",");
                    String[] phoneNo = phone_numbers_info[1].trim().split(",");
                    ArrayList<String> phones = new ArrayList<>();
                    for(int i=0;i<phoneNo.length;i++){
                        if(!phoneNo[i].equals("")){
                            phones.add(gates[i]+" - "+phoneNo[i]);
                        }
                    }
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(this);
                    builderSingle.setIcon(R.drawable.call);
                    builderSingle.setTitle("Call Security Guard");
                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_phone);
                    for (int i =0;i<phones.size();i++)
                    {
                        arrayAdapter.add(phones.get(i));
                    }

                    builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String strPhone = arrayAdapter.getItem(which);
                            makeCall(strPhone.split("-")[1]);
                        }
                    });
                    builderSingle.show();
                }

                return true;
            }catch (Exception ex){
                Log.e("Exception In Calling"," "+ex);
            }

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsInForegroundMode = false;
    }

    @Override
    protected void onStop() {
        Log.e("ON STOP","");
        super.onStop();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        ft = fragmentManager.beginTransaction();
        int id = item.getItemId();
        if (id == R.id.nav_visiters) {
            title="Visitors";
            ShowUI();
            outputBeanSearchProviders.clear();
            if(cc.isConnectingToInternet()) {
                if(drFlag!=0)
                {
                    visitor_PageNo=0;
                    Updated_result.clear();
                    new GetData(MainActivity.this, CallFor.VISITORS, ""+visitor_PageNo).execute();
                }
            } else {
                ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
            }
            drFlag = 0;
        } else if (id == R.id.nav_notice) {
           title="Notices";
            ShowUI();
            outputBeanSearchProviders.clear();
            if(cc.isConnectingToInternet()) {
                if(drFlag!=1) {
                    new GetData(MainActivity.this, CallFor.NOTICES, "").execute();
                }
            } else {
                ft.replace(R.id.main, new NoticesFragment(), "Data").commit();
            }
            drFlag = 1;
        }
        else if (id == R.id.nav_message) {
            title="Message";
            ShowUI();
            if(cc.isConnectingToInternet()) {
                if(drFlag!=2) {
                    updatedMessageResponceBean.clear();
                    message_PageNo = 0;
                    new GetData(MainActivity.this, CallFor.MESSAGE, ""+0).execute();
                }
            } else {
                ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
            }
            drFlag = 2;
        }
        else if (id == R.id.nav_feedback) {
            title="Service Feedback";
            outputBeanSearchProviders.clear();
            if(cc.isConnectingToInternet()) {
                if(drFlag!=3){
                    Updated_result_rating.clear();
                    RatingResponseBean ratingResponseBean = new RatingResponseBean();
                    ratingResponseBean.setSocietyId(Integer.parseInt(SheredPref.getSocietyId(MainContext)));
                    ratingResponseBean.setFamilyId(Integer.parseInt(SheredPref.getFamilyId(MainContext)));
                    new PostData(new Gson().toJson(ratingResponseBean), MainActivity.this, CallFor.PENDING_RATING).execute();
                    ShowUI();
                }
            } else {
                ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
            }
            drFlag = 3;
        }
        else if (id == R.id.nav_search_provider) {
            title="Search Provider";
            if(drFlag!=4){
                if(cc.isConnectingToInternet()) {
                    ShowUI();
                    ft.replace(R.id.main, new SearchProviderFragment(), "Data").commit();
                } else {
                    ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
                }
                drFlag = 4;
            }
        }
        else if (id == R.id.nav_logout) {
            outputBeanSearchProviders.clear();
            if(cc.isConnectingToInternet()) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainContext, R.style.AppCompatAlertDialogStyle);
                builder.setMessage("Are you sure? If you logout you will not receive visitor notification");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {

                        String device_id = Settings.Secure.getString(MainContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                        UserBean userBean = new UserBean();
                        userBean.setUser_dev_id(device_id);
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGOUT).execute();
                        LoginScreenActivity.splashFlag=false;
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toasty.info(this, "Unable to logout, Please check internet connection", Toast.LENGTH_SHORT, true).show();
            }
        }
        else if(id == R.id.nav_share){
            outputBeanSearchProviders.clear();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "VZ Track");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
            CleverTap.cleverTap_Record_Event(this,Events.event_refer);
        }
        else if (id == R.id.nav_vehicles) {
            title="Vehicle Search";
            CleverTap.cleverTap_Record_Event(this, Events.event_search_vehicle_screen);
            if(drFlag!=5){
                if(cc.isConnectingToInternet())
                {
                    NoVisiterLayout.setVisibility(View.VISIBLE);
                    NoDataLayout.setVisibility(View.GONE);
                    ft.replace(R.id.main, new VehiclesFragment(), "Data").commit();
                }
                else
                {
                    ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
                }
                drFlag=5;
            }

        }
        else if (id == R.id.nav_setting) {
            outputBeanSearchProviders.clear();
            title="Setting";
            if(drFlag!=6){
                if(cc.isConnectingToInternet())
                {
                    NoVisiterLayout.setVisibility(View.VISIBLE);
                    NoDataLayout.setVisibility(View.GONE);
                    ft.replace(R.id.main, new SettingFragment(), "Data").commit();
                }
                else
                {
                    ft.replace(R.id.main, new SettingFragment(), "Data").commit();
                }
                drFlag=6;
            }

        }
        else if (id == R.id.nav_support) {
            outputBeanSearchProviders.clear();
            title="Support";
            if(drFlag!=7){
                if(cc.isConnectingToInternet())
                {
                    NoVisiterLayout.setVisibility(View.VISIBLE);
                    NoDataLayout.setVisibility(View.GONE);
                    ft.replace(R.id.main, new SupportFragment(), "Data").commit();
                }
                else
                {
                    ft.replace(R.id.main, new SupportFragment(), "Data").commit();
                }
                drFlag = 7;
            }
        }
        else if (id == R.id.nav_complaint) {
            outputBeanSearchProviders.clear();
            title="Complaint Register";
            if(drFlag!=8){
                if(cc.isConnectingToInternet())
                {
                    complian_result.clear();
                    pageNo=0;
                    new GetData(MainActivity.this, CallFor.GET_COMPLAIN, pageNo+"").execute();
                }
                else
                {
                    ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
                }
                drFlag = 8;
            }
        }
        else if (id == R.id.nav_more_rattings) {
            drFlag = 9;
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.main_drawer_submenu);
        }
        else if (id == R.id.nav_back) {
            drFlag = 10;
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_main_drawer);

            Menu nav_Menu = navigationView.getMenu();
            complaint = SheredPref.getComplain(MainContext);
            localStores = SheredPref.getLocalStores(MainContext);
            if(!complaint){
                nav_Menu.findItem(R.id.nav_complaint).setVisible(false);
            }
            if(!localStores || SheredPref.isCompany(MainContext)){
                nav_Menu.findItem(R.id.nav_local_stores).setVisible(false);
            }
        } else if(id == R.id.nav_local_stores){
            title = "Local Stores";
            if(drFlag!=11){
                if(cc.isConnectingToInternet()) {
                    ft.replace(R.id.main, new LocalStoreFragment(), "DATA").commit();
                    new GetData(this, CallFor.LOCAL_STORES, "").execute();
                    CleverTap.cleverTap_Record_Event(this, Events.local_store_open);
                } else {
                    ft.replace(R.id.main, new NoInternetFragment(), "DATA").commit();
                }
            }
            drFlag = 11;
        }
        getSupportActionBar().setTitle(title);

        if(drFlag ==9 || drFlag ==10){

        }else{
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void ShowUI(){
        NoVisiterLayout.setVisibility(View.VISIBLE);
        NoDataLayout.setVisibility(View.GONE);
    }

    public void makeCall(String phone){
        Uri number = Uri.parse("tel:" + phone);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        if(cc.isConnectingToInternet())
        {
            LoginResponse loginResponse = null;
            if (response == null) {
                return;
            }

            if (callFor.equals(CallFor.LOGIN)) {
                loginResponse=new Gson().fromJson(response,LoginResponse.class);
                try {
                    if (loginResponse.getCode().equals("SUCCESS"))
                    {
                        String phoneNo = loginResponse.getFamily().getSoc_phoneNo();
                        boolean sosAccess = loginResponse.getConfigBean().isSos();
                        boolean complaintAccess = loginResponse.getConfigBean().isComplain();
                        boolean localStoreAccess = loginResponse.getConfigBean().isLocalStore();
                        if(!SheredPref.getPhoneNo(this).equals(phoneNo)){
                            SheredPref.setPhoneNo(this,phoneNo);
                        }
                        if(SheredPref.getSOSAccess(this)!=sosAccess){
                            SheredPref.setSOSAccess(this,sosAccess);
                        }
                        if(SheredPref.getComplain(this)!=complaintAccess){
                            SheredPref.setComplaint(this,complaintAccess);
                        }
                        if(SheredPref.getLocalStores(this)!= localStoreAccess){
                            SheredPref.setLocalStores(this,localStoreAccess);
                        }
                        if(SheredPref.getSOSAccess(this)) {
                            btnSOS.setVisibility(View.VISIBLE);
                        }else{
                            btnSOS.setVisibility(View.GONE);
                        }
                        SheredPref.setCompany(this, loginResponse.getConfigBean().isCompany());
                        type = SheredPref.getType(this);
                        complaint = SheredPref.getComplain(this);
                        localStores = SheredPref.getLocalStores(this);
                        Menu nav_Menu = navigationView.getMenu();
                        if(!complaint){
                            nav_Menu.findItem(R.id.nav_complaint).setVisible(false);
                        }
                        if(!localStores || SheredPref.isCompany(MainContext)){
                            nav_Menu.findItem(R.id.nav_local_stores).setVisible(false);
                        }
                        SheredPref.setAdminAccess(this,loginResponse.getFamily().isExtraAccess());
                        appVersion = loginResponse.getAppBean().getAppVersion();
                        if(app_version_code!=appVersion){
                            int forcedUpdate =  Integer.parseInt(loginResponse.getAppBean().getForceUpdate());
                            String versionInfo =loginResponse.getAppBean().getVersionInfo();

                            if(forcedUpdate==1)
                            {
                                showUpdateDialog(versionInfo,true);
                            }else{
                                if(SheredPref.getLatestAppVersion(MainContext)!=appVersion){
                                    showUpdateDialog(versionInfo,false);
                                }
                            }
                        }
                        if(SheredPref.getSocietyId(this).equals("")){
                            int strSocietyId = loginResponse.getFamily().getSocietyId();
                            int strFamilyId = loginResponse.getFamily().getFamilyId();
                            SheredPref.setSocietyId(this,""+strSocietyId);
                            SheredPref.setFamilyId(this,""+strFamilyId);
                        }

                        if(SheredPref.getExecute(getApplicationContext()).equals(""))
                        {
                            if(Notiifcation_flag.equals("1"))
                            {
                                drFlag = 0;
                                Updated_result.clear();
                                new GetData(MainActivity.this, CallFor.VISITORS, ""+visitor_PageNo).execute();
                                SheredPref.setExecute(getApplication(),"Not");
                                getSupportActionBar().setTitle("Visitors");
                            }
                            if(Notiifcation_flag.equals("2"))
                            {
                                drFlag=1;
                                new GetData(MainActivity.this, CallFor.NOTICES, "").execute();
                                SheredPref.setExecute(getApplication(),"Not");
                                getSupportActionBar().setTitle("Notices");
                            }
                            if(Notiifcation_flag.equals("4")){
                                drFlag=2;
                                updatedMessageResponceBean.clear();
                                message_PageNo = 0;
                                Notiifcation_flag = "0";
                                getSupportActionBar().setTitle("Message");
                                SheredPref.setExecute(getApplication(),"Not");
                                new GetData(MainActivity.this, CallFor.MESSAGE, ""+0).execute();
                            }
                        }
                        else
                        {
                            SheredPref.setExecute(MainActivity.this,"NotExecute");
                        }

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        date = df.format(c.getTime());
                        if(date.compareTo(SheredPref.getDateForCompApi(this))!=0){
                            SheredPref.setDateForCompApi(this,date);
                            new GetData(MainActivity.this, CallFor.GET_COMPLAIN_CATEGORY,"").execute();
                        }
                    }
                    else if(loginResponse.getCode().equals("ERROR"))
                    {
                        Toasty.error(this, "Something went wrong, Please login again", Toast.LENGTH_SHORT, true).show();
                        String device_id = Settings.Secure.getString(MainContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                        UserBean userBean = new UserBean();
                        userBean.setUser_dev_id(device_id);
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGOUT).execute();

                    }
                }catch (Exception ex)
                {
                    Log.e("Exception login resp"," "+ex);
                }
            }

            if (callFor.equals(CallFor.NOTICES)) {
                getSupportActionBar().setTitle("Notices");
                noticesResponse = new Gson().fromJson(response,NoticesResponse.class);
                if(fragment_flag==1)
                {
                    NoticesFragment.mSwipeRefreshLayout.setRefreshing(false);
                    fragment_flag=0;
                }
                try {
                    if (noticesResponse.getCode().equals("SUCCESS")){
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new NoticesFragment(), "Data").commit();

                        if(noticesResponse.getMessage().equals("No Notices"))
                        {
                            isNoticeZero =true;
                        }
                        else
                        {
                            isNoticeZero =false;
                        }
                    }
                    else if (noticesResponse.getCode().equals("NEED_LOGIN")) {
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(getApplicationContext()));
                        userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGIN).execute();
                    }
                    else {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new NoticesFragment(), "Data").commit();
                    }
                } catch (Exception e) {
                    Log.e("Exception Notices"," "+e);
                }
            }  // End If

            if (callFor.equals(CallFor.VISITORS)) {
                getSupportActionBar().setTitle("Visitors");
                visitorsArray = new Gson().fromJson(response,VisitorsArray.class);
                if(fragment_flag==1)
                {
                    VisitorsFragment.mSwipeRefreshLayout.setRefreshing(false);
                    fragment_flag=0;
                }
                try {
                    if (visitorsArray.getVisitors().size() == 0 && visitor_PageNo==0) {
                        NoVisiterLayout.setVisibility(View.GONE);
                        NoDataLayout.setVisibility(View.VISIBLE);
                        NoDataText.setText("No Visitor To Display");
                    }
                    else
                    {
                        NoVisiterLayout.setVisibility(View.VISIBLE);
                        NoDataLayout.setVisibility(View.GONE);
                    }

                    if (visitorsArray.getCode().equals("SUCCESS")) {
                        showFlag=1;
                        ft = fragmentManager.beginTransaction();
                        //visitorsArray.getVisitors().clear();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commitAllowingStateLoss();
                        //ft.replace(R.id.main, new RatingFragment(), "Data").commit();
                    }
                    else if (visitorsArray.getCode().equals("NEED_LOGIN")) {
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(getApplicationContext()));
                        userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGIN).execute();
                    }
                    else
                    {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                    }
                } catch (Exception e) {
                    Log.e("Exception Visitor"," "+e);
                }
            }

            if (callFor.equals(CallFor.MESSAGE)) {
                getSupportActionBar().setTitle("Message");
                messageResponceBean = new Gson().fromJson(response,MessageResponceBean.class);
                if(fragment_flag==1)
                {
                    MessageFragment.mSwipeRefreshLayout.setRefreshing(false);
                    fragment_flag=0;
                }
                try {
                    if (messageResponceBean.getCode().equals("SUCCESS")){
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new MessageFragment(), "Data").commit();
                    }
                    else if (messageResponceBean.getCode().equals("NEED_LOGIN")) {
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(getApplicationContext()));
                        userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGIN).execute();
                    }
                    else {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new MessageFragment(), "Data").commit();
                    }
                } catch (Exception e) {
                    Log.e("Exception "," "+e);
                }
            }

            if (callFor.equals(CallFor.PROVIDER_LIST)) {
                try{
                prov_list = new Gson().fromJson(response,String[].class);
                // Store the Provider List in SharedPreferences
                SharedPreferences prefs = this.getSharedPreferences("LIST",0);
                SharedPreferences.Editor edit = prefs.edit();
                edit.putInt("array_size", prov_list.length);
                for(int i=0;i<prov_list.length; i++)
                    edit.putString("array_" + i, prov_list[i]);
                edit.commit();
                } catch (Exception e) {
                    Log.e("Exception ProviderList"," "+e);
                }
            }

            if (callFor.equals(CallFor.PROVIDERS_DATA)) {
                getSupportActionBar().setTitle("Search Provider");
                try
                {
                    outputBeanSearchProviders = new Gson().fromJson(response,new TypeToken<ArrayList<OutputBeanSearchProvider>>(){}.getType());
                    if(outputBeanSearchProviders.size()==0){
                        SearchProviderFragment.tvNoProvider.setVisibility(View.VISIBLE);
                        SearchProviderFragment.tvNoProvider.setText("No Provider Found !");
                        SearchProviderFragment.mRecyclerView.setVisibility(View.GONE);
                    }
                    else{
                        SearchProviderFragment.tvNoProvider.setVisibility(View.GONE);
                        SearchProviderFragment.mRecyclerView.setVisibility(View.VISIBLE);
                    }
                    SearchProviderFragment.mAdapter = new Search_Provider_RecyclerViewAdapter(this,MainActivity.outputBeanSearchProviders);
                    SearchProviderFragment.mRecyclerView.setAdapter( SearchProviderFragment.mAdapter);
                }catch (Exception ex)
                {
                    Log.e("EXCEPTION ProviderData"," "+ex);
                }
            }


            if (callFor.equals(CallFor.PENDING_RATING)) {
                getSupportActionBar().setTitle("Service Feedback");
                try{
                    ratingBeanArrayList = new Gson().fromJson(response,new TypeToken<ArrayList<RatingBean>>(){}.getType());
                    if(fragment_flag==1)
                    {
                        RatingFragment.mSwipeRefreshLayout.setRefreshing(false);
                        fragment_flag=0;
                    }
                    if (ratingBeanArrayList.size()==0) {
                        NoVisiterLayout.setVisibility(View.GONE);
                        NoDataLayout.setVisibility(View.VISIBLE);
                        NoDataText.setText("No provider available for feedback");
                    }
                    else
                    {
                        NoVisiterLayout.setVisibility(View.VISIBLE);
                        NoDataLayout.setVisibility(View.GONE);
                    }
                    ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.main, new RatingFragment(), "Data").commit();
                }catch (Exception ex)
                {
                    Log.e("EXCEPTION P Rating"," "+ex);
                }
            }

            if (callFor.equals(CallFor.LOGOUT)) {
                LogoutResponse logoutResponse = new Gson().fromJson(response,LogoutResponse.class);
                try {
                        SheredPref.setLoginInfo(this,"LoggedOut");
                        SheredPref.setPassword(this,"");
                        SheredPref.setDateForApi(this,"");
                        SheredPref.setDateForCompApi(this,"");
                        SheredPref.setNoticeCount(this,0);
                        //SheredPref.set(this,0);
                        SharedPreferences prefs = this.getSharedPreferences("COMP_LIST",0);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.clear();
                        edit.commit();
                        Intent I = new Intent(MainActivity.this,LoginScreenActivity.class);
                        startActivity(I);
                        this.finish();
                        I.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        SheredPref.setNotification(this,"DISABLE");
                }
                catch (Exception ex)
                {
                    Log.e("Exception Logout"," "+ex);
                }
            }

            if (callFor.equals(CallFor.SOS)) {
                loginResponse=new Gson().fromJson(response,LoginResponse.class);
                try {
                    if (loginResponse.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
                        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                        pDialog.setTitleText(loginResponse.getMessage().split(",")[0]);
                        pDialog.setContentText(loginResponse.getMessage().split(",")[1]);
                        pDialog.setCancelable(true);
                        pDialog.show();
                    }
                    else if(loginResponse.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                        //String data = "?socity_id="+SheredPref.getSocietyId(MainContext)+"&familyId="+SheredPref.getFamilyId(MainContext);
                        //new GetData(MainActivity.MainContext, CallFor.SOS, data).execute();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception In SOS"," "+ex);
                }
            }

            if (callFor.equals(CallFor.FEEDBACK)) {
                loginResponse=new Gson().fromJson(response,LoginResponse.class);
                try {
                    if (loginResponse.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        new SweetAlertDialog(MainContext, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(loginResponse.getMessage().split(",")[0])
                                .setContentText(loginResponse.getMessage().split(",")[1])
                                .show();
                    }
                    else if(loginResponse.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();

//                        String society_id = SheredPref.getSocietyId(MainActivity.this);
//                        String flat_number = SheredPref.getFlat_No(MainActivity.this);
//                        String family_id = SheredPref.getFamilyId(MainActivity.this);
//                        FamilyBean familyBean = new FamilyBean();
//                        familyBean.setFlatOwnerName(SupportFragment.name);
//                        familyBean.setFlatOwnerMobile(SupportFragment.message);
//                        familyBean.setSocietyId(Integer.parseInt(society_id));
//                        familyBean.setFlatNo(flat_number);
//                        familyBean.setFamilyId(Integer.parseInt(family_id));
//                        familyBean.setFlatFamilySize(SupportFragment.level);
//                        new PostData(new Gson().toJson(familyBean), MainActivity.MainContext, CallFor.FEEDBACK).execute();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception Feedback"," "+ex);
                }
            }
            if (callFor.equals(CallFor.GET_COMPLAIN)) {
                complainResponceBean=new Gson().fromJson(response,ComplainResponceBean.class);
                try {
                    if (complainResponceBean.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        NoVisiterLayout.setVisibility(View.VISIBLE);
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new ComplaintFragment(), "Data").commitAllowingStateLoss();
                    }
                    else if(complainResponceBean.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception Complain"," "+ex);
                }
            }

            if (callFor.equals(CallFor.CLOSE_COMPLAIN)) {
                complainResponceBean=new Gson().fromJson(response,ComplainResponceBean.class);
                try {
                    if (complainResponceBean.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        complian_result.clear();
                        pageNo = 0;
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new ComplaintFragment(), "Data").commitAllowingStateLoss();
                        Toasty.success(this,complainResponceBean.getMessage(), Toast.LENGTH_SHORT, true).show();
                        //new GetData(MainActivity.this, CallFor.GET_COMPLAIN,pageNo+"").execute();
                    }
                    else if(complainResponceBean.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception Complain"," "+ex);
                }
            }

            if (callFor.equals(CallFor.GET_COMPLAIN_CATEGORY)) {
                complainResponceBean=new Gson().fromJson(response,ComplainResponceBean.class);
                try {
                    if (complainResponceBean.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        ComplainResponceBean comp  = new Gson().fromJson(response,ComplainResponceBean.class);
                        comp.getSocietyComplainCategories();
                        // Store the Provider List in SharedPreferences
                        SharedPreferences prefs = this.getSharedPreferences("COMP_LIST",0);
                        SharedPreferences.Editor edit = prefs.edit();
                        edit.putInt("comp_array_size",  comp.getSocietyComplainCategories().size());
                        for(int i=0;i< comp.getSocietyComplainCategories().size(); i++)
                            edit.putString("comp_array_" + i, comp.getSocietyComplainCategories().get(i));
                        edit.commit();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception Complain Cat"," "+ex);
                }
            }


            if (callFor.equals(CallFor.ADD_COMPLAIN)) {
                complainResponceBean=new Gson().fromJson(response,ComplainResponceBean.class);
                try {
                    if (complainResponceBean.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        complian_result.clear();
                        pageNo = 0;
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new ComplaintFragment(), "Data").commitAllowingStateLoss();
                        Toasty.success(this,complainResponceBean.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                    else if(complainResponceBean.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                        //Toasty.error(this,"Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Exception Complain"," "+ex);
                }
            }
            if (callFor.equals(CallFor.ERROR_ENTRY)) {
                MainActivity.Updated_result.clear();
                visitor_PageNo = 0;
                visitorsArray = new Gson().fromJson(response,VisitorsArray.class);
                try {
                    if (visitorsArray.getCode().equals("SUCCESS")) {
                        Toasty.success(this, "Successfully marked visitor", Toast.LENGTH_SHORT, true).show();
                        showFlag=1;
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                    }
                    else if (visitorsArray.getCode().equals("NEED_LOGIN")) {
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(getApplicationContext()));
                        userBean.setUser_password(SheredPref.getPassword(getApplicationContext()));
                        new PostData(new Gson().toJson(userBean), MainActivity.this, CallFor.LOGIN).execute();
                    }
                    else if (visitorsArray.getCode().equals("Already_Marked")) {
                        Toasty.success(this, visitorsArray.getMessage(), Toast.LENGTH_SHORT, true).show();
                        showFlag=1;
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                    }
                    else
                    {
                        ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.main, new VisitorsFragment(), "Data").commit();
                    }

                }catch (Exception ex){
                    Log.e("EXCEPTION Error Entry",""+ex);
                }
            }

            if (callFor.equals(CallFor.VEHICLENOPATTERN)) {
                vehicleResponce=new Gson().fromJson(response,VehicleResponce.class);
                try {
                    if (vehicleResponce.getCode().equalsIgnoreCase("SUCCESS"))
                    {
                        String all_vehicle_no_for_add = null;
                        String all_vehicle_no_for_search = null;
                        String all_vehicle_pattern = null;
                        String all_flags  = null;
                        for(int i=0;i<MainActivity.vehicleResponce.getVehiclePattern().size();i++){
                            if(i==0){
                                all_vehicle_pattern = vehicleResponce.getVehiclePattern().get(i).split("=")[1].trim();
                                all_flags = vehicleResponce.getVehiclePattern().get(i).split("=")[2].trim();
                                String flag =  vehicleResponce.getVehiclePattern().get(i).split("=")[2].trim();
                                if(flag.trim().equals("0")){
                                    all_vehicle_no_for_add = vehicleResponce.getVehiclePattern().get(i).split("=")[0].trim();
                                }
                                all_vehicle_no_for_search = vehicleResponce.getVehiclePattern().get(i).split("=")[0].trim();

                            }else{
                                all_vehicle_pattern = all_vehicle_pattern +"@ "+ vehicleResponce.getVehiclePattern().get(i).split("=")[1].trim();
                                all_flags = all_flags +", "+ vehicleResponce.getVehiclePattern().get(i).split("=")[2].trim();
                                String flag =  vehicleResponce.getVehiclePattern().get(i).split("=")[2].trim();
                                if(flag.trim().equals("0")){
                                    if(all_vehicle_no_for_add == null){
                                        all_vehicle_no_for_add = vehicleResponce.getVehiclePattern().get(i).split("=")[0].trim();
                                    }else{
                                        all_vehicle_no_for_add = all_vehicle_no_for_add +", "+ vehicleResponce.getVehiclePattern().get(i).split("=")[0].trim();
                                    }
                                }
                                all_vehicle_no_for_search = all_vehicle_no_for_search +", "+ vehicleResponce.getVehiclePattern().get(i).split("=")[0].trim();
                            }
                        }
                        SheredPref.setVehicleNo_for_add(this, all_vehicle_no_for_add);
                        SheredPref.setVehicleNo_for_search(this, all_vehicle_no_for_search);
                        SheredPref.setVehiclePatternNo(this, all_vehicle_pattern);
                        SheredPref.setVehicleFlag(this, all_flags);

                    }
                    else if(complainResponceBean.getCode().equalsIgnoreCase("NEED_LOGIN"))
                    {
                        UserBean userBean = new UserBean();
                        userBean.setUser_name(SheredPref.getUsername(this));
                        userBean.setUser_password(SheredPref.getPassword(this));
                        new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                        new GetData(MainActivity.MainContext, CallFor.VEHICLENOPATTERN,"").execute();
                    }
                    else
                    {
                        drawer.closeDrawer(GravityCompat.START);
                        Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                    }
                }catch (Exception ex)
                {
                    Log.e("Ex Vehicle Pattern"," "+ex);
                }
            }
        }

        if (callFor.equals(CallFor.SEARCH_VEHICLE)) {
            vehicleResponce=new Gson().fromJson(response,VehicleResponce.class);
            try {
                if (vehicleResponce.getCode().equalsIgnoreCase("SUCCESS"))
                {
                    int size = vehicleResponce.getSearchVehicleBeans().size();
                    AddvehicleFragment.vehicleNumber.setText("");
                    if(size==0){
                         SearchVehicleFragment.noData.setVisibility(View.VISIBLE);
                         SearchVehicleFragment.noData.setText("Opps no result found for vehicle Number \n '"+SearchVehicleFragment.res.getText().toString().trim().split("'")[1]+"'");
                         SearchVehicleFragment.searched_vehicle_recycler_view.setVisibility(View.GONE);
                         SearchVehicleFragment.card_view.setVisibility(View.GONE);
                         SearchVehicleFragment.res.setVisibility(View.GONE);
                    }
                    else{
                        SearchVehicleFragment.noData.setVisibility(View.GONE);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setVisibility(View.VISIBLE);
                        SearchVehicleFragment.card_view.setVisibility(View.VISIBLE);
                        SearchVehicleFragment.res.setVisibility(View.VISIBLE);
                    }
                    try{
                        SearchVehicleFragment.mAdapter = new Search_Vehicles_RecyclerViewAdapter(this,MainActivity.vehicleResponce.getSearchVehicleBeans());
                        SearchVehicleFragment.searched_vehicle_recycler_view.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setLayoutManager(mLayoutManager);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        SearchVehicleFragment.mAdapter.notifyDataSetChanged();
                        SearchVehicleFragment.searched_vehicle_recycler_view.setAdapter(SearchVehicleFragment.mAdapter);

                    }catch (Exception ex){
                        Log.e("Exception "," "+ex);
                    }
                }
                else if(vehicleResponce.getCode().equalsIgnoreCase("NEED_LOGIN"))
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    //Toasty.error(this, "Please try again", Toast.LENGTH_SHORT, true).show();
                }
                else
                {
                    drawer.closeDrawer(GravityCompat.START);
                    Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Search Vehicle"," "+ex);
            }
        }

        if (callFor.equals(CallFor.GET_VEHICLES)) {
            vehicleResponce=new Gson().fromJson(response,VehicleResponce.class);
            try {
                if (vehicleResponce.getCode().equalsIgnoreCase("SUCCESS"))
                {
                    int size = vehicleResponce.getVehicleBeans().size();
                    if(size==0){
                        AddvehicleFragment.no_Data.setVisibility(View.VISIBLE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.GONE);
                    }
                    else{
                        AddvehicleFragment.no_Data.setVisibility(View.GONE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.VISIBLE);
                    }
                    try{
                        AddvehicleFragment.mAdapter = new VehicleListAdapter(vehicleResponce.getVehicleBeans(),getApplicationContext());
                        AddvehicleFragment.vehicle_List.setAdapter(AddvehicleFragment.mAdapter);

                        SearchVehicleFragment.mAdapter = new Search_Vehicles_RecyclerViewAdapter(this,MainActivity.vehicleResponce.getSearchVehicleBeans());
                        SearchVehicleFragment.searched_vehicle_recycler_view.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setLayoutManager(mLayoutManager);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        SearchVehicleFragment.mAdapter.notifyDataSetChanged();
                        SearchVehicleFragment.searched_vehicle_recycler_view.setAdapter(SearchVehicleFragment.mAdapter);

                    }catch (Exception ex){
                        Log.e("Exception "," "+ex);
                    }
                }
                else if(vehicleResponce.getCode().equalsIgnoreCase("NEED_LOGIN"))
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    Toasty.error(this, "Please try again", Toast.LENGTH_SHORT, true).show();
                }
                else
                {
                    drawer.closeDrawer(GravityCompat.START);
                    Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Get Vehicles"," "+ex);
            }
        }

        if (callFor.equals(CallFor.ADD_VEHICLE)) {
            vehicleResponce=new Gson().fromJson(response,VehicleResponce.class);
            try {
                if (vehicleResponce.getCode().equalsIgnoreCase("SUCCESS"))
                {
                    SearchVehicleFragment.noData.setVisibility(View.GONE);
                    SearchVehicleFragment.searched_vehicle_recycler_view.setVisibility(View.GONE);
                    SearchVehicleFragment.card_view.setVisibility(View.GONE);
                    SearchVehicleFragment.res.setVisibility(View.GONE);
                    AddvehicleFragment.vehicleNumber.setText("");
                    int size = vehicleResponce.getVehicleBeans().size();
                    if(size==0){
                        AddvehicleFragment.no_Data.setVisibility(View.VISIBLE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.GONE);
                    }
                    else{
                        AddvehicleFragment.no_Data.setVisibility(View.GONE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.VISIBLE);
                    }
                    try{
                        AddvehicleFragment.mAdapter = new VehicleListAdapter(vehicleResponce.getVehicleBeans(),getApplicationContext());
                        AddvehicleFragment.vehicle_List.setAdapter(AddvehicleFragment.mAdapter);

                        SearchVehicleFragment.mAdapter = new Search_Vehicles_RecyclerViewAdapter(this,MainActivity.vehicleResponce.getSearchVehicleBeans());
                        SearchVehicleFragment.searched_vehicle_recycler_view.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setLayoutManager(mLayoutManager);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        SearchVehicleFragment.mAdapter.notifyDataSetChanged();
                        SearchVehicleFragment.searched_vehicle_recycler_view.setAdapter(SearchVehicleFragment.mAdapter);

                    }catch (Exception ex){
                        Log.e("Exception"," "+ex);
                    }
                }
                else if(vehicleResponce.getCode().equalsIgnoreCase("NEED_LOGIN"))
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    Toasty.error(this, "Please try again", Toast.LENGTH_SHORT, true).show();
                }
                else
                {
                    drawer.closeDrawer(GravityCompat.START);
                    Toasty.error(this, vehicleResponce.getMessage() , Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Add Vehicle"," "+ex);
            }
        }

        if (callFor.equals(CallFor.DELETE_VEHICLE)) {
            vehicleResponce=new Gson().fromJson(response,VehicleResponce.class);
            try {
                if (vehicleResponce.getCode().equalsIgnoreCase("SUCCESS"))
                {
                    SearchVehicleFragment.noData.setVisibility(View.GONE);
                    SearchVehicleFragment.searched_vehicle_recycler_view.setVisibility(View.GONE);
                    SearchVehicleFragment.card_view.setVisibility(View.GONE);
                    SearchVehicleFragment.res.setVisibility(View.GONE);
                    int size = vehicleResponce.getVehicleBeans().size();
                    if(size==0){
                        AddvehicleFragment.no_Data.setVisibility(View.VISIBLE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.GONE);
                    }
                    else{
                        AddvehicleFragment.no_Data.setVisibility(View.GONE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.VISIBLE);
                    }
                    try{
                        AddvehicleFragment.mAdapter = new VehicleListAdapter(vehicleResponce.getVehicleBeans(),getApplicationContext());

                        AddvehicleFragment.vehicle_List.setAdapter(AddvehicleFragment.mAdapter);

                        SearchVehicleFragment.mAdapter = new Search_Vehicles_RecyclerViewAdapter(this,MainActivity.vehicleResponce.getSearchVehicleBeans());
                        SearchVehicleFragment.searched_vehicle_recycler_view.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setLayoutManager(mLayoutManager);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        SearchVehicleFragment.mAdapter.notifyDataSetChanged();
                        SearchVehicleFragment.searched_vehicle_recycler_view.setAdapter(SearchVehicleFragment.mAdapter);

                    }catch (Exception ex){
                        Log.e("Exception Vehicle"," "+ex);
                    }
                }
                else if(vehicleResponce.getCode().equalsIgnoreCase("NEED_LOGIN"))
                {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    Toasty.error(this, "Please try again", Toast.LENGTH_SHORT, true).show();
                }
                else if (vehicleResponce.getCode().equalsIgnoreCase("ERROR"))
                {
                    Toasty.error(this, vehicleResponce.getMessage(), Toast.LENGTH_SHORT, true).show();
                    SearchVehicleFragment.noData.setVisibility(View.GONE);
                    SearchVehicleFragment.searched_vehicle_recycler_view.setVisibility(View.GONE);
                    SearchVehicleFragment.card_view.setVisibility(View.GONE);
                    SearchVehicleFragment.res.setVisibility(View.GONE);
                    int size = vehicleResponce.getVehicleBeans().size();
                    if(size==0){
                        AddvehicleFragment.no_Data.setVisibility(View.VISIBLE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.GONE);
                    }
                    else{
                        AddvehicleFragment.no_Data.setVisibility(View.GONE);
                        AddvehicleFragment.vehicle_List.setVisibility(View.VISIBLE);
                    }
                    try{
                        AddvehicleFragment.mAdapter = new VehicleListAdapter(vehicleResponce.getVehicleBeans(),getApplicationContext());

                        AddvehicleFragment.vehicle_List.setAdapter(AddvehicleFragment.mAdapter);

                        SearchVehicleFragment.mAdapter = new Search_Vehicles_RecyclerViewAdapter(this,MainActivity.vehicleResponce.getSearchVehicleBeans());
                        SearchVehicleFragment.searched_vehicle_recycler_view.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setLayoutManager(mLayoutManager);
                        SearchVehicleFragment.searched_vehicle_recycler_view.setItemAnimator(new DefaultItemAnimator());
                        SearchVehicleFragment.mAdapter.notifyDataSetChanged();
                        SearchVehicleFragment.searched_vehicle_recycler_view.setAdapter(SearchVehicleFragment.mAdapter);

                    }catch (Exception ex){
                        Log.e("Exception "," "+ex);
                    }
                }
                else
                {
                    drawer.closeDrawer(GravityCompat.START);
                    Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Delete Vehicle"," "+ex);
            }
        }

        if(callFor.equals(CallFor.LOCAL_STORES)){
            try{
                LocalStoreResponse result = new Gson().fromJson(response, LocalStoreResponse.class);
                if(result.getCode().equals("SUCCESS")){
                    LocalStoreFragment.localStores.clear();
                    if(result.getData().size() == 0){
                        result.getData().add(null);
                        LocalStoreFragment.NoDataLayout.setVisibility(View.VISIBLE);
                        LocalStoreFragment.local_store_recycler_view.setVisibility(View.GONE);
                    }
                    else{
                        LocalStoreFragment.NoDataLayout.setVisibility(View.GONE);
                        LocalStoreFragment.local_store_recycler_view.setVisibility(View.VISIBLE);
                        LocalStoreFragment.localStores.addAll(result.getData());
                        LocalStoreFragment.localStoreAdapter.notifyDataSetChanged();
                    }

                }else if(result.getCode().equalsIgnoreCase("NEED_LOGIN")) {
                    UserBean userBean = new UserBean();
                    userBean.setUser_name(SheredPref.getUsername(this));
                    userBean.setUser_password(SheredPref.getPassword(this));
                    new PostData(new Gson().toJson(userBean), this, CallFor.LOGIN).execute();
                    Toasty.error(this, "Please try again", Toast.LENGTH_SHORT, true).show();

                }else
                {
                    drawer.closeDrawer(GravityCompat.START);
                    Toasty.error(this, "Something went wrong, Please try again", Toast.LENGTH_SHORT, true).show();
                }
            }catch (Exception ex)
            {
                Log.e("Ex Delete Vehicle"," "+ex);
            }
        }
    }

    public static void ShowErrorAlert() {
          new SweetAlertDialog(MainContext, SweetAlertDialog.ERROR_TYPE)
                  .setTitleText("Oops...")
                  .setContentText("Check your internet connection")
                  .show();
    }

    public void showUpdateDialog(String versionInfo,Boolean value){
        final HeaderDialog headerDialog = new HeaderDialog(this);
        headerDialog.setColor(getResources().getColor(R.color.colorPrimary));
        //headerDialog.setIcon(getResources().getDrawable(R.drawable.female));
        headerDialog.setTitle("Update VZ Track Application");
        headerDialog.setMessage("New version of VZ Track application available on play store. Please update VZ Track application to get updated features - "+versionInfo); // Sets the dialog message
        headerDialog.setTitleColor(Color.parseColor("#FFFFFF"));
        headerDialog.justifyContent(true);
        headerDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("https://play.google.com/store/apps/details?id=vztrack.gls.com.vztrack_user&hl=en"));
                startActivity(viewIntent);
            }
        });

        if(value==true)
        {
            headerDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    finish();
                }
            });
            headerDialog.setCancelable(false);
        }else{
            headerDialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SheredPref.setLatestAppVersion(MainContext,appVersion);
                }
            });
            headerDialog.setCancelable(false);
        }
        headerDialog.show();
    }

    private void setMenuCounter(@IdRes int itemId, int count) {
        LinearLayout LinearView = (LinearLayout) navigationView.getMenu().findItem(itemId).getActionView().findViewById(R.id.counterLinearView);
        TextView view = (TextView) navigationView.getMenu().findItem(itemId).getActionView().findViewById(R.id.counterView);
        view.setText(count > 0 ? String.valueOf(count) : null);
        if(count!=0){
            LinearView.setVisibility(View.VISIBLE);
        }
        else{
            LinearView.setVisibility(View.GONE);
        }
    }

    public static void logShopCall(final String shopId){
        class LogCall extends AsyncTask{
            @Override
            protected Object doInBackground(Object[] params) {
                String url = Constants.BASE_URL+ URL.STORE_CALL_LOG+"?storeId="+shopId;
                String result = ServerConnection.giveResponse(url,"");
                Log.e("URL",url);
                Log.e("Response", result);
                return null;
            }
        }

        new LogCall().execute();
    }
}