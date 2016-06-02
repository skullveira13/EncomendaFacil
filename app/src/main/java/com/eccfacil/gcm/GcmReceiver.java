package com.eccfacil.gcm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by skullveira on 19/05/2016.
 */
public class GcmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            Log.d("Erro_Notificao", "Receiver intent null");
        } else {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
            String msgType = gcm.getMessageType(intent);

        }
    }
}
