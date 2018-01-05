package vztrack.gls.com.vztrack_user;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;

import java.util.ArrayList;

import vztrack.gls.com.vztrack_user.beans.NotificationBean;
import vztrack.gls.com.vztrack_user.utils.SheredPref;
import vztrack.gls.com.vztrack_user.utils.WakeLocker;

/**
 * Created by sandeep on 29/4/16.
 */
public class GcmBroadcastReceiver extends BroadcastReceiver {
    public static String NotificationFlag;
    public static long[] vibration = {0,500, 200, 500};
    public static Uri uri;
    public static String notification_check;
    public static String sound_check;
    public static String vibration_check;
    private String title;
    private String message;
    private NotificationBean notificationBean;
    public static ArrayList<NotificationBean> notificationBeenAll = new ArrayList<NotificationBean>();
    public static  int cnt;
    public static  int cnt_pro=0;
    private String result;
    private String redirectTo;

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.e("gcm_debug", "PushReceiver onReceive called");
        WakeLocker.acquire(context.getApplicationContext());
        notification_check = SheredPref.getNotification(context);
        sound_check = SheredPref.getSound(context);
        vibration_check = SheredPref.getVibration(context);

        if(!notification_check.equals("DISABLE"))
        {
            Bundle extras = intent.getExtras();
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            String msgType = gcm.getMessageType(intent);
            if(!extras.isEmpty()){
                if(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(msgType)){
                    Log.i("gcm_debug", "Message send error");
                }else if(GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(msgType)){
                    Log.i("gcm_debug", "Message deleted");
                }else if(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(msgType)){
                    Log.i("gcm_debug", "Message received : " + extras.toString());
                    try
                    {
                        cnt++;
                        title = extras.get("title").toString();
                        message = extras.get("message").toString();
                        notificationBean = new NotificationBean();
                        result = extras.get("object").toString();
                        redirectTo = extras.get("redirectTo").toString();

                    }
                    catch (Exception ex)
                    {
                        Log.e("Exep In Getting Values"," "+ex);
                    }

                    NotificationFlag="0";
                    try{
                        if(title.contains("You have notice "))
                        {
                            int noticeCnt = SheredPref.getNoticeConut(context);
                            noticeCnt = noticeCnt+1;
                            SheredPref.setNoticeCount(context,noticeCnt);
                            NotificationFlag = "2";
                        }
                        else if(title.contains("You have visitor "))
                        {
                            NotificationFlag = "1";
                        }
                        else if(title.contains("You have new message")){
                            NotificationFlag = "4";
                        }
                        else if (title.contains("Please Rate Visited ")) {
                            NotificationFlag = "3";
                            cnt_pro++;
                            Gson gson = new Gson();
                            notificationBean = gson.fromJson(result, NotificationBean.class);
                            notificationBeenAll.add(notificationBean);
                        } else {
                            NotificationFlag = "1";
                        }

                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                        builder.setSmallIcon(R.drawable.ic_stat_vz);
                        builder.setContentTitle(title);
                        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.vz_track_large);
                        builder.setLargeIcon(largeIcon);
                        builder.setContentText(message);

                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                        bigText.bigText(message);
                        bigText.setBigContentTitle(title);
                        bigText.setSummaryText("By : VZ Track");
                        builder.setStyle(bigText);

                        if(GcmBroadcastReceiver.sound_check.equals("ENABLE"))
                        {
                            GcmBroadcastReceiver.uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                            String ringtone = SheredPref.getNotificationRingtone(context);
                            if(ringtone.equals("")){
                                builder.setSound(GcmBroadcastReceiver.uri);
                            }else{
                                builder.setSound(Uri.parse(ringtone));
                            }
                        }
                        else
                        {
                            builder.setSound(null);
                        }

                        if(GcmBroadcastReceiver.vibration_check.equals("ENABLE")) {
                            builder.setVibrate(GcmBroadcastReceiver.vibration);
                        }
                        else
                        {
                            builder.setVibrate(null);
                        }
                        builder.setAutoCancel(true);
                        Intent intent1;


                        if(title.contains("Please Rate Visited "))
                        {
                            if(cnt_pro==1){
                                intent1 = new Intent(context,RatingActivity.class);
                                intent1.putExtra("VISITOR_NAME",notificationBean.getVisitorName());
                                intent1.putExtra("VISIT_PURPOSE",notificationBean.getVisitorPurpose());
                                intent1.putExtra("IN_TIME",notificationBean.getInTime());
                                intent1.putExtra("MOBILE_NO",notificationBean.getVisitorMobile());
                                intent1.putExtra("VISITR_PHOTO",notificationBean.getVisitorPhoto());
                                intent1.putExtra("FROM","Notification");
                                TaskStackBuilder tsb = TaskStackBuilder.create(context);
                                tsb.addParentStack(RatingActivity.class);
                                tsb.addNextIntent(intent1);
                                PendingIntent pendingIntent = tsb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                            }
                            else{
                                intent1 = new Intent(context,MainActivity.class);
                                intent1.putExtra("NOT_FLAG",NotificationFlag);
                                TaskStackBuilder tsb = TaskStackBuilder.create(context);
                                tsb.addParentStack(MainActivity.class);
                                tsb.addNextIntent(intent1);
                                PendingIntent pendingIntent = tsb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                                builder.setContentIntent(pendingIntent);
                            }

                        }
                        else{
                            intent1 = new Intent(context,MainActivity.class);
                            intent1.putExtra("NOT_FLAG",NotificationFlag);
                            TaskStackBuilder tsb = TaskStackBuilder.create(context);
                            tsb.addParentStack(MainActivity.class);
                            tsb.addNextIntent(intent1);
                            PendingIntent pendingIntent = tsb.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
                            builder.setContentIntent(pendingIntent);
                        }

                        NotificationManager  notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(cnt,builder.build());

                        WakeLocker.release();

                    }catch (Exception ex){
                        Log.e("Exception "," "+ex);
                    }
                }
            }
            setResultCode(Activity.RESULT_OK);
        }

    }
}