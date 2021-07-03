package com.example.etu000603_android.ui.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.etu000603_android.R;

import java.util.Locale;

import jp.wasabeef.blurry.Blurry;


public class ActivityWithLanguage extends AppCompatActivity {


    private String SHARED_PREFERENCES_NAME="Toky_businnes";
    private String LANGUAGE="Toky_businness_language";
    private PopupWindow popupMessage =null;
    public boolean loading=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        checkLanguage();
        initialisePopupError();

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
    private void initialisePopupError(){
        popupMessage =  new PopupWindow(this.getBaseContext());
        View customView = getLayoutInflater().inflate(R.layout.popup_error, null);
        final ImageButton bouton = customView.findViewById(R.id.close_button);

        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMessage.dismiss();
            }
        });
        final View back = this.getWindow().getDecorView().getRootView();
        popupMessage.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {


                System.out.println("dismiss");
            }
        });
        popupMessage.setElevation(0F);
        popupMessage.setContentView(customView);
        popupMessage.setBackgroundDrawable(null);
        popupMessage.setOutsideTouchable(true);
    }
    public void showMessage(String message,boolean error){
       // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        final View back = this.getWindow().getDecorView().getRootView();
        try{
            TextView textView = popupMessage.getContentView().findViewById(R.id.text_message);
            textView.setText(message);
            ImageView imageView = popupMessage.getContentView().findViewById(R.id.icon_message);
            if(error){
                imageView.setImageDrawable(getDrawable(R.drawable.error));
            }
            popupMessage.showAtLocation(back, Gravity.TOP, 0, 140);
        }catch (Exception exc){
            exc.printStackTrace();
        }


    }
    public void checkLanguage(){
        try {
            String lang="fr";
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
