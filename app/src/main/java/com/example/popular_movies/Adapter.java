package com.example.popular_movies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Adapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private String[] titleArr;
    private String[] voteCountArr;
    private String[] aveVoteArr;
    private String[] imageArr;

    static String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/original";

    static class ViewHolder{
        ImageView image;
        TextView title;
        TextView voteCount;
        TextView averageVote;
    }

    public Adapter(Context context, int itemLayoutId,
                   String[] titles, String[] images,
                   String[] vote_average, String[] vote_count){

        inflater = LayoutInflater.from(context);
        layoutID = itemLayoutId;
        titleArr = titles;
        voteCountArr = vote_count;
        aveVoteArr = vote_average;
        imageArr = images;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();
            //holder.img = convertView.findViewById(R.id.listImage);
            holder.title = convertView.findViewById(R.id.titleTV);
            holder.voteCount = convertView.findViewById(R.id.voteCountTV);
            holder.averageVote = convertView.findViewById(R.id.averageVoteTV);
            holder.image = convertView.findViewById(R.id.listImageIV);
            convertView.setTag((holder));
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //holder.img.setImageBitmap(imageList[position]);
        holder.title.setText(titleArr[position]);
        holder.voteCount.setText("No of Votes: " + voteCountArr[position]);
        holder.averageVote.setText("Ave. of Votes: " + aveVoteArr[position] + "/ 10.0");
        Picasso.with(this).load(BASE_IMAGE_URL+imageArr[position]).into(holder.image);

        return convertView;
    }

    @Override
    public int getCount() {
        return titleArr.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}
