package com.dummy;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
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
        WebappContext ctx = new WebappContext("demo", "/demo");

        Container container = new ExpressContainer("com.expressmvc");
        final ServletRegistration reg = ctx.addServlet("", container.getComponent(DispatchServlet.class));
        reg.setInitParameter("package-to-scan", "com.dummy");
        reg.addMapping("/");

        ctx.deploy(httpServer);
        httpServer.start();
        System.out.println("Press any key to stop the server...");
        System.in.read();
    }

}