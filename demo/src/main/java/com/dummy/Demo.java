package com.dummy;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressmvc.DispatchServlet;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import java.io.IOException;

public class Demo implements Runnable{
    public static final int PORT = 3000;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread demo = new Thread(new Demo());
        demo.setDaemon(true);
        demo.start();

        Thread.currentThread().join();
    }

    public static void startServer() throws IOException {
        HttpServer httpServer = HttpServer.createSimpleServer("/", HOST, PORT);
        WebappContext ctx = new WebappContext("demo", "/demo");

        Container container = new ExpressContainer("com.expressmvc");
        final ServletRegistration reg = ctx.addServlet("", container.getComponent(DispatchServlet.class));
        reg.setInitParameter("webapp_root_package", "com.dummy");
        reg.addMapping("/");

        ctx.deploy(httpServer);
        httpServer.start();
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}