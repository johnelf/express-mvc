package com.expressmvc.test;

import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.Path;
import com.expressmvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/article")
public class ArticleController implements BaseController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public ModelAndView service(HttpServletRequest req, HttpServletResponse resp) {
        this.mailService.sendNotificationMailToReader();
        return null;
    }
}
