package com.expressmvc.fixture;

import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;
import com.expressmvc.model.ModelContainer;

@Path("/article")
public class TestPathAnnotationController extends AppController {

    @Path("/create")
    public ModelContainer create() {
        return null;
    }

    @Path("/edit")
    public ModelContainer edit() {
        return null;
    }
}
