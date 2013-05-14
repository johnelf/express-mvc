package com.dummy.models;

import com.thoughtworks.Model;

public class Author extends Model{
    private String name;
    private int age;
    private String email;
    private Article[] articles;

    public Article[] getArticles() {
        return articles;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }
}
