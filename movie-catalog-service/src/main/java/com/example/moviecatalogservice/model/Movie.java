package com.example.moviecatalogservice.model;

import java.io.Serializable;

public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String imgUrl;
    private String title;
    private String productionCompany;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }

    public Movie() {}

    public Movie(String id, String imgUrl, String title, String productionCompany) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.title = title;
        this.productionCompany = productionCompany;
    }   
}