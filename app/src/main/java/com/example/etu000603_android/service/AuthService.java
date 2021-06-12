package com.example.etu000603_android.service;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    final static String URL = "http://192.168.0.108:8010/api/users/";

    public static void login(String login,
                             String password,
                             final Context context,
                             final Method successCallback,
                             @Nullable final Method errorCallback) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences("AuthSharedPref", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("login", login);
        jsonBody.put("password", password);
        final String requestBody = jsonBody.toString();

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL + "login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            Map<String, Object> responseMap = new Gson().fromJson(
                                    response, new TypeToken<HashMap<String, Object>>() {}.getType()
                            );
                            editor.putString("ACCESSTOKEN", responseMap.get("token").toString());
                            // editor.putBoolean("IS_AUTH");
                            editor.apply();
                            successCallback.invoke(context);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (errorCallback != null) {
                            try {
                                errorCallback.invoke(context);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return requestBody == null ? null : requestBody.getBytes(StandardCharsets.UTF_8);
            }

            /*@Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                // can get more details such as response.headers
                if (response != null) responseString = String.valueOf(response.statusCode);
                assert response != null;
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }*/
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public static void getUser(final Context context,
                               final Method successCallback, final String token) throws JSONException {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL + "auth",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            successCallback.invoke(context, response);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String>  params = new HashMap<>();
                params.put("x-access-token", token);
                params.put("Accept", "*/*");

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
