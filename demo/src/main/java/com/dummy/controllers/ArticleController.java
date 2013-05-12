package com.dummy.controllers;

import com.dummy.services.MailService;
import com.dummy.models.Article;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.ModelAndViewContainer;

import java.sql.SQLException;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Path("/article")
public class ArticleController extends AppController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    @GET
    @Path("/")
    public ModelAndViewContainer show() {
        return ModelAndViewContainer.initWith();
    }

    @GET
    @Path("/display")
    public ModelAndViewContainer display() throws SQLException {
        //TODO add PathVariable annotation support, do it in DataBinder \\{([^/]+?)\\}
        List<Article> articles = Article.find_all();

        return ModelAndViewContainer.initWith(articles);
    }

    @POST
    @Path("/create")
    public ModelAndViewContainer create(Article article) throws SQLException {

        //fake business logic
        article.setUrl("http://www.example.com/2013/" + article.getAuthorId() + "/" + article.getTitle());
        article.save();
        if (article.getAuthor() != null && article.getAuthor().getEmail() != null) {
            article.getAuthor().save();
        }

        //using injected service
        mailService.sendMail("a@b.com", article.toString());

        return ModelAndViewContainer.initWith(article);
    }

    @POST
    @Path("/edit")
    public ModelAndViewContainer edit(Article article) {

        return ModelAndViewContainer.initWith(article);
    }
}
