package com.example.BetSoccer_android.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.BetSoccer_android.R;
import com.example.BetSoccer_android.service.AuthService;
import com.example.BetSoccer_android.ui.language.ActivityWithLanguage;
import com.example.BetSoccer_android.ui.pari.PariActvity;

public class InscriptionActivity extends ActivityWithLanguage {

    private EditText textName,textUsername,textPassword,textFirstName,textEmail;
    TextView errorMessage;
    private Button  inscription,loginButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);
        textName = findViewById(R.id.name);
        textUsername = findViewById(R.id.username);
        textPassword = findViewById(R.id.password);
        textFirstName= findViewById(R.id.firstname);
        textEmail= findViewById(R.id.email);
        inscription = findViewById(R.id.inscription);
        progressBar = findViewById(R.id.progressBar);
        errorMessage = findViewById(R.id.error_message);
        loginButton = findViewById(R.id.login);
        setOnclickListener();
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
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

        textPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        //Hide Password
        textPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                finish();
            }
        });
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=textEmail.getText().toString();
                String password=textPassword.getText().toString();
                String username =textUsername.getText().toString();
                String firstname =textFirstName.getText().toString();
                String name =textName.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                try {
                    inscription.setEnabled(false);
                    AuthService.inscription(username,email,name,firstname,
                            password,
                            InscriptionActivity.this
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void onInscriptionSuccess() {
        progressBar.setVisibility(View.INVISIBLE);
        startActivity(new Intent(getBaseContext(), PariActvity.class));
        inscription.setEnabled(true);
        finish();
    }

    public void onInscriptionError() {
        progressBar.setVisibility(View.INVISIBLE);
        inscription.setEnabled(true);
        //errorMessage.setVisibility(View.VISIBLE);
        showMessage("Informations invalides",true);
    }
}