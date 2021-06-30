package com.example.etu000603_android.data.repository;

import com.example.etu000603_android.data.LoginDataSource;
import com.example.etu000603_android.data.constants.Constant;
import com.example.etu000603_android.data.model.LoggedInUser;
import com.example.etu000603_android.data.model.PariPersonnel;
import com.example.etu000603_android.data.model.Result;
import com.example.etu000603_android.data.model.User;
import com.example.etu000603_android.ui.authstate.LocalAuthState;
import com.example.etu000603_android.ui.pari.PariPersonelActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class ProfilRepository {

    private static volatile ProfilRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private ProfilRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }
    public ProfilRepository() {

    }
    public static ProfilRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new ProfilRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        Result<LoggedInUser> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        return result;
    }

    public  void getAuthInfo(String token, final LocalAuthState authState){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(Constant.API_NODE+"users/auth")
                .addHeader("Accept","application/json")
                .addHeader("x-access-token",token)

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

                    if(response.code()!=200){
                        authState.logout();
                        return;
                    }
                    JSONObject json = new JSONObject(myResponse);
                    System.out.println("Json out:"+myResponse);
                    if(json!=null){
                        User user =new User();
                        user.setId(json.getString("_id"));
                        user.setUsername(json.getString("login"));
                        user.setEmail(json.getString("email"));
                        authState.checkProfil(user);
                    }else{
                        authState.logout();
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });


    }

    public  void getProfil(final User user, final LocalAuthState authState){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(Constant.TIMOUT, TimeUnit.SECONDS)
                .build();

        Request request = new Request.Builder()
                .url(Constant.API_GRAILS+"profil?id="+user.getId())
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
                    System.out.println("Json profil");
                    JSONObject json = new JSONObject(myResponse);

                    if(json!=null){
                        user.setPrenom(json.getString("prenom"));
                        user.setNom(json.getString("nom"));
                        user.setSolde(Double.parseDouble(json.getString("solde")));
                        authState.setSession(user);
                    }


                } catch (Exception e) {
                    e.printStackTrace();


                }


            }
        });


    }
}