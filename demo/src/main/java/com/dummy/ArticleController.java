package com.dummy;

import com.dummy.models.Article;
import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.Envelope;

@Path("/article")
public class ArticleController extends AppController {
    @POST
    public Envelope create(Article article) {
        article.setUrl("http://www.example.com/2013/article_1");
        return Envelope.initWith(article);
    }
}
