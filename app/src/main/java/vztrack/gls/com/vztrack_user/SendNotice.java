package vztrack.gls.com.vztrack_user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import mehdi.sakout.fancybuttons.FancyButton;
import vztrack.gls.com.vztrack_user.beans.NoticeBean;
import vztrack.gls.com.vztrack_user.responce.LoginResponse;
import vztrack.gls.com.vztrack_user.utils.CallFor;
import vztrack.gls.com.vztrack_user.utils.CheckConnection;
import vztrack.gls.com.vztrack_user.utils.CleverTap;
import vztrack.gls.com.vztrack_user.utils.Events;
import vztrack.gls.com.vztrack_user.utils.PostData;
import vztrack.gls.com.vztrack_user.utils.SheredPref;

public class SendNotice extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private String strStartDate,strEndDate,encodedPhotoImage;
    FancyButton startDate,endDate;
    int flag;
    EditText heading,description;
    TextView counterHeading,counterDescription;
    ImageView imgSelectedImage;
    String[] months ;
    private static int RESULT_LOAD_IMAGE = 1;
    TextView tvAddPhoto;
    CheckConnection cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Send Notice");
        setContentView(R.layout.activity_send_notice);
        cc = new CheckConnection(this);
        startDate = (FancyButton) findViewById(R.id.btnStartDate);
        endDate = (FancyButton) findViewById(R.id.btnEndDate);

        heading = (EditText) findViewById(R.id.input_heading);
        description = (EditText) findViewById(R.id.input_description);

        counterHeading = (TextView) findViewById(R.id.counterHeading);
        counterDescription = (TextView) findViewById(R.id.counterDesc);

        imgSelectedImage = (ImageView) findViewById(R.id.imgSelectedImage);

        tvAddPhoto = (TextView) findViewById(R.id.tvAddPhoto);

        heading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                counterHeading.setText(editable.toString().length() + " / max 100");
            }
        });

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                counterDescription.setText(editable.toString().length() + " / max 1000");
            }
        });

        months = getResources().getStringArray(R.array.months);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=1;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SendNotice.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag=2;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        SendNotice.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int mon = monthOfYear+1;
        String date = dayOfMonth+" "+months[monthOfYear]+" "+year;
        if(flag==1){
            strStartDate =  dayOfMonth+"/"+mon+"/"+year;
            startDate.setText(date);
        }
        if(flag==2){
            endDate.setText(date);
            strEndDate =  dayOfMonth+"/"+mon+"/"+year;
        }

    }

    public void Send(View v){
        CleverTap.cleverTap_Record_Event(this, Events.event_send_notice_button);
        String strHeading = heading.getText().toString().trim();
        String strDescription = description.getText().toString().trim();
        String strInStartDate = startDate.getText().toString().trim();
        String strInEndDate = endDate.getText().toString().trim();

        if(strHeading.equals("")){
            Toasty.info(this, "Notice heading should not be blank", Toast.LENGTH_SHORT, true).show();
        }else if(strInStartDate.equals("Select Start Date")){
            Toasty.info(this, "Please select start date", Toast.LENGTH_SHORT, true).show();
        }
        else if(strInEndDate.equals("Select End Date")){
            Toasty.info(this, "Please select end date", Toast.LENGTH_SHORT, true).show();
        }else if(strDescription.equals(""))
        {
            Toasty.info(this, "Notice description should not be blank", Toast.LENGTH_SHORT, true).show();
        }
        else if(!cc.isConnectingToInternet())
        {
            Toasty.info(this, "Please check internet connection", Toast.LENGTH_SHORT, true).show();
        }
        else{
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // here set the pattern as you date in string was containing like date/month/year
                Date sDate = sdf.parse(strStartDate);
                Date eDate = sdf.parse(strEndDate);

                Calendar c = Calendar.getInstance();
                String formattedDate = sdf.format(c.getTime());
                Date today = sdf.parse(formattedDate);
                if (eDate.compareTo(sDate)<0)
                {
                    Toast.makeText(this, "Start date should not be greater than end date", Toast.LENGTH_SHORT).show();
                }
                else if(eDate.compareTo(today)<0){
                    Toast.makeText(this, "End date should not be past date", Toast.LENGTH_SHORT).show();
                }
                else if(today.compareTo(sDate)<0){
                    Toast.makeText(this, "Start date should not be future date", Toast.LENGTH_SHORT).show();
                }
                else{
                    NoticeBean notice = new NoticeBean();
                    notice.setNoticeHeading(strHeading);
                    notice.setNoticeDesc(strDescription);
                    notice.setNoticeStartDate(strInStartDate);
                    notice.setNoticeEndDate(strInEndDate);
                    notice.setSocityId(Integer.parseInt(SheredPref.getSocietyId(this)));
                    notice.setNoticeBanner(encodedPhotoImage);
                    new PostData(new Gson().toJson(notice), SendNotice.this, CallFor.SEND_NOTICE).execute();
                }
            }catch(ParseException ex){
                Log.e("Ex Send Notice",""+ex);
            }
        }
    }

    public void showGallary(View v){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, RESULT_LOAD_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            tvAddPhoto.setVisibility(View.GONE);
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            selectedImage = getResizedBitmap(selectedImage, 400);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            selectedImage.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] byte_arr = stream.toByteArray();
            encodedPhotoImage = Base64.encodeToString(byte_arr, 0);
            imgSelectedImage.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
           Log.e("Ex "," "+e);
        }
        catch (Exception ex){
            Log.e("Ex "," "+ex);
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void onGetResponse(String response, String callFor) {
        LoginResponse loginResponse=new Gson().fromJson(response,LoginResponse.class);
        if (loginResponse.getCode().equals("SUCCESS")){
            this.finish();
            Toasty.success(this,"Notice sent successfully",Toast.LENGTH_LONG).show();
        }
        else{
            Toasty.error(this,"Error in sending notice",Toast.LENGTH_LONG).show();;
        }
    }
}
