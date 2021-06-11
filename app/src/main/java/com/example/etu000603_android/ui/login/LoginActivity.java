package com.example.etu000603_android.ui.login;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import com.example.etu000603_android.R;
import com.example.etu000603_android.service.AuthService;
import com.example.etu000603_android.ui.company.SearchCompany;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.etu000603_android.R;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.json.JSONException;

public class LoginActivity extends ActivityWithLanguage {
    private FirebaseAuth authState;
    private ProgressBar progressBar;
    private TextView errorMessage;
    private EditText textPassword;
    private EditText textUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authState=FirebaseAuth.getInstance();
        checkUser();
        setContentView(R.layout.activity_login);


        textUsername=findViewById(R.id.username);
        textPassword=findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        errorMessage=findViewById(R.id.error_message);
        setOnclickListener();
    }
    private void checkUser(){
        FirebaseUser user=authState.getCurrentUser();
        if(user!=null){
            startActivity(new Intent(getBaseContext(), PariActvity.class));
            finish();
        }
    }
    public void ShowHidePass(View view){

        if(view.getId()==R.id.image_pass){

            if(textPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){

                ((ImageView)(view)).setImageResource(R.drawable.ic_eye_closed);

                //Show Password
                textPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                ((ImageView)(view)).setImageResource(R.drawable.ic_eye_open);

                //Hide Password
                textPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        }
    }
    private void setOnclickListener(){
        final Button loginButton=findViewById(R.id.login);
        textPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        //Hide Password
        textPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=textUsername.getText().toString();
                String password=textPassword.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
               authState.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           startActivity(new Intent(getBaseContext(), PariActvity.class));
                           finish();
                       }else {
                           System.out.println("failed");
                           errorMessage.setVisibility(View.VISIBLE);
                       }
                       progressBar.setVisibility(View.INVISIBLE);
                   }
               });
            }
        });
    }

    public void onLoginSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(getBaseContext(), SearchCompany.class));
        finish();
    }

    public void onLoginError() {
        progressBar.setVisibility(View.INVISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();

    }
};
