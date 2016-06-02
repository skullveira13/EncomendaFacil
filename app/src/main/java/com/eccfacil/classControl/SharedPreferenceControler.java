package com.eccfacil.classControl;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by skullveira on 13/05/2016.
 */
public class SharedPreferenceControler {

    Context context;

    public SharedPreferenceControler(Context _context){
        this.context = _context;
    }

    public SharedPreferences getPreferences(){
        return context.getSharedPreferences("ECCFACILUSR", context.MODE_PRIVATE );
    }
    public void savePreferences(String key, String value)    {
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void saveBooleanPreference(String key, Boolean value){
        SharedPreferences sharedPreferences = getPreferences();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public Boolean loadBooleanPreferences(String key){
        try {
            SharedPreferences sharedPreferences = getPreferences();
            Boolean loadMemo = sharedPreferences.getBoolean(key, false);
            return loadMemo;
        } catch (Exception ex)
        {
            Log.e("Erro_Pereference",ex.getMessage());
            return false;
        }
    }

    public String loadPreferences(String key){
        try {
            SharedPreferences sharedPreferences = getPreferences();
            String loadMemo = sharedPreferences.getString(key, "");
            return loadMemo;
        } catch (Exception ex)
        {
            Log.e("Erro_Pereference",ex.getMessage());
            return null;
        }
    }
    public boolean deletePreferences(String key){
        SharedPreferences.Editor editor=getPreferences().edit();
        editor.remove(key).commit();
        return false;
    }

    public boolean deleteLoginKeys() {
        try {
            SharedPreferences.Editor editor = getPreferences().edit();
            editor.remove("email");
            editor.remove("senha");
            editor.commit();
        }catch (Exception ex){
            Log.e("Error_Preference", ex.toString());
            return  false;
        }

        return true;
    }
}
