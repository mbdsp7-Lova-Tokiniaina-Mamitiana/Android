package com.example.etu000603_android.ui.authstate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.etu000603_android.data.model.User;
import com.example.etu000603_android.data.repository.ProfilRepository;
import com.example.etu000603_android.service.AuthService;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.login.LoginActivity;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.utils.Session;

import org.json.JSONException;

public class LocalAuthState extends ActivityWithLanguage {
    String uid;
    private static final String AUTH_PREF = "AuthSharedPref";
    private static final String TOKEN_PREF = "ACCESSTOKEN";
    private ProfilRepository repository=new ProfilRepository();

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
        String auth =getAuthToken();
        if (auth == null) {
            redirectToLogin();
        }
    }

    public void redirectToLogin(){
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);

        startActivity(intent);
    }
    public void redirectToAccueil(){
        Intent intent = new Intent(getBaseContext(), PariActvity.class);
        finish();
        startActivity(intent);
    }
    public boolean isConnected(){


        return Session.profil != null;
    }

    public void logout(){
        System.out.println("Tena logout");
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        redirectToAccueil();
    }
    private void checkAuthToken(){
        String token = getAuthToken();
        if(token != null){
            repository.getAuthInfo(token,this);
        }
    }
    public void checkProfil(User user){
        String token = getAuthToken();
        if(token != null){
            repository.getProfil(user,this);
        }
    }
    public void setSession(User user){
        System.out.println("Set session");
        Session.profil =user;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //check Profil

        super.onCreate(savedInstanceState);
        checkAuthToken();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
