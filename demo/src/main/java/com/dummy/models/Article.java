package com.dummy.models;

import com.thoughtworks.Model;
import com.thoughtworks.annotation.Transactional;

public class Article extends Model {
    private String title;
    private int authorId;
    private String url;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    @Transactional
    public <T extends Model> T save() {
        return super.save();
    }
}
