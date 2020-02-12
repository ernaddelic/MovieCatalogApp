package com.example.moviecatalogservice.model;

public class RatedMovie extends Movie {

    private String rating;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public RatedMovie() {}

    public RatedMovie(String id, String imgUrl, String title, String productionCompany, String rating) {
        super(id, imgUrl, title, productionCompany);
        this.rating = rating;
    }
}
