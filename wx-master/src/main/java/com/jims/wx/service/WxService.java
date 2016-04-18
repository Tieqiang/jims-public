package com.jims.wx.service;



import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.RequestMessageFacade;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import weixin.popular.api.TokenAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.user.User;
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
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by heren on 2016/2/24.
 */
@Path("wx-service")
public class WxService {


    private RequestMessageFacade requestMessageFacade ;
    private AppUserFacade appUserFacade ;
    private HttpServletRequest request ;
    private HttpServletResponse response ;

    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();
    @Inject
    public WxService(RequestMessageFacade requestMessageFacade, AppUserFacade appUserFacade, HttpServletRequest request, HttpServletResponse response) {
        this.requestMessageFacade = requestMessageFacade;
        this.appUserFacade = appUserFacade;
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
            //不同的消息类型有不同处理方式
            String msgType = eventMessage.getMsgType() ;
            String event = eventMessage.getEvent() ;
            if("event".equals(msgType)&&"subscribe".equals(event)){
                //公众号订阅
                String fromUser = eventMessage.getFromUserName() ;
                User user = UserAPI.userInfo(this.getToken(), fromUser) ;
                appUserFacade.createUser(user) ;
            }

            if("unsubscribe".equals(event)&&"event".equals(msgType)){
                //取消订阅公众号
            }

            if("text".equals(msgType)||"image".equals(msgType)||"voice".equals(msgType)
                    ||"video".equals(msgType)||"shortvideo".equals(msgType)){//普通消息
                requestMessageFacade.saveMsg(eventMessage) ;
            }

            if("location".equals(msgType)){
                //上报地理信息
            }
            if("link".equals(msgType)){
                //超链接
            }
            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "您所发送消息已经收到，会尽快回复您");
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


}
