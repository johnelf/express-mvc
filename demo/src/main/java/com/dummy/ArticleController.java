package com.dummy;

import com.expressmvc.annotation.Path;
import com.expressmvc.annotation.http.POST;
import com.expressmvc.controller.AppFormController;
import com.expressmvc.controller.Envelope;

@Path("/article")
public class ArticleController extends AppFormController{

    @POST
    public Envelope create(Envelope envelope, Article article) {
        article.setUrl("http://www.example.com/2013/article_1");
        return envelope.add(article);
    }


}
