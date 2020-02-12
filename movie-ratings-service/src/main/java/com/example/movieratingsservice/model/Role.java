package com.example.movieratingsservice.model;

public class Role {

    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }
    public Role() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
        
}