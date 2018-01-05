package vztrack.gls.com.vztrack_user.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.clevertap.android.sdk.CleverTapAPI;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by sandeep on 10/5/17.
 */


public class CleverGCM extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        if (GoogleCloudMessaging. MESSAGE_TYPE_MESSAGE.equals(messageType)) {
            CleverTapAPI.createNotification(context, extras);
        }
    }
}

