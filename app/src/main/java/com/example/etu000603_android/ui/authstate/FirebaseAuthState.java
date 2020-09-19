package com.example.etu000603_android.ui.authstate;

import android.content.Intent;
import android.os.Bundle;

import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.Nullable;

public class FirebaseAuthState extends ActivityWithLanguage {
    private FirebaseAuth authState;
    private void checkAuthstate(){
        FirebaseUser currentUser = authState.getCurrentUser();
        if(currentUser==null){
            redirectToLogin();
        }
    }
    public void redirectToLogin(){

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        checkAuthstate();
    }
}
