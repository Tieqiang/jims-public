package com.jims.wx.service;


import com.jims.wx.entity.AppUser;

import com.jims.wx.entity.WxOpenAccountConfig;
import com.jims.wx.facade.*;
import com.jims.wx.util.Bare;
import com.jims.wx.util.WeiXinPayUtils;
import com.jims.wx.vo.AppSetVo;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.springframework.web.bind.annotation.RequestBody;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.TokenAPI;
import com.jims.wx.vo.AppSetVo;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.pay.PayPackage;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.client.LocalHttpClient;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
import weixin.popular.util.PayUtil;
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
import java.util.List;
import java.net.URLEncoder;

/**
 * Created by heren on 2016/2/24.
 */
@Path("wx-service")
public class WxService {


    private RequestMessageFacade requestMessageFacade;
    private AppUserFacade appUserFacade;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private WxOpenAccountConfigFacade wxOpenAccountConfigFacade;
    private PatVsUserFacade patVsUserFacade;
    private HospitalInfoFacade hospitalInfoFacade;
    private static final String MCH_ID = "1318000301";//微信支付商号
    private static final String KEY = "jmyruanjianyouxiangongsi84923632";//API密钥 or 商户支付密钥
    private static final String APP_ID = "wx890edf605415aaec";//商户的APP_ID
    private static final String APP_SERECT = "0e9446b7e5690138e6f8b1d07bb04a02";
    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @Inject
    public WxService(RequestMessageFacade requestMessageFacade, AppUserFacade appUserFacade, HttpServletRequest request, HttpServletResponse response, WxOpenAccountConfigFacade wxOpenAccountConfigFacade, PatVsUserFacade patVsUserFacade, HospitalInfoFacade hospitalInfoFacade) {
        this.requestMessageFacade = requestMessageFacade;
        this.appUserFacade = appUserFacade;
        this.request = request;
        this.response = response;
        this.wxOpenAccountConfigFacade = wxOpenAccountConfigFacade;
        this.patVsUserFacade = patVsUserFacade;
        this.hospitalInfoFacade = hospitalInfoFacade;
    }

    @Path("check")
    @Produces("text/html")
    @GET
    public String checkSignature(@QueryParam("signature") String signature, @QueryParam("timestamp") String timestamp,
                                 @QueryParam("nonce") String nonce, @QueryParam("echostr") String echostr) {
        System.out.println(signature);
        System.out.println(timestamp);
        System.out.println(nonce);
        System.out.println(echostr);
        System.out.println(request.toString());
        System.out.println(response.toString());
        return echostr;
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
        if (echostr != null) {
            outputStreamWrite(outputStream, echostr);
            return;
        }

        if (inputStream != null) {
            //转换XML
            EventMessage eventMessage = XMLConverUtil.convertToObject(EventMessage.class, inputStream);
            String key = eventMessage.getFromUserName() + "__"
                    + eventMessage.getToUserName() + "__"
                    + eventMessage.getMsgId() + "__"
                    + eventMessage.getCreateTime();
            System.out.println(eventMessage.getContent());
            if (expireKey.exists(key)) {
                //重复通知不作处理
                return;
            } else {
                expireKey.add(key);
            }
            //不同的消息类型有不同处理方式
            String msgType = eventMessage.getMsgType();
            String event = eventMessage.getEvent();
            if ("event".equals(msgType) && "subscribe".equals(event)) {
                //公众号订阅
                String fromUser = eventMessage.getFromUserName();
                User user = UserAPI.userInfo(this.getToken(), fromUser);
                AppUser appUser=appUserFacade.createUser(user);
                if(appUser!=null&&!"".equals(appUser)){//关注成功
                String message="欢迎关注，详情点击"+"<a href='http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/app-dept-dict-info.html'>详情</a>";
                    //创建回复
                    XMLMessage xmlTextMessage = new XMLTextMessage(
                            eventMessage.getFromUserName(),
                            eventMessage.getToUserName(),
                            message);
                    //回复
                    xmlTextMessage.outputStreamWrite(outputStream);
                    return;
                }

             }
            if ("unsubscribe".equals(event) && "event".equals(msgType)) {
                //取消订阅公众号


            }

            if ("text".equals(msgType) || "image".equals(msgType) || "voice".equals(msgType)
                    || "video".equals(msgType) || "shortvideo".equals(msgType)) {//普通消息
                requestMessageFacade.saveMsg(eventMessage);
            }

            if ("location".equals(msgType)) {
                //上报地理信息
            }
            if ("link".equals(msgType)) {
                //超链接
            }

            if ("VIEW".equals(event) && "event".equals(msgType)) {
            }
            //创建回复
            //创建回复
            XMLMessage xmlTextMessage = new XMLTextMessage(
                    eventMessage.getFromUserName(),
                    eventMessage.getToUserName(),
                    "您所发送消息已经收到，会尽快回复您");
            //回复
            xmlTextMessage.outputStreamWrite(outputStream);

            return;
        }
        outputStreamWrite(outputStream, "");
    }

