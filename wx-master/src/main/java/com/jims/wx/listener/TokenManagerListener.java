package com.jims.wx.listener;

import com.sun.media.sound.SoftTuning;
import weixin.popular.support.TokenManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class TokenManagerListener implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//WEB容器 初始化时调用
		TokenManager.init("wxecde8da70c21dc65", "d4624c36b6795d1d99dcf0547af5443d");
		//TokenManager.init("wx46af0b9fd8bdc7d7", "cc8510ce2bfe486ee9d83a010bffa58e");
        System.out.println("yes it is me !");
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		//WEB容器  关闭时调用
		TokenManager.destroyed();
	}
}
