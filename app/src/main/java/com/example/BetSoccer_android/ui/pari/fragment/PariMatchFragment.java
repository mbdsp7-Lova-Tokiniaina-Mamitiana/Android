package com.example.BetSoccer_android.ui.pari.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.BetSoccer_android.R;
import com.example.BetSoccer_android.data.model.Pari;
import com.example.BetSoccer_android.ui.pari.PariFormActivity;

public class PariMatchFragment extends Fragment {
    private Pari pari =null;
    private PariFormActivity activity =null;

    public PariMatchFragment(Pari p, PariFormActivity activity){
        this.pari= p;
        this.activity =activity;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view= (ViewGroup) inflater.inflate(
                R.layout.pari_match, container, false);
        final TextView textPari = view.findViewById(R.id.description_pari);
        final TextView textCote =view.findViewById(R.id.cote);
        final EditText editText = view.findViewById(R.id.value);
        final Button btn_pari = view.findViewById(R.id.btn_pari);
        textPari.setText(pari.getDescription());
        textCote.setText(pari.getCote()+"");


        btn_pari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    double montant = 0;
                    try{
                     montant =Double.parseDouble(editText.getText().toString());
                     if(montant<0){
                         throw  new Exception("Montant ne doit pas etre negative");
                     }
                    }catch (Exception exc){
                        throw  new Exception("Montant invalide");
                    }

                    activity.parier(pari,montant);
                }catch (Exception exc){
                    exc.printStackTrace();
                    activity.showMessage(exc.getMessage(),true);
                }
            }
        });
        return view;
    }
}
