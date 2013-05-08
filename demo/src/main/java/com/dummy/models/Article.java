package com.dummy.models;

import com.expressmvc.annotation.ViewIngredient;
import com.thoughtworks.Model;

@ViewIngredient("_article")
public class Article extends Model {
    private String title;
    private int authorId;
    private String url;
    private Author author;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
