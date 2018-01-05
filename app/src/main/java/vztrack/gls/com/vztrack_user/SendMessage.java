package vztrack.gls.com.vztrack_user;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.thomashaertel.widget.MultiSpinner;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.beans.FlatResponce;
import vztrack.gls.com.vztrack_user.beans.MessageBean;
import vztrack.gls.com.vztrack_user.beans.MessageResponceBean;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.GetData;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class SendMessage extends BaseActivity{
    Switch layoutSwitch;
    MultiSpinner groupSpinner;
    MultiAutoCompleteTextView flatNumberAutoComplite;
    EditText input_message;
    LinearLayout groupLayout;
    CheckConnection cc;
    private MessageResponceBean messageResponceBean;
    private FlatResponce flatResponce = new FlatResponce();
    List<String> groupList = new ArrayList<String>();
    List<Integer> groupIDList = new ArrayList<Integer>();
    List<String> flatList = new ArrayList<String>();
    List<Integer> familyIdList = new ArrayList<Integer>();
    ArrayList<String> arrayOfFlats;
    LinearLayout ll;
    ArrayAdapter<String> adapter;
    TextView counterHeading;
    TextView counterDesc;
    HorizontalScrollView horizomtalScroll;
    String message;
    TextView empORflatText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Send Message");
        setContentView(R.layout.activity_send_message);
        layoutSwitch = (Switch) findViewById(R.id.layoutSwitch);
        flatNumberAutoComplite = (MultiAutoCompleteTextView) findViewById(R.id.flatNumberAutoComplite);
        input_message = (EditText) findViewById(R.id.input_message);
        groupLayout = (LinearLayout) findViewById(R.id.groupLayout);
        ll = (LinearLayout) findViewById(R.id.flatList);
        counterHeading = (TextView) findViewById(R.id.counterHeading);
        counterDesc = (TextView) findViewById(R.id.counterDesc);
        empORflatText = (TextView) findViewById(R.id.empORflatText);
        horizomtalScroll = (HorizontalScrollView) findViewById(R.id.horizomtalScroll);
        cc = new CheckConnection(this);
        layoutSwitch.setChecked(false);

        arrayOfFlats = new ArrayList<String>();

        groupSpinner = (MultiSpinner) findViewById(R.id.groupSpinner);

        boolean type = SheredPref.getType(this);
        // If type true then this is Company
        if(!type){
            empORflatText.setText("Flat No.");
            flatNumberAutoComplite.setHint("Select Flat Number");
        }
        else{
            empORflatText.setText("Employee No.");
            flatNumberAutoComplite.setHint("Select Employee Id");
        }


        groupSpinner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(groupList.size()==0){
                    Toasty.error(SendMessage.this,"No groups added yet",Toast.LENGTH_LONG).show();
                }

                adapter = new ArrayAdapter<String>(SendMessage.this, android.R.layout.simple_spinner_item);
                adapter.addAll(groupList);
                groupSpinner.setAdapter(adapter, false, onSelectedListener);
                return false;
            }
        });

        groupSpinner.setHint("----- SELECT GROUP -----");

        input_message.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                counterDesc.setText(s.length()+" / max 500");
            }
        });

        layoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    groupSpinner.setText("----- SELECT GROUP -----");
                    groupLayout.setBackgroundColor(Color.parseColor("#B3B3B3"));
                    groupLayout.setAlpha(0.3f);
                    groupSpinner.setEnabled(false);
                    groupSpinner.setClickable(false);
                    flatNumberAutoComplite.setEnabled(false);
                    flatNumberAutoComplite.setClickable(false);
                    flatNumberAutoComplite.setText("");
                    input_message.setText("");
                    counterHeading.setText("Added 0");
                    ll.removeAllViews();
                }else{
                    groupLayout.setBackgroundColor(Color.parseColor("#E8E8E8"));
                    groupLayout.setAlpha(1);
                    groupSpinner.setEnabled(true);
                    groupSpinner.setClickable(true);
                    flatNumberAutoComplite.setEnabled(true);
                    flatNumberAutoComplite.setClickable(true);
                }
            }
        });

        if(cc.isConnectingToInternet()){
            new GetData(this, CallFor.GET_GROUPS, "").execute();
        }else{
            Toasty.error(this,"Please Check Internet Connection",Toast.LENGTH_LONG).show();
        }

        flatNumberAutoComplite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String flatno = null;
                String selectedText = (String) parent.getAdapter().getItem(position).toString();
                flatNumberAutoComplite.setText("");

                try {
                    flatno =selectedText.split("-")[0].trim();
                 } catch (Exception ex) {
                }

                boolean contains = arrayOfFlats.contains(flatno);
                if(contains){
                    Toasty.error(SendMessage.this,"Already Added : "+flatno,Toast.LENGTH_SHORT).show();
                }
                else{
                    addItem(flatno);
                }
            }
        });
    }

    public void addItem(String flatno){
            arrayOfFlats.add(flatno);
            final LinearLayout linearLayout = new LinearLayout(this);
            counterHeading.setText("Added "+arrayOfFlats.size());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 0, 5, 0);
            linearLayout.setLayoutParams(lp);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View closeView = vi.inflate(R.layout.close_btn, null);

            final FancyButton btn = (FancyButton) closeView.findViewById(R.id.btn_close);
            btn.setText(flatno.trim());

            linearLayout.addView(closeView);
            ll.addView(linearLayout);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ll.getChildCount() == 0) {
                        ll.setVisibility(View.GONE);
                        arrayOfFlats.clear();
                        ((LinearLayout) linearLayout.getParent()).removeView(linearLayout);
                        View v = linearLayout.getChildAt(0);
                        arrayOfFlats.remove(btn.getText().toString().trim());
                    } else {
                        ll.setVisibility(View.VISIBLE);
                        ((LinearLayout) linearLayout.getParent()).removeView(linearLayout);
                        arrayOfFlats.remove(btn.getText().toString().trim());
                    }
                    counterHeading.setText("Added "+arrayOfFlats.size());
                }
            });
    }

    public void Send(View v) {
        if (cc.isConnectingToInternet()) {

        CleverTap.cleverTap_Record_Event(this, Events.event_send_message_button);
        message = input_message.getText().toString().trim();
        if (message.equals("")) {
            Toasty.error(this, "Message text should not be blank").show();
        } else {
            if (!layoutSwitch.isChecked()) {
                if (arrayOfFlats.size() <= 0) {
                    Toasty.error(this, "Please add Flat or Group to send messsage").show();
                } else {
                    List<Integer> selectedFamilyId = new ArrayList<Integer>();
                    List<Integer> selectedGroupId = new ArrayList<Integer>();
                    for (int i = 0; i < arrayOfFlats.size(); i++) {
                        String listItem = arrayOfFlats.get(i).trim();
                        if (listItem.contains("GROUP-")) {
                            String groupName = listItem.split("-")[1].trim();
                            for (int j = 0; j < groupList.size(); j++) {
                                if (groupName.equals(groupList.get(j).trim())) {
                                    int groupId = groupIDList.get(j);
                                    selectedGroupId.add(groupId);
                                }
                            }
                        } else {
                            for (int j = 0; j < flatList.size(); j++) {
                                String flatNo = flatList.get(j).split("-")[0].trim();
                                if (listItem.equals(flatNo)) {
                                    int familyid = familyIdList.get(j);
                                    selectedFamilyId.add(familyid);
                                }
                            }
                        }
                    }

                    int[] F_Ids = null;
                    int[] G_Ids = null;

                    for (int i = 0; i < selectedFamilyId.size(); i++) {
                        if (i == 0) {
                            F_Ids = new int[selectedFamilyId.size()];
                            F_Ids[i] = selectedFamilyId.get(i);
                        } else {
                            F_Ids[i] = selectedFamilyId.get(i);
                        }
                    }

                    for (int i = 0; i < selectedGroupId.size(); i++) {
                        if (i == 0) {
                            G_Ids = new int[selectedGroupId.size()];
                            G_Ids[i] = selectedGroupId.get(i);
                        } else {
                            G_Ids[i] = selectedGroupId.get(i);
                        }
                    }

                    // Send Message to group or flat
                    MessageBean messageBean = new MessageBean();
                    messageBean.setFamily_id(F_Ids);
                    messageBean.setGroupId(G_Ids);
                    messageBean.setMessage(message);
                    messageBean.setMessageToAll(false);
                    messageBean.setSocietyId(SheredPref.getSocietyId(this));
                    new PostData(new Gson().toJson(messageBean), this, CallFor.SEND_MESSAGE).execute();
                }
            } else {
                // Send Message to all
                MessageBean messageBean = new MessageBean();
                messageBean.setMessage(message);
                messageBean.setMessageToAll(true);
                messageBean.setSocietyId(SheredPref.getSocietyId(this));
                new PostData(new Gson().toJson(messageBean), this, CallFor.SEND_MESSAGE).execute();
            }
        }
        }else{
            Toasty.info(this, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    private MultiSpinner.MultiSpinnerListener onSelectedListener =
            new MultiSpinner.MultiSpinnerListener() {
        public void onItemsSelected(boolean[] selected) {

            if(groupIDList.size()==0){
                groupSpinner.setText("No Group Added Yet");
            }

                for(int i=0;i<selected.length;i++){
                    if(selected[i]){
                        String group_name = groupSpinner.getAdapter().getItem(i).toString();

                        boolean isSame = arrayOfFlats.contains("GROUP-"+group_name);

                        if(isSame){
                            Toasty.error(SendMessage.this,"Already added group : "+group_name,Toast.LENGTH_SHORT).show();
                        }else{
                            addItem("GROUP-"+group_name);
                            if(i+1==groupList.size()){
                                groupSpinner.setText("----- SELECT GROUP -----");
                            }
                        }
                    }
                }
        }
    };

    @Override
    public void onGetResponse(String response, String callFor) {
        messageResponceBean = new Gson().fromJson(response,MessageResponceBean.class);
        if(callFor.equals(CallFor.GET_GROUPS)){
            if (messageResponceBean.getCode().equals("SUCCESS")){
                for(int i =0;i<messageResponceBean.getMessageGroupBeans().size();i++){
                    String groupName = messageResponceBean.getMessageGroupBeans().get(i).getGroup_name();
                    int groupId = messageResponceBean.getMessageGroupBeans().get(i).getGroup_id();
                    groupIDList.add(groupId);
                    groupList.add(groupName);
                }

                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
                adapter.addAll(groupList);
                groupSpinner.setAdapter(adapter, false, onSelectedListener);

                new GetData(this, CallFor.GET_FLATS, "").execute();
            }
            else{
                Toasty.error(this,"Error",Toast.LENGTH_LONG).show();
            }
        }
        if(callFor.equals(CallFor.GET_FLATS)){
            if (messageResponceBean.getCode().equals("SUCCESS")){
                flatResponce = new Gson().fromJson(response,FlatResponce.class);
                for(int i =0;i<flatResponce.getAvailFlats().size();i++){
                    String flatNumber = flatResponce.getAvailFlats().get(i).getFlatNo();
                    String ownerName = flatResponce.getAvailFlats().get(i).getFlatOwnerName();
                    int familyId = flatResponce.getAvailFlats().get(i).getFamilyId();
                    familyIdList.add(familyId);
                    flatList.add(flatNumber+"-"+ownerName);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_list_item_1,
                        flatList );
                flatNumberAutoComplite.setThreshold(1);
                flatNumberAutoComplite.setAdapter(arrayAdapter);
                flatNumberAutoComplite.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
        }
        if(callFor.equals(CallFor.SEND_MESSAGE)){
            if (messageResponceBean.getCode().contains("SUCCESS")){
                groupSpinner.setText("----- SELECT GROUP -----");
                flatNumberAutoComplite.setText("");
                input_message.setText("");
                counterHeading.setText("Added 0");
                ll.removeAllViews();
                arrayOfFlats.clear();
                Toasty.success(this, "Successfully Sent Message").show();
                String message_id = messageResponceBean.getCode().split("-")[1];
                MessageBean messageBean = new MessageBean();
                messageBean.setMessageId(Integer.parseInt(message_id));
                messageBean.setSocietyId(SheredPref.getSocietyId(this));
                if (!layoutSwitch.isChecked()) {
                    messageBean.setMessageToAll(false);
                }else{
                    messageBean.setMessageToAll(true);
                }
                messageBean.setMessage(message);
                SheredPref.setRun(this, "DONT RUN");
                new PostData(new Gson().toJson(messageBean), this, CallFor.SEND_MESSAGE_NOTIFICATION).execute();
                this.finish();
            }
            else{
                Toasty.error(this, messageResponceBean.getMessage()).show();
            }
        }
        if(callFor.equals(CallFor.SEND_MESSAGE_NOTIFICATION)){
            SheredPref.setRun(this, "RUN");
        }
    }
}
