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

    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public static String appId = "wx75bf60f4b0d3a8fb";
    public static String secret = "12bacb204a9a6e168843df163f903e51";

    @Path("check")
    @Produces("text/html")
    @GET
    public String checkSignature(@QueryParam("signature")String signature,@QueryParam("timestamp")String timestamp,
                                 @QueryParam("nonce")String nonce,@QueryParam("echostr")String echostr){
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);

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
        String token ="";
        String expires = "";
        try {
            httpClient.start();
            //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx75bf60f4b0d3a8fb&secret=12bacb204a9a6e168843df163f903e51" ;
            String url = access_token_url.replace("APPID", appId).replace("APPSECRET", secret);
            ContentResponse response = httpClient.GET(url) ;
            String str = response.getContentAsString() ;
            System.out.println("success:"+str);
            if(null != str && str.contains("access_token")){
                String [] info = str.split(",");
                token = info[0].substring(info[0].indexOf("\":\"")+3,info[0].length()-1);
                System.out.println("token:" + token);
                expires = info[1].substring(info[1].indexOf("\":")+2);
                System.out.println("expires:" + expires);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return token ;
    }

    private void createMyMenu(){

        String accessToken = getAccessToKen();
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
        String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken ;
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

    public void receiveTextMessage(){

    }
}
