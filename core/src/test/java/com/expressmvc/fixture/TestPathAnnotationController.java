package com.expressmvc.fixture;

import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.ModelAndViewContainer;

@Path("/article")
public class TestPathAnnotationController extends AppController {

    @Path("/create")
    public ModelAndViewContainer create() {
        return null;
    }

    @Path("/edit")
    public ModelAndViewContainer edit() {
        return null;
    }
}
