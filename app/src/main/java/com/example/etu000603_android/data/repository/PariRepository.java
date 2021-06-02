package com.example.etu000603_android.data.repository;


import com.example.etu000603_android.data.model.Address;
import com.example.etu000603_android.data.model.Company;
import com.example.etu000603_android.data.model.Contact;
import com.example.etu000603_android.data.model.Equipe;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;


public class PariRepository {



    public void getPariDisponibles(PariActvity activity){

        List<Pari> list=new ArrayList<>();
        for(int i=0;i<50;i++){
            Match match =new Match();
            match.setDomicile(new Equipe("domicile"+i,"","https://logodownload.org/wp-content/uploads/2017/02/chelsea-fc-logo-1.png"));
            match.setExterieur(new Equipe("domicile"+i,"","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRARu6vDD55MzDzZh6CaANLfVnNBt4x1Gu4Lwh3ZvIzkb8jGvFZCWtMXTS8nD2drq3nnfM&usqp=CAU"));
            Pari pari =new Pari();
            pari.setMatch(match);
            pari.setDescription("Chelsea va gagner 2 - 0 ");
            pari.setCote(2.5);

            list.add(pari);
            activity.getPariDisponibles(list);
        }
    }





}
