package com.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView mMoviesListTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_list);

        String searchQuery = "day";
        URL searchURL = NetworkUtils.buildUrl(searchQuery);
        Log.d("searchURL", searchURL.toString());
        new MoviesList().execute(searchURL);

    }

    public class MoviesList extends AsyncTask<URL, Void, String> {

        public MoviesList(){
            Log.d("MoviesList", "initialized");
        }

        @Override
        protected String doInBackground(URL...urls){
            URL url = urls[0];
            String results = null;

            try{
                results = NetworkUtils.getResponseFromHttpUrl(url);
                Log.d("results", results);
            } catch (IOException e){
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String results){
            Log.d("onPostExecute", "initialized");
            Log.d("onPostExecute-results", results);

            List<String> titleList = new ArrayList<String>();
            List<String> voteCountList = new ArrayList<String>();
            List<String> aveVoteList = new ArrayList<String>();
            List<String> imageList = new ArrayList<String>();

            if (results != null && !results.equals("")){

                try {
                    JSONObject JSONresults = new JSONObject(results);
                    JSONArray jsonArry = JSONresults.getJSONArray("results");

                    //ArrayList names = new ArrayList<>();
                    List<Map<String, String>> data = new ArrayList<Map<String, String>>();

                    for (int i=0; i<jsonArry.length(); i++){
                        JSONObject obj = jsonArry.getJSONObject(i);
                        Log.d("title:", obj.optString("title"));
                        Log.d("original_title:", obj.optString("original_title"));
                        Log.d("backdrop_path:", obj.optString("backdrop_path"));
                        Log.d("vote_count:", obj.optString("vote_count"));
                        Log.d("vote_average:", obj.optString("vote_average"));

                        if (obj.optString("title") != "") {
                            Map<String, String> item = new HashMap<String, String>();
                            item.put("name", obj.optString("title"));
                            item.put("image", obj.optString("backdrop_path"));
                            data.add(item);

                            titleList.add(obj.optString("title"));
                            voteCountList.add(obj.optString("vote_count"));
                            aveVoteList.add(obj.optString("vote_average"));
                            imageList.add(obj.optString("backdrop_path"));

                        }
                    }

                    //ArrayAdapter adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, names);
                    /*
                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),data, android.R.layout.simple_list_item_2,
                            new String[]{"name", "image"},
                            new int[]{android.R.id.text1, android.R.id.text2});
                     */
                    String[] titleArr = new String[titleList.size()];
                    String[] aveVoteArr = new String[titleList.size()];
                    String[] voteCountArr = new String[titleList.size()];
                    String[] imageArr = new String[titleList.size()];

                    titleArr = titleList.toArray(titleArr);
                    aveVoteArr = aveVoteList.toArray(aveVoteArr);
                    voteCountArr = voteCountList.toArray(voteCountArr);
                    imageArr = imageList.toArray(imageArr);

                    BaseAdapter adapter = new Adapter(getApplicationContext(),
                            R.layout.movie_item,
                            titleArr, imageArr, aveVoteArr, voteCountArr);
                    ListView listView = findViewById(R.id.moviesList_lv);
                    listView.setAdapter(adapter);

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

}
