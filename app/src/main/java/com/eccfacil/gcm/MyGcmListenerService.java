package com.eccfacil.gcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.eccfacil.encomendafacil.MainActivity;
import com.eccfacil.encomendafacil.R;
import com.google.android.gms.gcm.GcmListenerService;

/**
 * Created by skullveira on 17/05/2016.
 */
public class MyGcmListenerService extends GcmListenerService{
    private static final String TAG = "TAG";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        //super.onMessageReceived(from, data);
        String titulo = data.getString("Titulo");
        String menssagem = data.getString("Mensagem");

        sendNotification(titulo,menssagem);
    }

    private void sendNotification(String titulo, String menssagem) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(menssagem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());

    }
}
