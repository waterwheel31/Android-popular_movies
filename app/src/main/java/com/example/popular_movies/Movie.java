package com.example.popular_movies;

public class Movie {

    private String id;
    private String vote_count;
    private String vote_average;
    private String title;
    private String release_date;
    private String backdrop_path;
    private String overview;

    public Movie(String id, String vote_count, String vote_average,
                 String title, String release_date,
                 String backdrop_path, String overview) {
        this.id = id;
        this.vote_count = vote_count;
        this.vote_average = vote_average;
        this.title = title;
        this.release_date = release_date;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
    }

    public String getId(){return id;}
    public String getVote_count(){return vote_count;}
    public String getVote_average(){return vote_average;}
    public String getTitle(){return title;}
    public String getRelease_date(){return release_date;}
    public String getBackdrop_path(){return backdrop_path;}
    public String getOverview(){return overview;}
}
