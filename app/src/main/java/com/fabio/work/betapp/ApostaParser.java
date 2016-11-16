package com.fabio.work.betapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Fabio on 15/11/2016.
 */
public class ApostaParser {

    public static final String URL_GET_APOSTA = "http://bet24h.com.br/Bet24h/webservice/endpoint.php/aposta/";

    public static List<Jogo> getApostaById(int apostaId) throws java.io.IOException {

        String json = getJsonStr(apostaId);

        List<Jogo> yourClassList = null;

        if(json != null){
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<Jogo>>(){}.getType();

            yourClassList = new Gson().fromJson(json, listType);
        }

        return yourClassList;
    }

    private static String getJsonStr(int apostaId) throws java.io.IOException {
        URL url = new URL(URL_GET_APOSTA + apostaId);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");

        int responseCode = urlConnection.getResponseCode();

        String jsonString = "";

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while ((line = br.readLine()) != null) {
                jsonString += line;
            }
        } else {
            jsonString = "";
        }

        return jsonString;

    }

}
