package com.example.etu000603_android.data.repository;


import com.example.etu000603_android.data.constants.Constant;
import com.example.etu000603_android.data.model.Equipe;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.PariStatut;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class PariRepository {



    public void getPariDisponibles(PariActvity activity){

        List<Match> list=new ArrayList<>();
        for(int i=0;i<50;i++){
            Match match =new Match();
            match.setDomicile(new Equipe("domicile"+i,"","https://logodownload.org/wp-content/uploads/2017/02/chelsea-fc-logo-1.png"));
            match.setExterieur(new Equipe("domicile"+i,"","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRARu6vDD55MzDzZh6CaANLfVnNBt4x1Gu4Lwh3ZvIzkb8jGvFZCWtMXTS8nD2drq3nnfM&usqp=CAU"));
            match.setDate(new Timestamp(System.currentTimeMillis()));
            Pari pari =new Pari();
            pari.setMatch(match);
            pari.setDescription("Chelsea va gagner 2 - 0 ");
            pari.setCote(2.5);
            match.getListPari().add(pari);

            list.add(match);

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
    public  void getPariPersonnel(final String iduser, final PariStatut statut, int page , int max, final PariPersonelActivity activity){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();
        final int offset= (page -1)*max;
        int stat= 0;
        if(PariStatut.TERMINEE == statut){
            stat=1;
        }
        Request request = new Request.Builder()
                .url(Constant.API_GRAILS+"historiquepersonnels?id="+iduser+"&&statut="+stat+"&&offset="+offset+"&&max="+max)
                .addHeader("Accept","application/json")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response error");
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    if(response.code()!=200 && response.code()!=401){

                    }
                    System.out.println("Json pari perso");
                    JSONArray jsonArray = new JSONArray(myResponse);
                    int n=0;
                    if(jsonArray!=null){
                        n = jsonArray.length();
                    }
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    // you can change format of date

                    final List<PariPersonnel> liste= new ArrayList<>();
                    for(int i= 0;i<n;i++){
                        JSONObject object = jsonArray.getJSONObject(i);
                        PariPersonnel p =new PariPersonnel();
                        p.setUser(object.getString("idUser"));
                        p.setId(object.getString("id"));
                        p.setMise(object.getDouble("montant"));
                        p.setCote(object.getDouble("cote"));
                        p.setDescription(object.getString("textePari"));
                        Date datep = formatter.parse(object.getString("dateHisto"));
                        p.setDate(new Timestamp(datep.getTime()));
                        Match match=new Match();
                        p.setMatch(match);
                        match.setLocalisationY(object.getDouble("localisationy"));
                        match.setLocalistionX(object.getDouble("localisationx"));
                        Date datematch = formatter.parse(object.getString("dateMatch"));
                        match.setDate(new Timestamp(datematch.getTime()));
                        Equipe e1=new Equipe();
                        e1.setName(object.getString("nomEquipe1"));
                        e1.setName(object.getString("avatarEquipe1"));
                        match.setDomicile(e1);
                        Equipe e2=new Equipe();
                        e2.setName(object.getString("nomEquipe2"));
                        e2.setName(object.getString("avatarEquipe2"));
                        match.setExterieur(e2);
                        match.setId(object.getString("idMatch"));

                        p.setStatut(statut);
                        liste.add(p);

                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            System.out.println("getResponse Stats");
                            activity.getPariDisponibles(liste,statut);
                            // getEvolution(id,activity,listeFinal);
                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });


    }





}
