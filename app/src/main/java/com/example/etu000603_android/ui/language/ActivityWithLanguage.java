package com.example.etu000603_android.ui.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;


public class ActivityWithLanguage extends AppCompatActivity {


    private String SHARED_PREFERENCES_NAME="Toky_businnes";
    private String LANGUAGE="Toky_businness_language";

    public boolean loading=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        checkLanguage();

    }

    public String getStoredLanguage() {
        String retour = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
                .getString(LANGUAGE, getResources().getConfiguration().locale.getLanguage());


        return retour;
    }
    private void persistLanguage(String langue) {
        getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE).edit()
                .putString(LANGUAGE, langue)
                .commit();


    }
    public void checkLanguage(){
        try {
            String lang=getStoredLanguage();
            setLocale(lang,true);
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
    public void setLocale(String lang,boolean init) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        onConfigurationChanged(conf);
        persistLanguage(lang);
        if(!init){
            finish();
            startActivity(getIntent());
        }


    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = newConfig.locale;
        res.updateConfiguration(conf,dm);
        getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());


    }

}
