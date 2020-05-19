package com.example.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    private Menu mOptionsMenu;
    JSONArray jsonArry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String BASE_URL = getString(R.string.url_popular);
        setList(BASE_URL);
    }

    private void setList(String BASE_URL){
        setContentView(R.layout.movie_list);
        RecyclerView rView = (RecyclerView) findViewById(R.id.moviesList_lv);

        String API_KEY = getString(R.string.api_key);

        URL searchURL = NetworkUtils.buildUrl(BASE_URL, API_KEY);
        Log.d("searchURL", searchURL.toString());
        new MoviesList().execute(searchURL);

        /*
        rView.OnItemTouchListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });
         */
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);

        JSONObject obj = new JSONObject();
        try {
            obj = jsonArry.getJSONObject(position);
        }catch(JSONException e){
            e.printStackTrace();
        }
        intent.putExtra(DetailActivity.MOVIE_OBJECT, obj.toString());
        Log.d("EXTRA_POSITOIN before", String.valueOf(position));
        startActivity(intent);
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

            RecyclerView rView = (RecyclerView) findViewById(R.id.moviesList_lv);

            ArrayList<Movie> movies = new ArrayList<Movie>();

            Log.d("onPostExecute", "initialized");
            Log.d("onPostExecute-results", results);

            List<String> titleList = new ArrayList<String>();
            List<String> voteCountList = new ArrayList<String>();
            List<String> aveVoteList = new ArrayList<String>();
            List<String> imageList = new ArrayList<String>();

            if (results != null && !results.equals("")){

                try {
                    JSONObject JSONresults = new JSONObject(results);
                    jsonArry = JSONresults.getJSONArray("results");

                    for (int i=0; i<jsonArry.length(); i++){
                        JSONObject obj = jsonArry.getJSONObject(i);
                        Log.d("title:", obj.optString("title"));
                        Log.d("name:", obj.optString("name"));
                        Log.d("original_title:", obj.optString("original_title"));
                        Log.d("backdrop_path:", obj.optString("backdrop_path"));
                        Log.d("vote_count:", obj.optString("vote_count"));
                        Log.d("vote_average:", obj.optString("vote_average"));

                        String combinedTitle = obj.optString("title") + obj.optString("name");


                        Map<String, String> item = new HashMap<String, String>();
                        item.put("name", combinedTitle);
                        item.put("image", obj.optString("backdrop_path"));
                        Log.d("movie:", "checkpoint2");

                        Movie movie = new Movie (
                                obj.optString("id"),
                                obj.optString("vote_count"),
                                obj.optString("vote_average"),
                                combinedTitle,
                                obj.optString("release_date"),
                                obj.optString("backdrop_path"),
                                obj.optString("overview")
                                );

                        movies.add(movie);
                        Log.d("movie:", "added");

                    }

                    Adapter adapter = new Adapter(movies);
                    rView.setAdapter(adapter);
                    rView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mOptionsMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String BASE_URL;

        switch (item.getItemId()) {
            case R.id.action_popular:
                Toast.makeText(this, "Sorting by Popularity", Toast.LENGTH_SHORT).show();
                setTitle(getString(R.string.sort_by_popularity));
                item.setChecked(true);
                mOptionsMenu.findItem(R.id.action_voted).setChecked(false);
                BASE_URL = getString(R.string.url_popular);
                setList(BASE_URL);
                return true;
            case R.id.action_voted:
                Toast.makeText(this, "Sorting by Votes", Toast.LENGTH_SHORT).show();
                setTitle(getString(R.string.sort_by_votes));
                item.setChecked(true);
                mOptionsMenu.findItem(R.id.action_popular).setChecked(false);
                BASE_URL = getString(R.string.url_top_rated);
                setList(BASE_URL);
                return true;
            default:
                return false;
        }
    }
}
