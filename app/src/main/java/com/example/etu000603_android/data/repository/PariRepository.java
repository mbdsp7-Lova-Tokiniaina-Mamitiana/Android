package com.example.etu000603_android.data.repository;


import com.example.etu000603_android.data.constants.Constant;
import com.example.etu000603_android.data.model.Equipe;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.PariStatut;
import com.example.etu000603_android.ui.navigation.QrActivity;
import com.example.etu000603_android.ui.pari.PariActvity;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;
import com.example.etu000603_android.utils.Session;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PariRepository {


    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
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
    public void getMatchById(String id, final QrActivity activity){
        final  Match m =new Match();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(Constant.API_NODE+"match/"+id)
                .addHeader("Accept","application/json")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response error");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.showMessage("Impossible de se connecter au serveur.Essayer ulterieurement",true);

                    }
                });
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    if(response.code()!=200 && response.code()!=401){
                        activity.showMessage("QR Code invalide ou ce match n'existe plus",true);
                    }


                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // you can change format of date


                    JSONObject match =new JSONObject(myResponse);
                    if(match!=null){
                        try{
                            m.setDate(new Timestamp(formatter.parse(match.getString("date_match")).getTime()));
                        }catch (Exception exc){
                            m.setDate(new Timestamp(System.currentTimeMillis()));
                        }

                        m.setId(match.getString("_id"));
                        m.setTermine(match.getBoolean("etat"));
                        m.setLocalistionX(match.getDouble("longitude"));
                        m.setLocalisationY(match.getDouble("latitude"));
                        JSONObject equipe1=match.getJSONObject("equipe1");
                        JSONObject equipe2=match.getJSONObject("equipe2");
                        Equipe e1 =new Equipe();
                        e1.setId(equipe1.getString("_id"));
                        e1.setUrl_image(Constant.BACKENDURL+equipe1.getString("avatar"));
                        e1.setName(equipe1.getString("nom"));
                        Equipe e2 = new Equipe();
                        e2.setId(equipe2.getString("_id"));
                        e2.setUrl_image(Constant.BACKENDURL+equipe2.getString("avatar"));
                        e2.setName(equipe2.getString("nom"));
                        m.setDomicile(e1);
                        m.setExterieur(e2);

                        int n2 = 0;

                        JSONArray arrayPari = match.getJSONArray("pari");
                        if(arrayPari!=null){
                            n2 = arrayPari.length();
                        }
                        for(int j=0;j<n2;j++){
                            JSONObject p = arrayPari.getJSONObject(j);
                            Pari pari =new Pari();
                            pari.setCote(p.getDouble("cote"));
                            pari.setDescription(p.getString("description"));
                            pari.setId(p.getString("_id"));
                            m.getListPari().add(pari);

                        }
                    }


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                                activity.redirectToMatch(m);
                            //  activity.getPariDisponibles(liste,statut);
                            // getEvolution(id,activity,listeFinal);

                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });
    }
    public void getMatchById(String id, final PariPersonelActivity activity){
        final  Match m =new Match();
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(Constant.API_NODE+"match/"+id)
                .addHeader("Accept","application/json")

                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response error");
                call.cancel();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activity.showMessage("Impossible de se connecter au serveur.Essayer ulterieurement",true);
                        activity.redirectToAccueil();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    if(response.code()!=200 && response.code()!=401){
                        activity.showMessage("QR Code invalide",true);
                    }


                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    // you can change format of date


                    JSONObject match =new JSONObject(myResponse);
                    if(match!=null){
                        try{
                            m.setDate(new Timestamp(formatter.parse(match.getString("date_match")).getTime()+3*3600*1000));
                        }catch (Exception exc){
                            m.setDate(new Timestamp(System.currentTimeMillis()));
                        }

                        m.setId(match.getString("_id"));
                        m.setTermine(match.getBoolean("etat"));
                        m.setLocalistionX(match.getDouble("longitude"));
                        m.setLocalisationY(match.getDouble("latitude"));
                        JSONObject equipe1=match.getJSONObject("equipe1");
                        JSONObject equipe2=match.getJSONObject("equipe2");
                        Equipe e1 =new Equipe();
                        e1.setId(equipe1.getString("_id"));
                        e1.setUrl_image(Constant.BACKENDURL+equipe1.getString("avatar"));
                        e1.setName(equipe1.getString("nom"));
                        Equipe e2 = new Equipe();
                        e2.setId(equipe2.getString("_id"));
                        e2.setUrl_image(Constant.BACKENDURL+equipe2.getString("avatar"));
                        e2.setName(equipe2.getString("nom"));
                        m.setDomicile(e1);
                        m.setExterieur(e2);

                        int n2 = 0;

                        JSONArray arrayPari = match.getJSONArray("pari");
                        if(arrayPari!=null){
                            n2 = arrayPari.length();
                        }
                        for(int j=0;j<n2;j++){
                            JSONObject p = arrayPari.getJSONObject(j);
                            Pari pari =new Pari();
                            pari.setCote(p.getDouble("cote"));
                            pari.setDescription(p.getString("description"));
                            pari.setId(p.getString("_id"));
                            m.getListPari().add(pari);

                        }
                    }


                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            activity.redirectToMatch(m);
                            //  activity.getPariDisponibles(liste,statut);
                            // getEvolution(id,activity,listeFinal);

                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });
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

    public void getMatchs2(final PariActvity activity,int page ,int limit){
        final List<Match>[] list = new List[]{new ArrayList<>()};
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();
        CouchbaseService.getListMatch(activity.getApplicationContext());
        Map<String,Object> map =new HashMap<>();

        map.put("page",page);
        map.put("limit",limit);
        map.put("etat",false);


        String json = new JSONObject(map).toString();
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(Constant.API_NODE+"matchs/search")
                .addHeader("Accept","application/json")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response error");

                list[0] = CouchbaseService.listMatchs;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //  activity.getPariDisponibles(liste,statut);
                        // getEvolution(id,activity,listeFinal);


                    }
                });

                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    if(response.code()!=200 && response.code()!=401){


                    }
                    System.out.println("Json matchs2");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");




                    // you can change format of date


                    JSONObject object =new JSONObject(myResponse);
                    if(object!=null){

                        JSONArray array =object.getJSONArray("docs");
                        int n=0;
                        if(array!=null){
                            n = array.length();
                        }
                        for(int i=0;i<n;i++){
                            Match m=new Match();
                            JSONObject match = array.getJSONObject(i);
                            try{

                                m.setDate(new Timestamp(formatter.parse(match.getString("date_match")).getTime()+3*3600*1000));

                            }catch (Exception exc){
                                exc.printStackTrace();
                                m.setDate(new Timestamp(System.currentTimeMillis()));
                            }

                            m.setId(match.getString("_id"));
                            m.setTermine(match.getBoolean("etat"));
                            m.setLocalistionX(match.getDouble("longitude"));
                            m.setLocalisationY(match.getDouble("latitude"));
                            JSONObject equipe1=match.getJSONObject("equipe1");
                            JSONObject equipe2=match.getJSONObject("equipe2");
                            Equipe e1 =new Equipe();
                            e1.setId(equipe1.getString("_id"));
                            e1.setUrl_image(Constant.BACKENDURL+equipe1.getString("avatar"));
                            e1.setName(equipe1.getString("nom"));
                            Equipe e2 = new Equipe();
                            e2.setId(equipe2.getString("_id"));
                            e2.setUrl_image(Constant.BACKENDURL+equipe2.getString("avatar"));
                            e2.setName(equipe2.getString("nom"));
                            m.setDomicile(e1);
                            m.setExterieur(e2);

                            int n2 = 0;

                            JSONArray arrayPari = match.getJSONArray("pari");
                            if(arrayPari!=null){
                                n2 = arrayPari.length();
                            }
                            for(int j=0;j<n2;j++){
                                JSONObject p = arrayPari.getJSONObject(j);
                                Pari pari =new Pari();
                                pari.setCote(p.getDouble("cote"));
                                pari.setDescription(p.getString("description"));
                                pari.setId(p.getString("_id"));
                                m.getListPari().add(pari);

                            }
                            list[0].add(m);
                            CouchbaseService.insertMatch(m,activity.getApplicationContext());
//                            CouchbaseService.retrieveDocument(m.getId(),activity.getApplicationContext());
                        }

                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            //  activity.getPariDisponibles(liste,statut);
                            // getEvolution(id,activity,listeFinal);

                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });

    }
    public void getMatchs(final PariActvity activity,int page ,int limit,String search,boolean etat,boolean isToday,String dateDebut,String dateFin){
        final List<Match>[] list = new List[]{new ArrayList<>()};
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();
        CouchbaseService.getListMatch(activity.getApplicationContext());
        Map<String,Object> map =new HashMap<>();

        map.put("page",page);
        map.put("limit",limit);
        map.put("etat",etat);

        if(!search.isEmpty()){
            map.put("pari",search);
            map.put("equipe",search);
        }

        if(isToday){
            map.put("isToday",isToday);
        }else{

            try{
                SimpleDateFormat d1 =new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat d2 =new SimpleDateFormat("yyyy-MM-dd");
                Date date =d1.parse(dateDebut);
                map.put("date_debut",d2.format(date));

            }catch (Exception exc){

            }
            try{
                SimpleDateFormat d1 =new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat d2 =new SimpleDateFormat("yyyy-MM-dd");
                Date date =d1.parse(dateFin);
                map.put("date_fin",d2.format(date));
            }catch (Exception exc){

            }

        }
        String json = new JSONObject(map).toString();
        RequestBody body = RequestBody.create(JSON, json);

        Request request = new Request.Builder()
                .url(Constant.API_NODE+"matchs/search")
                .addHeader("Accept","application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("response error");

                list[0] = CouchbaseService.listMatchs;
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        //  activity.getPariDisponibles(liste,statut);
                        // getEvolution(id,activity,listeFinal);
                        Session.isOnline = false;
                        activity.getPariDisponibles(list[0]);

                    }
                });

                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    if(response.code()!=200 && response.code()!=401){
                         Session.isOnline = false;
                         list[0] = CouchbaseService.listMatchs;

                    }
                    System.out.println("Json matchs");

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                    // you can change format of date


                    JSONObject object =new JSONObject(myResponse);
                    if(object!=null){
                        activity.setNbpages(object.getInt("totalPages"));
                        activity.setTotals(object.getInt("totalDocs"));
                        JSONArray array =object.getJSONArray("docs");
                        int n=0;
                        if(array!=null){
                            n = array.length();
                        }
                        for(int i=0;i<n;i++){
                            Match m=new Match();
                            JSONObject match = array.getJSONObject(i);
                            try{
                                m.setDate(new Timestamp(formatter.parse(match.getString("date_match")).getTime()+3*3600*1000));
                            }catch (Exception exc){
                                m.setDate(new Timestamp(System.currentTimeMillis()));
                            }

                            m.setId(match.getString("_id"));
                            m.setTermine(match.getBoolean("etat"));
                            m.setLocalistionX(match.getDouble("longitude"));
                            m.setLocalisationY(match.getDouble("latitude"));
                            JSONObject equipe1=match.getJSONObject("equipe1");
                            JSONObject equipe2=match.getJSONObject("equipe2");
                            Equipe e1 =new Equipe();
                            e1.setId(equipe1.getString("_id"));
                            e1.setUrl_image(Constant.BACKENDURL+equipe1.getString("avatar"));
                            e1.setName(equipe1.getString("nom"));
                            Equipe e2 = new Equipe();
                            e2.setId(equipe2.getString("_id"));
                            e2.setUrl_image(Constant.BACKENDURL+equipe2.getString("avatar"));
                            e2.setName(equipe2.getString("nom"));
                            m.setDomicile(e1);
                            m.setExterieur(e2);

                            int n2 = 0;

                            JSONArray arrayPari = match.getJSONArray("pari");
                            if(arrayPari!=null){
                                n2 = arrayPari.length();
                            }
                            for(int j=0;j<n2;j++){
                                JSONObject p = arrayPari.getJSONObject(j);
                                Pari pari =new Pari();
                                pari.setCote(p.getDouble("cote"));
                                pari.setDescription(p.getString("description"));
                                pari.setId(p.getString("_id"));
                                m.getListPari().add(pari);

                            }
                            list[0].add(m);
                            //CouchbaseService.insertMatch(m,activity.getApplicationContext());
//                            CouchbaseService.retrieveDocument(m.getId(),activity.getApplicationContext());
                        }
                        Session.isOnline = true;
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                          //  activity.getPariDisponibles(liste,statut);
                            // getEvolution(id,activity,listeFinal);
                            activity.getPariDisponibles(list[0]);
                        }
                    });




                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });

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
        System.out.println(Constant.API_GRAILS+"historiquepersonnels?id="+iduser+"&&statut="+stat+"&&offset="+offset+"&&max="+max);
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
                        e1.setUrl_image(object.getString("avatarEquipe1"));
                        match.setDomicile(e1);
                        Equipe e2=new Equipe();
                        e2.setName(object.getString("nomEquipe2"));
                        e2.setUrl_image(object.getString("avatarEquipe2"));
                        match.setExterieur(e2);
                        match.setId(object.getString("idMatch"));
                        p.setGain(object.getInt("gain"));

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


    public  void countPariPersonnel(final String iduser, final PariStatut statut,  final PariPersonelActivity activity){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();

        int stat= 0;
        if(PariStatut.TERMINEE == statut){
            stat=1;
        }
        System.out.println("url count:"+Constant.API_GRAILS+"counthistoriquepersonnels?id="+iduser+"&&statut="+stat);
        Request request = new Request.Builder()
                .url(Constant.API_GRAILS+"counthistoriquepersonnels?id="+iduser+"&&statut="+stat)
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
                    System.out.println("Json count pari perso");
                    final JSONObject object =new JSONObject(myResponse);
                   final int count =object.getInt("count");
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            System.out.println("getResponse Stats count");

                            activity.setTotals(count);

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
