package com.expressmvc.test;

import com.expressmvc.ModelAndView;
import com.expressmvc.annotation.Path;
import com.expressmvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("/article")
public class ArticleController extends BaseController {
    private final MailService mailService;

    public ArticleController(MailService mailService) {
        this.mailService = mailService;
    }

    //@Post
    @Override
    public ModelAndView doService(HttpServletRequest req, HttpServletResponse resp) {
        this.mailService.sendNotificationMailToReader();
        ModelAndView modelAndView = new ModelAndView();
        //TOOD set model
        return modelAndView;
    }
}
