package com.example.moviecatalogservice.model;

public class UserRating {

    private String id;
    private String movieId;
    private String name;
    private String rating;

    public UserRating(String movieId, String name) {
        this.movieId = movieId;
        this.name = name;
    }

    public UserRating() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}