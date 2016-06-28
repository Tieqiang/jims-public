package com.jims.wx.listener;

import com.sun.media.sound.SoftTuning;
import weixin.popular.support.TokenManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TokenManagerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//WEB容器 初始化时调用
        String appId = sce.getServletContext().getInitParameter("APP_ID") ;
        String appSecret = sce.getServletContext().getInitParameter("APP_SERECT") ;
		TokenManager.init(appId, appSecret);
        System.out.println("tokenManager 初始化.....");
        System.out.println("tokenManager 初始化完成");
    }

	@Override

	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TokenManager.destroyed();
	}
}
