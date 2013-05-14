package com.dummy.controllers;

import com.dummy.models.Article;
import com.dummy.models.Author;
import com.dummy.services.MailService;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;

import java.util.List;

@Path("/article")
public class ArticleController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    @GET
    @Path("/")
    public void show() {
    }

    @GET
    @Path("/display")
    public List<Author> display() {
        List<Author> authors = Author.find_all().includes(Article.class);
        return authors;
    }

    @POST
    @Path("/create")
    public Article create(Article article) {
        Author author = Author.find_first("name='" + article.getAuthor().getName() + "'");
        if (author == null) {
            author = article.getAuthor().save();
        }

        article.setAuthorId(author.getId()).save();

        mailService.sendMail("a@b.com", article.toString());

        return article;
    }

}
