package com.dummy.controllers;

import com.dummy.services.MailService;
import com.dummy.models.Article;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.Envelope;

@Path("/article")
public class ArticleController extends AppController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    @POST
    public Envelope create(Article article) {

        //fake business logic
        article.setUrl("http://www.example.com/2013/article_1");

        //using injected service
        mailService.sendMail("a@b.com", article.toString());

        return Envelope.initWith(article);
    }
}
