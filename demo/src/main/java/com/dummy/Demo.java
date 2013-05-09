package com.dummy;

import com.expressioc.Container;
import com.expressioc.ExpressContainer;
import com.expressmvc.DispatchServlet;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.io.Resources;
import com.thoughtworks.DB;
import com.thoughtworks.ModelInstrument;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo implements Runnable{
    public static final int PORT = 3000;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException {
        Thread demo = new Thread(new Demo());
        demo.setDaemon(true);
        demo.start();

        Thread.currentThread().join();
    }

    public static void startServer() throws IOException, SQLException {
        HttpServer httpServer = HttpServer.createSimpleServer("/", HOST, PORT);
        WebappContext ctx = new WebappContext("demo", "/demo");

        Container container = new ExpressContainer("com.expressmvc");
        final ServletRegistration reg = ctx.addServlet("", container.getComponent(DispatchServlet.class));
        reg.setInitParameter("webapp_root_package", "com.dummy");
        reg.addMapping("/");

        Connection connection = DB.connection();
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

        ctx.deploy(httpServer);
        httpServer.start();
    }

    @Override
    public void run() {
        try {
            startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}