    /**
     * 数据流输出
     *
     * @param outputStream
     * @param text
     * @return
     */
    private boolean outputStreamWrite(OutputStream outputStream, String text) {
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
    public String getToken() {
        return TokenManager.getDefaultToken();
    }


    @GET
    @Path("questionnaire-survey")
    public String test(@QueryParam("code") String code) throws IOException {
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        List<AppUser> appList = appUserFacade.findByOpenId(snsToken.getOpenid());
        String patId = "";
        if (appList.size() > 0) {
            patId = appList.get(0).getPatId();
        }
        response.sendRedirect("/views/his/public/questionnaire-survey.html?openId=" + snsToken.getOpenid() + "&patId=" + patId);
        return "http://www.baidu.com/";
    }

    @GET
    @Path("pat-visit")
    public String inp(@QueryParam("code") String code) throws IOException {
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        List<AppUser> appList = appUserFacade.findByOpenId(snsToken.getOpenid());
        String patientId = "";
        if (appList.size() > 0) {
            patientId = appList.get(0).getPatId();
        }
        response.sendRedirect("/views/his/public/Pat-Visit.html?openId=" + snsToken.getOpenid() + "&patientId=" + patientId);
        return "http://www.baidu.com/";
    }

    @GET
    @Path("user-bangker")
    public String userBangker(@QueryParam("code") String code) {
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
        //测试用
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        try {
            response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * app挂号选择科室
     *
     * @param code
     * @return
     */
    @GET
    @Path("find-dept")
    public String findDept(@QueryParam("code") String code) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
            //测试用
            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
            System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
            /**
             * 如果次微信用户有绑定的患者，则跳到挂号页面，否则跳到绑卡页面
             */
            boolean flag = patVsUserFacade.findIsExistsPatInfo(snsToken.getOpenid());
            if (flag) {//绑定和患者
                response.sendRedirect("/views/his/public/app-dept-dict.html?param=" + snsToken.getOpenid());
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * JSAPI支付
     *
     * @return json 字符串
     */
    @POST
    @Path("pay-js")
    public String payJs(@RequestBody String xml) {
        String json = "";//返回的json字符串；
        String price = request.getParameter("price");
        String openId = request.getParameter("openId");
        String body = "测试挂号";
        String notifyUrl = "http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/app-pay-success.html";
        String ip = request.getRemoteAddr();
        if (price.contains(".")) {//double
            price = String.valueOf(Double.valueOf(price) * 100);
        } else {//integer
            price = String.valueOf(Integer.valueOf(price) * 100);
        }
        json = WeiXinPayUtils.weiXinPayNeedJson(price, openId, body, notifyUrl, ip);
        return json;
    }

    @GET
    @Path("rcpt-list")
    public String rcpt(@QueryParam("code")String code) throws IOException {
        AppSetVo appSetVo= hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(),appSetVo.getAppSecret(), code);
        List<AppUser> appList=appUserFacade.findByOpenId(snsToken.getOpenid());
        String patId="";
        if(appList.size()>0){
            patId=appList.get(0).getPatId();
        }
        response.sendRedirect("/views/his/public/rcpt-master.html?openId="+snsToken.getOpenid());
        return "http://www.baidu.com/" ;    }

    @POST
    @Path("pay-jsp")
    public String payJs2() {

        PayPackage packageParams = new PayPackage();
        packageParams.setBank_type("WX");
        packageParams.setBody("挂号");
        packageParams.setFee_type("CHM");
        packageParams.setInput_charset("UTF-8");
        packageParams.setNotify_url(" http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/app-pay-success.html");
        packageParams.setOut_trade_no("1");
        packageParams.setPartner(MCH_ID);
        packageParams.setSpbill_create_ip(request.getRemoteHost());
        packageParams.setTotal_fee("10");
        String json = PayUtil.generatePayJsRequestJson(packageParams, "wx890edf605415aaec", KEY, KEY);
        return json;
    }

    @GET
    @Path("find-pat-info")
    public String findPatInfo(@QueryParam("code")String code) throws IOException {
        AppSetVo appSetVo= hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(),appSetVo.getAppSecret(), code);
        response.sendRedirect("/views/his/public/app-my-information.html?openId="+snsToken.getOpenid());
        return "" ;
    }

}
