package com.example.moviecatalogservice.model;

import java.io.Serializable;

public class UserRating implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String movieId;
    private String username;
    private String rating;

    public UserRating(String movieId, String name) {
        this.movieId = movieId;
        this.username = name;
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

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}