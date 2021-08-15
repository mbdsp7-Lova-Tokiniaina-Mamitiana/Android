package com.example.BetSoccer_android.ui.navigation;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.BetSoccer_android.R;
import com.example.BetSoccer_android.data.model.Match;
import com.example.BetSoccer_android.data.repository.PariRepository;
import com.example.BetSoccer_android.ui.language.ActivityWithLanguage;
import com.example.BetSoccer_android.ui.pari.PariFormActivity;
import com.example.BetSoccer_android.utils.Session;
import com.google.zxing.Result;

public class QrActivity extends ActivityWithLanguage {
    private CodeScanner mCodeScanner;
    private final PariRepository repository =new PariRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        repository.getMatchById(result.getText(),QrActivity.this);


                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
    public void redirectToMatch(Match m){
        Session.selected_match=m;
        Intent intent=new Intent(getBaseContext(), PariFormActivity.class);

        startActivity(intent);
    }
}