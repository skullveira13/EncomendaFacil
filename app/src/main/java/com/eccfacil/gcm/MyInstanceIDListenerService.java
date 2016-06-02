package com.eccfacil.gcm;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Guilhermando Amorim on 17/05/2016.
 */
public class MyInstanceIDListenerService extends InstanceIDListenerService{

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Intent intent = new Intent(this,RegistrationIntentService.class);
        startService(intent);

    }
}
