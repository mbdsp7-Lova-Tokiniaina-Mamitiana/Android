package com.example.BetSoccer_android.ui.authstate;

import android.content.Intent;
import android.os.Bundle;

import com.example.BetSoccer_android.ui.language.ActivityWithLanguage;
import com.example.BetSoccer_android.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class AuthState extends ActivityWithLanguage {
    private FirebaseAuth authState;


    public String getUid(){
        String uid="";
        FirebaseUser currentUser = authState.getCurrentUser();
        if(currentUser!=null){
            uid=currentUser.getUid();
        }
        return uid;
    }
    private void checkAuthstate(){
        FirebaseUser currentUser = authState.getCurrentUser();
        if(currentUser==null){
            redirectToLogin();
        }
    }
    public void redirectToLogin(){

        Intent intent = new Intent(getBaseContext(), LoginActivity.class);

        finish();
        startActivity(intent);
    }
    public void logout(){
        authState.signOut();
        redirectToLogin();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authState=FirebaseAuth.getInstance();
    }
    @Override
    public void onStart() {
        super.onStart();
//       / checkAuthstate();
    }
}
