package com.dummy;

import com.expressioc.ExpressContainer;
import com.expressmvc.DispatchServlet;
import com.expressmvc.controller.MappingResolver;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.thoughtworks.DB;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class Demo implements Runnable{
    public static final int PORT = 3000;
    public static final String HOST = "localhost";
    private static final String MVC_FRAMEWORK = "com.expressmvc";
    private static final String DEMO_APP_PACKAGE = "com.dummy";

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread demo = new Thread(new Demo());
        demo.setDaemon(true);
        demo.start();

        Thread.currentThread().join();
    }

    @Override
    public void run() {
        try {
            setupDatabase();
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startServer() throws IOException, SQLException {
        createHttpServer().start();
    }

    private static HttpServer createHttpServer() {
        WebappContext ctx = new WebappContext("demo", "/demo");
        HttpServer httpServer = HttpServer.createSimpleServer("/", HOST, PORT);

        final ServletRegistration reg = ctx.addServlet("", new ExpressContainer(MVC_FRAMEWORK).getComponent(DispatchServlet.class));
        reg.setInitParameter(MappingResolver.WEB_APP_ROOT_PACKAGE, DEMO_APP_PACKAGE);
        reg.addMapping("/");

        ctx.deploy(httpServer);
        return httpServer;
    }

    private static void setupDatabase() throws IOException, SQLException {
        URL url = Resources.getResource("dbschema.sql");
        String sqls = Resources.toString(url, Charsets.UTF_8);

        for (String sql : sqls.split(";")) {
            if (Strings.isNullOrEmpty(sql.trim())) {
                continue;
            }

            Statement statement = DB.connection().createStatement();
            statement.execute(sql);
            statement.close();
        }
    }
}