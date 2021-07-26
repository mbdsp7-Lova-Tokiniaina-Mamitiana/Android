package com.example.etu000603_android.data.repository;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.Database;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Document;
import com.couchbase.lite.Expression;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Ordering;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.SelectResult;
import com.example.etu000603_android.data.model.Equipe;
import com.example.etu000603_android.data.model.Match;
import com.example.etu000603_android.data.model.Pari;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public  class CouchbaseService {

    public  static Database database =null;
    public static List<Match> listMatchs = new ArrayList<>();
    public static int count = 0;

    public static Database getDatabase(Context context){


        try {

            if(database==null){
                CouchbaseLite.init(context);
                DatabaseConfiguration config = new DatabaseConfiguration();
                config.setDirectory(String.format("%s/%s", context.getFilesDir(), "mbds"));
                database = new Database("dbpari", config);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  database;
    }
    public static void insertMatch(Match match,Context context){
        try {
            Document doc = retrieveDocument(match.getId(),context);
            System.out.println("Insertion doc:"+doc);
            if(doc==null){
                checkcount(context);

                ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
                String json = ow.writeValueAsString(match);
                Map<String, Object> docContent = new HashMap<String, Object>();


                docContent.put("match", json);
                docContent.put("date",match.getDate().getTime());

                MutableDocument mutableDocument = new MutableDocument(match.getId(), docContent);
                getDatabase(context).save(mutableDocument);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void  checkcount(Context context){
        count = listMatchs.size();
        if(count>100){
            System.out.println("Suppression doc");
            deleteAllDocuments(context);
        }
    }
    public static void getListMatch(Context context){
            List<Match> list =new ArrayList<>();
            try{
                Query query = QueryBuilder.select(SelectResult.all())
                        .from(DataSource.database(getDatabase(context))).orderBy(Ordering.property("date").ascending());

                ResultSet result = query.execute();
                List<Result> lr =result.allResults();

                for(Result r:lr){
                        Match m =new Match();

                        Map map =r.toMap();
                        Map map1 = (Map) map.get("dbpari");
                        String json = (String)map1.get("match");
                        JSONObject jsonObject = new JSONObject(json);
                        if(jsonObject!=null){
                            m.setId(jsonObject.getString("id"));
                            m.setDate(new Timestamp(jsonObject.getLong("date")));
                            JSONObject domicile = jsonObject.getJSONObject("domicile");
                            if(domicile!=null){
                                Equipe equipe =new Equipe();
                                equipe.setId(domicile.getString("id"));
                                equipe.setName(domicile.getString("name"));
                                equipe.setUrl_image(domicile.getString("url_image"));
                                m.setDomicile(equipe);
                            }
                            JSONObject exterieur = jsonObject.getJSONObject("exterieur");
                            if(exterieur!=null){
                                Equipe equipe =new Equipe();
                                equipe.setId(exterieur.getString("id"));
                                equipe.setName(exterieur.getString("name"));
                                equipe.setUrl_image(exterieur.getString("url_image"));
                                m.setExterieur(equipe);
                            }
                            JSONArray arrayPari = jsonObject.getJSONArray("listPari");
                            int n2 = 0;
                            if(arrayPari!=null){
                                n2 = arrayPari.length();
                            }
                            for(int j=0;j<n2;j++){
                                JSONObject p = arrayPari.getJSONObject(j);
                                Pari pari =new Pari();
                                pari.setCote(p.getDouble("cote"));
                                pari.setDescription(p.getString("description"));
                                pari.setId(p.getString("id"));
                                m.getListPari().add(pari);

                            }
                        }
                        list.add(m);
                }

                listMatchs = list;

            }catch (Exception exc){
                exc.printStackTrace();
            }


    }
    public static boolean deleteAllDocuments(Context context) {
        try {
            getDatabase(context).close();
            getDatabase(context).delete();
            database = null;

            return true;
        } catch (Exception e) {
            e.printStackTrace();


        }

        return false;
    }
    public static Document retrieveDocument(String docId,Context context) {
        Document retrievedDocument = getDatabase(context).getDocument(docId);

        return retrievedDocument;
    }
}
