package com.expressmvc.test;

import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;

@Path("/article")
public class ArticleController extends AppController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }
}
