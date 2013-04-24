package com.dummy.models;

import com.expressmvc.annotation.ViewIngredient;

@ViewIngredient("_article")
public class Article {
    private String title;
    private Author author;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
