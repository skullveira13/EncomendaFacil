package com.eccfacil.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.eccfacil.classControl.SharedPreferenceControler;
import com.eccfacil.encomendafacil.R;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by skullveira on 17/05/2016.
 */
public class RegistrationIntentService extends IntentService {

    public static  final String TAG = "LOG";
    public static  final String ServerToken = "";

    public RegistrationIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreferenceControler sharedPreferenceControler = new SharedPreferenceControler(this);
        Boolean status = sharedPreferenceControler.loadBooleanPreferences("TokenSaved");

        synchronized (TAG){
            InstanceID instanceID = InstanceID.getInstance(this);
            try {
                if(!status) {
                    String tokenId = instanceID.getToken(String.valueOf(R.string.gcm_IdProjeto), GoogleCloudMessaging.INSTANCE_ID_SCOPE,null);

                    sharedPreferenceControler = new SharedPreferenceControler(this);
                    sharedPreferenceControler.savePreferences("TokenId", tokenId);
                    sharedPreferenceControler.saveBooleanPreference("TokenSaved",true);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Enviar o TokenID ao Banco de Dados
    private void salvarTokenWebservice(String token){

    }
}
