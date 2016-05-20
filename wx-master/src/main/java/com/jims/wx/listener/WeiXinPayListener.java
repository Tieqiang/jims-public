package com.jims.wx.listener;

import com.jims.wx.util.WeiXinPayUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by admin on 2016/4/27.
 */
public class WeiXinPayListener implements ServletContextListener {
    /**
      * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String APP_ID= sce.getServletContext().getInitParameter("APP_ID");
        String APP_SERECT= sce.getServletContext().getInitParameter("APP_SERECT");
        String MCH_ID= sce.getServletContext().getInitParameter("MCH_ID");
        String KEY= sce.getServletContext().getInitParameter("KEY");
        WeiXinPayUtils w=new WeiXinPayUtils();
        w.init(APP_ID,APP_SERECT,MCH_ID,KEY);
//        System.out.println("MCH_ID="+MCH_ID);
        System.out.println("WeiXinPayListener 初始化完毕！");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
