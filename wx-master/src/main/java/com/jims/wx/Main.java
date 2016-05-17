package com.jims.wx;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.flywaydb.core.Flyway;

import java.io.File;

/**
 * Created by heren on 2014/10/13.
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{

        String webappDirLocation = "wx-master/src/main/webapp/";
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
        //migrationDb();
        server.start();
        server.join();
    }


    /**
     * 数据库版本控制
     */
    public static void migrationDb(){
         Flyway flyway = new Flyway();
        //设置数据库链接配置
        flyway.setDataSource("jdbc:oracle:thin:@127.0.0.1:1521:orcl","wx","wx");
        //设置schema用户
        flyway.setSchemas("WX");
        flyway.setTable("SCHEMA_VERSION");
        flyway.setEncoding("UTF-8");
        flyway.setValidateOnMigrate(true);
        //清空所有表结构
        flyway.clean();
        //初始化flyWAy
        flyway.init();
        //执行版本控制
        flyway.migrate();
     }
}
