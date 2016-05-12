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
        //测试用
//        TokenManager.init("wx1b3cf470d135a830", "df933603351f378c54883853e05dd228");
        System.out.println("tokenManager 初始化.....");
        System.out.println("tokenManager 初始化完成");
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TokenManager.destroyed();
	}
}
