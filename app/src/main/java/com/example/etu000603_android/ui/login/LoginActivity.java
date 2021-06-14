package com.example.etu000603_android.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import com.example.etu000603_android.R;
import com.example.etu000603_android.service.AuthService;
import com.example.etu000603_android.ui.language.ActivityWithLanguage;
import com.example.etu000603_android.ui.pari.PariActvity;
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
                try {
                    AuthService.login(email,
                            password,
                            LoginActivity.this,
                            LoginActivity.this.getClass().getMethod("onLoginSuccess"),
                            LoginActivity.this.getClass().getMethod("onLoginError")
                    );
                } catch (NoSuchMethodException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onLoginSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(getBaseContext(), PariActvity.class));
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
