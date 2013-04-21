package com.dummy;

import com.expressmvc.controller.AnnotationBasedMappingResolver;
import com.expressmvc.servlet.DispatchServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import java.io.IOException;

public class Demo {
    public static final int PORT = 9090;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.createSimpleServer("/", HOST, PORT);
        WebappContext ctx = new WebappContext("Demo", "/");
        addServlet(ctx, "", "/");
        ctx.deploy(httpServer);
        httpServer.start();
        System.out.println("Press any key to stop the server...");
        System.in.read();
    }

    private static ServletRegistration addServlet(final WebappContext ctx, final String name, final String alias) {
        final ServletRegistration reg = ctx.addServlet(name, new DispatchServlet());
        reg.setInitParameter(AnnotationBasedMappingResolver.PACKAGE_TO_SCAN, "com.dummy");
        reg.addMapping(alias);
        return reg;
    }
}