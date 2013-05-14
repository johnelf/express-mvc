package com.dummy.models;

import com.thoughtworks.Model;

public class Article extends Model {
    private String title;
    private int authorId;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return authorId;
    }
    public Article setAuthorId(int authorId) {
        this.authorId = authorId;
        return this;
    }

    public String getTitle() {
        return title;
    }
}
