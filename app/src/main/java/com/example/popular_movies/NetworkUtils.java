package com.example.popular_movies;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static String BASE_URL = "https://api.themoviedb.org/3/trending/all/";
    final static String PARAM_QUERY = "api_key";
    final static String API_KEY = "<YOURKEY>";


    public static String getResponseFromHttpUrl(URL url) throws IOException{
        Log.d("getResponseFromHttpUrl", "initialized");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static URL buildUrl(String searchQuery){
        Uri builtUri = Uri.parse(BASE_URL+searchQuery).buildUpon()
                .appendQueryParameter(PARAM_QUERY, API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        Log.d("url:", builtUri.toString());
        return url;
    }

}
