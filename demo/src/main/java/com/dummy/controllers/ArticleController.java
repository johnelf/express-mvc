package com.dummy.controllers;

import com.dummy.models.Article;
import com.dummy.models.Author;
import com.dummy.services.MailService;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.thoughtworks.annotation.Transactional;

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
        return Author.find_all().includes(Article.class);
    }

    @POST
    @Path("/create")
    @Transactional
    public Article create(Article article) {
        Author author = Author.find_or_create("name", article.getAuthor().getName());
        article.setAuthorId(author.getId()).save();

        mailService.sendMail("a@b.com", article.toString());

        return article;
    }

}
