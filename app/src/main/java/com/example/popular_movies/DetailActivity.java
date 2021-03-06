package com.example.popular_movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class DetailActivity extends AppCompatActivity {

    public static String MOVIE_OBJECT;

    static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";

    private TextView voteCountTV;
    private TextView voteAverageTV;
    private TextView releaseDateTV;
    private TextView overviewTV;
    private ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        voteCountTV = findViewById(R.id.vote_count_tv);
        voteAverageTV = findViewById(R.id.vote_average_tv);
        releaseDateTV = findViewById(R.id.release_date_tv);
        overviewTV = findViewById(R.id.overview_tv);
        imageIV = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        //String obj = intent.getStringExtra(MOVIE_OBJECT);
        Movie movie = (Movie) intent.getSerializableExtra(MOVIE_OBJECT);

        populateUI(movie);

    }

    private void populateUI(Movie movie){
        setTitle(movie.getTitle());
        Picasso.with(this).load(BASE_IMAGE_URL+movie.getBackdrop_path()).into(imageIV);
        voteCountTV.setText(movie.getVote_count());
        voteAverageTV.setText(movie.getVote_average());
        releaseDateTV.setText(movie.getRelease_date());
        overviewTV.setText(movie.getOverview());

    }
}
