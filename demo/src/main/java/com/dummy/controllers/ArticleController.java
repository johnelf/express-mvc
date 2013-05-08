package com.dummy.controllers;

import com.dummy.services.MailService;
import com.dummy.models.Article;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.Envelope;

import java.sql.SQLException;

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
    public Envelope display() {
        //TODO add PathVariable annotation support, do it in DataBinder \\{([^/]+?)\\}
        return Envelope.initWith();
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
