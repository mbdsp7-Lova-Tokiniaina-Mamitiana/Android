package com.example.etu000603_android.data.repository;


import com.example.etu000603_android.data.model.Equipe;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.PariStatut;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


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

        }
        activity.getPariDisponibles(list);
    }
    public void getPariFini(PariPersonelActivity activity){
        List<PariPersonnel> list=new ArrayList<>();
        for(int i=0;i<50;i++){
            Match match =new Match();
            match.setDomicile(new Equipe("domicile"+i,"","https://logodownload.org/wp-content/uploads/2017/02/chelsea-fc-logo-1.png"));
            match.setExterieur(new Equipe("domicile"+i,"","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRARu6vDD55MzDzZh6CaANLfVnNBt4x1Gu4Lwh3ZvIzkb8jGvFZCWtMXTS8nD2drq3nnfM&usqp=CAU"));
            PariPersonnel pari =new PariPersonnel();
            pari.setMatch(match);
            pari.setDescription("Chelsea va gagner 2 - 0 ");
            pari.setCote(2.5);
            pari.setStatut(PariStatut.TERMINEE);
            pari.setMise(100.0);
            if(i%2==0){
                pari.setMise(-i*100);
            }
            pari.setDate(new Timestamp(System.currentTimeMillis()));


            list.add(pari);

        }

        activity.getPariDisponibles(list,PariStatut.TERMINEE);
    }

    public void getPariEnCours(PariPersonelActivity activity){
        List<PariPersonnel> list=new ArrayList<>();
        for(int i=0;i<50;i++){
            Match match =new Match();
            match.setDomicile(new Equipe("domicile"+i,"","https://logodownload.org/wp-content/uploads/2017/02/chelsea-fc-logo-1.png"));
            match.setExterieur(new Equipe("domicile"+i,"","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRARu6vDD55MzDzZh6CaANLfVnNBt4x1Gu4Lwh3ZvIzkb8jGvFZCWtMXTS8nD2drq3nnfM&usqp=CAU"));
            PariPersonnel pari =new PariPersonnel();
            pari.setMatch(match);
            pari.setDescription("Chelsea va gagner 2 - 0 ");
            pari.setCote(2.5);
            pari.setStatut(PariStatut.ENCOURS);
            pari.setMise(i*100);

            pari.setDate(new Timestamp(System.currentTimeMillis()));


            list.add(pari);

        }
        activity.getPariDisponibles(list,PariStatut.ENCOURS);
    }
    public void getPariPersonnels(PariPersonelActivity activity, int type){
        if(type == 0){
            getPariEnCours(activity);
        }else{
            getPariFini(activity);
        }
    }





}
