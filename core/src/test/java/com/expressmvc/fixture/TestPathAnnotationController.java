package com.expressmvc.fixture;

import com.expressmvc.annotation.Path;
import com.expressmvc.controller.AppController;
import com.expressmvc.controller.Envelope;

@Path("/article")
public class TestPathAnnotationController extends AppController {

    @Path("/create")
    public Envelope create() {
        return null;
    }

    @Path("/edit")
    public Envelope edit() {
        return null;
    }
}
