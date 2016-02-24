package com.jims.wx;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.File;
//import org.flywaydb.core.Flyway;

/**
 * Created by heren on 2014/10/13.
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        String webappDirLocation = "src/main/webapp/";

        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "80";
        }

        File file = new File(webappDirLocation) ;
        if(file.exists()){
            System.out.println(file.getAbsolutePath());
        }else{
            System.out.println("路径有问题");
        }

        Server server = new Server(Integer.valueOf(webPort));
        WebAppContext root = new WebAppContext();

        root.setContextPath("/");
        root.setDescriptor(webappDirLocation + "/WEB-INF/web.xml");
        root.setResourceBase(webappDirLocation);
        root.setParentLoaderPriority(true);
        server.setHandler(root);
        server.start();
        server.join();
    }

}
