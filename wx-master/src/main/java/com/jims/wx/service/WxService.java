package com.jims.wx.service;



import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import weixin.popular.api.TokenAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.SignatureUtil;
import weixin.popular.util.XMLConverUtil;

import javax.inject.Inject;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by heren on 2016/2/24.
 */
@Path("wx-service")
public class WxService {



    private HttpServletRequest request ;
    private HttpServletResponse response ;

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();
    @Inject
    public WxService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Path("check")
    @Produces("text/html")
    @GET
    public String checkSignature(@QueryParam("signature")String signature,@QueryParam("timestamp")String timestamp,
                                 @QueryParam("nonce")String nonce,@QueryParam("echostr")String echostr){
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        System.out.println(request.toString());
        System.out.println(response.toString());
        return echostr ;
    }

    @POST
    @Path("check")
    @Produces("text/html")
    public void postMsg() throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        ServletOutputStream outputStream = response.getOutputStream();
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");

        //首次请求申请验证,返回echostr
        if(echostr!=null){
            outputStreamWrite(outputStream,echostr);
            return;
        }

        //验证请求签名
        //if(!signature.equals(SignatureUtil.generateEventMessageSignature(TokenManager.getDefaultToken(), timestamp, nonce))){
        //    System.out.println("The request signature is invalid");
        //    return;
        //}

        if(inputStream!=null){
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();
            System.out.println(eventMessage.getContent());
            if(expireKey.exists(key)){
                //重复通知不作处理
                return;
            }else{
                expireKey.add(key);
            }

            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "这个世界很美好，请你按照你所说的内容写一个信息");
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);
            return;
        }
        outputStreamWrite(outputStream,"");
    }
    /**
     * 数据流输出
     * @param outputStream
     * @param text
     * @return
     */
    private boolean outputStreamWrite(OutputStream outputStream,String text){
        try {
            outputStream.write(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
        return true;
    }
    @GET
    @Path("token")
    public String getToken(){
        return TokenManager.getDefaultToken() ;
    }



    //
    ///**
    // * 获取access_tooken access_tooken一般过期时间为7200s
    // * @return
    // */
    //private String getAccessToKen(){
    //    SslContextFactory sslContextFactory = new SslContextFactory();
    //    HttpClient httpClient = new HttpClient(sslContextFactory) ;
    //    httpClient.setConnectTimeout(5000);
    //    String token ="";
    //    String expires = "";
    //    try {
    //        httpClient.start();
    //        //String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx75bf60f4b0d3a8fb&secret=12bacb204a9a6e168843df163f903e51" ;
    //        String url = access_token_url.replace("APPID", appId).replace("APPSECRET", secret);
    //        ContentResponse response = httpClient.GET(url) ;
    //        String str = response.getContentAsString() ;
    //        System.out.println("success:"+str);
    //        if(null != str && str.contains("access_token")){
    //            String [] info = str.split(",");
    //            token = info[0].substring(info[0].indexOf("\":\"")+3,info[0].length()-1);
    //            System.out.println("token:" + token);
    //            expires = info[1].substring(info[1].indexOf("\":")+2);
    //            System.out.println("expires:" + expires);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //
    //
    //    return token ;
    //}
    //
    //private void createMyMenu(){
    //
    //    String accessToken = getAccessToKen();
    //    String menuData = " {\n" +
    //            "     \"button\":[\n" +
    //            "     {\t\n" +
    //            "          \"type\":\"click\",\n" +
    //            "          \"name\":\"今日歌曲\",\n" +
    //            "          \"key\":\"V1001_TODAY_MUSIC\"\n" +
    //            "      },\n" +
    //            "      {\n" +
    //            "           \"name\":\"菜单\",\n" +
    //            "           \"sub_button\":[\n" +
    //            "           {\t\n" +
    //            "               \"type\":\"view\",\n" +
    //            "               \"name\":\"搜索\",\n" +
    //            "               \"url\":\"http://www.soso.com/\"\n" +
    //            "            },\n" +
    //            "            {\n" +
    //            "               \"type\":\"view\",\n" +
    //            "               \"name\":\"视频\",\n" +
    //            "               \"url\":\"http://v.qq.com/\"\n" +
    //            "            },\n" +
    //            "            {\n" +
    //            "               \"type\":\"click\",\n" +
    //            "               \"name\":\"赞一下我们\",\n" +
    //            "               \"key\":\"V1001_GOOD\"\n" +
    //            "            }]\n" +
    //            "       }]\n" +
    //            " }" ;
    //    String url = " https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken ;
    //    SslContextFactory sslContextFactory = new SslContextFactory();
    //    HttpClient httpClient = new HttpClient(sslContextFactory) ;
    //    try {
    //        httpClient.start();
    //        //httpClient.addBean(menuData) ;
    //        Request request =httpClient.POST(url) ;
    //        ContentResponse response= request.send() ;
    //        System.out.println(response);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //
    //}
    //
    //public void receiveTextMessage(){
    //
    //}
}
