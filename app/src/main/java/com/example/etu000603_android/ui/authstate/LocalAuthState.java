package com.example.etu000603_android.ui.authstate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.example.etu000603_android.service.AuthService;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.login.LoginActivity;
import com.example.etu000603_android.ui.pari.PariActvity;

import org.json.JSONException;

public class LocalAuthState extends ActivityWithLanguage {
    String uid;
    private static final String AUTH_PREF = "AuthSharedPref";
    private static final String TOKEN_PREF = "ACCESSTOKEN";

    public String getAuthToken() {
        return getApplicationContext().getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
                .getString(TOKEN_PREF, null);
    }

    public String getUid(){
        String uid = "";
        try {
                AuthService.getUser(this, LocalAuthState.this.getClass().getMethod("assignUserId", String.class), getAuthToken());
        } catch (JSONException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return uid;
    }

    public void assignUserId(String result) {
        uid = result;
    }

    public void checkAuthstate() {
        if (getAuthToken() == null) {
            redirectToLogin();
        }
    }

    public void redirectToLogin(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        finish();
        startActivity(intent);
    }
    public void redirectToAccueil(){
        Intent intent = new Intent(getBaseContext(), PariActvity.class);
        finish();
        startActivity(intent);
    }

    public void logout(){
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        redirectToAccueil();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
       // checkAuthstate();
    }
}
