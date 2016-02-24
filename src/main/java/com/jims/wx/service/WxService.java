package com.jims.wx.service;



import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 * Created by heren on 2016/2/24.
 */
@Path("wx-service")
public class WxService {


    @Path("check")
    @Produces("text/html")
    @GET
    public String checkSignature(@QueryParam("signature")String signature,@QueryParam("timestamp")String timestamp,
                                 @QueryParam("nonce")String nonce,@QueryParam("echostr")String echostr){
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        createMyMenu() ;
        return echostr ;
    }


    /**
     * 获取access_tooken access_tooken一般过期时间为7200s
     * @return
     */
    private String getAccessToKen(){
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory) ;
        httpClient.setConnectTimeout(5000);

        try {
            httpClient.start();
            String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxecde8da70c21dc65&secret=d4624c36b6795d1d99dcf0547af5443d" ;
            ContentResponse response = httpClient.GET(url) ;
            String str = response.getContentAsString() ;
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null ;
    }

    private void createMyMenu(){
        String accessTooken = "j--GLT-ZusnA4okojdU5G-JHWab-LcQ-DcA38RpF90AolSqb3-huMqUXLtL7QQueJxk-FIaUzezS1RJ-6pqIOKzpbtHElhMbisHS2t3hXRUoZTy6xOgLgc1XQJ_KZYQ4WMRcAJAGWB" ;
        String menuData = " {\n" +
                "     \"button\":[\n" +
                "     {\t\n" +
                "          \"type\":\"click\",\n" +
                "          \"name\":\"今日歌曲\",\n" +
                "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
                "      },\n" +
                "      {\n" +
                "           \"name\":\"菜单\",\n" +
                "           \"sub_button\":[\n" +
                "           {\t\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"搜索\",\n" +
                "               \"url\":\"http://www.soso.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"view\",\n" +
                "               \"name\":\"视频\",\n" +
                "               \"url\":\"http://v.qq.com/\"\n" +
                "            },\n" +
                "            {\n" +
                "               \"type\":\"click\",\n" +
                "               \"name\":\"赞一下我们\",\n" +
                "               \"key\":\"V1001_GOOD\"\n" +
                "            }]\n" +
                "       }]\n" +
                " }" ;
        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessTooken ;
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory) ;
        try {
            httpClient.start();
            //httpClient.addBean(menuData) ;
            Request request =httpClient.POST(url) ;
            ContentResponse response= request.send() ;
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
