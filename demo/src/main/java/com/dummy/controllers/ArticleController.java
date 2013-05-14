package com.dummy.controllers;

import com.dummy.models.Article;
import com.dummy.models.Author;
import com.dummy.services.MailService;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.GET;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.model.ModelContainer;
import com.thoughtworks.Model;
import com.thoughtworks.query.QueryList;

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
    public ModelContainer display() {
        List<Author> authors = Author.find_all().includes(Article.class);
        return ModelContainer.initWith(authors);
    }

    @POST
    @Path("/create")
    public ModelContainer create(Article article) {

        String criteria = String.format("name = '%s'", article.getAuthor().getName());
        Author isAuthorExist = Author.find_first(criteria);
        if (isAuthorExist != null) {
            article.setAuthorId(isAuthorExist.getId());
        } else if (article.getAuthor() != null && article.getAuthor().getEmail() != null) {
            Author author = article.getAuthor().save();
            article.setAuthorId(author.getId());
        }
        article.setUrl("http://www.example.com/2013/" + article.getAuthorId() + "/" + article.getTitle());
        article.save();

        //using injected service
        mailService.sendMail("a@b.com", article.toString());

        return ModelContainer.initWith(article);
    }

}
