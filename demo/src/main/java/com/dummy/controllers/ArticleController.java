package com.dummy.controllers;

import com.dummy.services.MailService;
import com.dummy.models.Article;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.Envelope;
import com.thoughtworks.Model;

import java.sql.SQLException;
import java.util.List;

@Path("/article")
public class ArticleController extends AppController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    @GET
    @Path("/")
    public Envelope show() {
        return Envelope.initWith();
    }

    @GET
    @Path("/display")
    public Envelope display() throws SQLException {
        //TODO add PathVariable annotation support, do it in DataBinder \\{([^/]+?)\\}
        List<Article> articles = Article.find_all();

        return Envelope.initWith(articles);
    }

    @POST
    @Path("/create")
    public Envelope create(Article article) throws SQLException {

        //fake business logic
        article.setUrl("http://www.example.com/2013/" + article.getAuthorId() + "/" + article.getTitle());
        article.save();
        article.getAuthor().save();

        //using injected service
        mailService.sendMail("a@b.com", article.toString());

        return Envelope.initWith(article);
    }

    @POST
    @Path("/edit")
    public Envelope edit(Article article) {

        return Envelope.initWith(article);
    }
}
