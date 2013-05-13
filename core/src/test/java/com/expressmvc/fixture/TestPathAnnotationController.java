package com.expressmvc.fixture;

import com.expressmvc.annotation.Path;
import com.expressmvc.model.ModelContainer;

@Path("/article")
public class TestPathAnnotationController{

    public TestPathAnnotationController() {
    }

    @Path("/create")
    public ModelContainer create() {
        return null;
    }

    @Path("/edit")
    public ModelContainer edit() {
        return null;
    }
}
