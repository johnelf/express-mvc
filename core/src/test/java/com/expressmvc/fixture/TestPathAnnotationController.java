package com.expressmvc.fixture;

import com.expressmvc.annotation.Path;

@Path("/article")
public class TestPathAnnotationController{

    public TestPathAnnotationController() {
    }

    @Path("/create")
    public void create() {
    }

    @Path("/edit")
    public void edit() {
    }
}
