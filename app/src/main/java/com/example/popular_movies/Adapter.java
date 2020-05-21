package com.example.popular_movies;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class Adapter extends
        RecyclerView.Adapter<Adapter.ViewHolder> {

    static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";
    private List<Movie> mMovies;
    Context context;

    public Adapter(List<Movie> movies){
        mMovies = movies;
    }

    @Override
    public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        context = parent.getContext();
        LayoutInflater infalter = LayoutInflater.from(context);

        View contactView = infalter.inflate(R.layout.movie_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Adapter.ViewHolder viewHolder, final int position){

        final Movie movie = mMovies.get(position);

        Log.d("movie-title:", movie.getTitle());
        Log.d("movie-url:", BASE_IMAGE_URL+movie.getBackdrop_path());

        ImageView imageIV = viewHolder.image;
        Picasso.with(context).load(BASE_IMAGE_URL+movie.getBackdrop_path()).into(imageIV);

        TextView titleTV = viewHolder.title;
        titleTV.setText(movie.getTitle());

        TextView voteCountTV = viewHolder.voteCount;
        voteCountTV.setText(movie.getVote_count());

        TextView averageVoteTV = viewHolder.averageVote;
        averageVoteTV.setText(movie.getVote_average());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);

                intent.putExtra(DetailActivity.MOVIE_OBJECT, movie);
                Log.d("EXTRA_POSITOIN before", String.valueOf(position));
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView voteCount;
        public TextView averageVote;

        public ViewHolder(View itemView){
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.listImageIV);
            title = (TextView) itemView.findViewById(R.id.titleTV);
            voteCount = (TextView) itemView.findViewById(R.id.voteCountTV);
            averageVote = (TextView) itemView.findViewById(R.id.averageVoteTV);
        }
    }

}
