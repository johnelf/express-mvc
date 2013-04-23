package com.dummy;

import com.expressmvc.annotation.ViewIngredient;

@ViewIngredient("_article")
public class Article {
    private String url;
    private String tittle;
    private String tags;

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
