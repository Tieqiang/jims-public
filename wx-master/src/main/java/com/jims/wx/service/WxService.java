package com.jims.wx.service;


import com.jims.wx.entity.AppUser;

import com.jims.wx.entity.PatInfo;
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
    private PatInfoFacade patInfoFacade;
    private static final String MCH_ID = " ";//微信支付商号
    private static final String KEY = " ";//API密钥 or 商户支付密钥
    private static final String APP_ID = " ";//商户的APP_ID
    private static final String APP_SERECT = " ";
    //重复通知过滤
    private static ExpireKey expireKey = new DefaultExpireKey();

    @Inject
    public WxService(RequestMessageFacade requestMessageFacade, AppUserFacade appUserFacade, HttpServletRequest request, HttpServletResponse response, WxOpenAccountConfigFacade wxOpenAccountConfigFacade, PatVsUserFacade patVsUserFacade, HospitalInfoFacade hospitalInfoFacade, PatInfoFacade patInfoFacade) {
        this.requestMessageFacade = requestMessageFacade;
        this.appUserFacade = appUserFacade;
        this.request = request;
        this.response = response;
        this.wxOpenAccountConfigFacade = wxOpenAccountConfigFacade;
        this.patVsUserFacade = patVsUserFacade;
        this.hospitalInfoFacade = hospitalInfoFacade;
        this.patInfoFacade = patInfoFacade;
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
                AppUser appUser = appUserFacade.createUser(user);
                if (appUser != null && !"".equals(appUser)) {//关注成功
                    String message = "欢迎关注，详情点击" + "<a href='/views/his/public/app-dept-dict-info.html'>详情</a>";
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
                String fromUser = eventMessage.getFromUserName();
//                User user = UserAPI.userInfo(this.getToken(), fromUser);
                AppUser appUser = appUserFacade.findAppUserByOpenId(fromUser);
//                List<PatInfo> list=patVsUserFacade.findPatInfosByAppUserId(appUser.getId());
//                patInfoFacade.delete(list);
                if (appUser != null) {
                    appUserFacade.deleteByObject(appUser);
                }
                //                patVsUserFacade.deleteByAppUserId(appUser.getId());
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
             e.printStackTrace();
            return false;
        } catch (IOException e) {
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
//        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        SnsToken snsToken =getSnsToken(code);
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
//        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//        AppUser  appUser = appUserFacade.findAppUserByOpenId(snsToken.getOpenid());

        SnsToken snsToken =getSnsToken(code);
        response.sendRedirect("/views/his/public/Pat-Visit.html?openId=" + snsToken.getOpenid());
        return "http://www.baidu.com/";
    }

    @GET
    @Path("user-bangker")
    public String userBangker(@QueryParam("code") String code) {
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
        //测试用
//        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
          SnsToken snsToken =getSnsToken(code);
        try {
            response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * app当天挂号选择科室
     *
     * @param code
     * @return
     */
    @GET
    @Path("find-dept")
    public String findDept(@QueryParam("code") String code, @QueryParam("openId") String openId) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
            //测试用
            String openIdStr = "";
            SnsToken snsToken = null;
            if (openId == null || openId == "") {
//                AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//                snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//                System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
                  snsToken =getSnsToken(code);
                openIdStr = snsToken.getOpenid();
            } else {
                openIdStr = openId;
            }
            /**
             * 如果次微信用户有绑定的患者，则跳到挂号页面，否则跳到绑卡页面
             */
            boolean flag = patVsUserFacade.findIsExistsPatInfo(openIdStr);
            if (flag) {//绑定和患者
                response.sendRedirect("/views/his/public/app-dept-dict.html?param=" + openIdStr);
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openIdStr);
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
        String body = "挂号";
        String notifyUrl = "/views/his/public/app-pay-success.html";
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
    public String rcpt(@QueryParam("code") String code) throws IOException {
//        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        SnsToken snsToken =getSnsToken(code);
        List<AppUser> appList = appUserFacade.findByOpenId(snsToken.getOpenid());
        String patId = "";
        if (appList.size() > 0) {
            patId = appList.get(0).getPatId();
        }
        response.sendRedirect("/views/his/public/rcpt-master.html?openId=" + snsToken.getOpenid());
        return "http://www.baidu.com/";
    }

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
    public String findPatInfo(@QueryParam("code") String code) throws IOException {
//        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        SnsToken snsToken =getSnsToken(code);
        response.sendRedirect("/views/his/public/app-my-information.html?openId=" + snsToken.getOpenid());
        return "";
    }

    // window.location.href="/views/his/public/app-doct-info.html?deptId="+deptId+"&openId="+openId;
    @GET
    @Path("query-string")
    public String queryString(@QueryParam("openId") String openId, @QueryParam("deptId") String deptId) throws IOException {
        response.sendRedirect("/views/his/public/app-doct-info.html?openId=" + openId + "&deptId=" + deptId);
        return "";
    }

    @GET
    @Path("app-pay")
    public String appPay(@QueryParam("openId") String openId, @QueryParam("clinicForRegistId") String clinicForRegistId, @QueryParam("price") String price) throws IOException {
        response.sendRedirect("/views/his/public/app-pay.html?openId=" + openId + "&price=" + price + "&clinicForRegistId=" + clinicForRegistId);
        return "";
    }

    @GET
    @Path("get-param")
    public String getParam(@QueryParam("patId") String patId,@QueryParam("openId") String openId) throws IOException {
        response.sendRedirect("/views/his/public/app-pat-info.html?patId=" + patId+"&openId="+openId);
        return "";
    }


    @GET
    @Path("get-regist-id")
    public String getRegistId(@QueryParam("clinicForRegistId") String clinicForRegistId) throws IOException {
        response.sendRedirect("/views/his/public/app-regist-doct-info.html?clinicForRegistId=" + clinicForRegistId);
        return "";
    }

    @GET
    @Path("find-by-id")
    public String findById(@QueryParam("patId") String patId, @QueryParam("openId") String openId, @QueryParam("mid") String mid) throws IOException {
        response.sendRedirect("/views/his/public/app-pat-info.html?patId=" + patId + "&openId=" + openId + "&mid=" + mid);
        return "";
    }


    @GET
    @Path("query-dept")
    public String queryDept(@QueryParam("deptId") String deptId) throws IOException {
        response.sendRedirect("/views/his/public/app-dept-info.html?deptId=" + deptId);
        return "";
    }

    @GET
    @Path("change")
    public String change(@QueryParam("openId") String openId) throws IOException {
        response.sendRedirect("/views/his/public/app-my-information.html?openId=" + openId);
        return "";
    }

    //    query-open
    @GET
    @Path("query-open")
    public String queryOpen(@QueryParam("openId") String openId) throws IOException {
        response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openId);
        return "";
    }

    @GET
    @Path("regist-open")
    public String registOpen(@QueryParam("openId") String openId) throws IOException {

        boolean flag = patVsUserFacade.findIsExistsPatInfo(openId);
        if (flag) {//绑定和患者
            response.sendRedirect("/views/his/public/app-dept-dict.html?param=" + openId);
        } else {
            response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openId);
        }
        return "";
    }

    /**
     * @param age
     * @param sexValue
     * @return
     * @throws java.io.IOException
     */
    @GET
    @Path("select-body")
    public String selectBody(@QueryParam("age") String age, @QueryParam("sexValue") String sexValue) throws IOException {
        if (sexValue == "1")//男性
            response.sendRedirect("/views/his/public/app-select-body.html?age=" + age + "&sexValue=" + sexValue);
        response.sendRedirect("/views/his/public/app-select-body-woman.html?age=" + age + "&sexValue=" + sexValue);
        return "";
    }


    @GET
    @Path("query-symptom")
    public String querySymptom(@QueryParam("bodyId") String bodyId,@QueryParam("openId") String openId,@QueryParam("sexValue") String sexValue) throws IOException {
        response.sendRedirect("/views/his/public/app-select-symptom.html?bodyId=" + bodyId + "&openId=" + openId+"&sexValue="+sexValue);
        return "";
    }


    @GET
    @Path("query-sickness")
    public String queerySickness(@QueryParam("ids") String ids, @QueryParam("openId") String openId,@QueryParam("sexValue") String sexValue) throws IOException {
        response.sendRedirect("/views/his/public/app-sickness-result.html?ids=" + ids + "&openId=" + openId+"&sexValue="+sexValue);
        return "";
    }

    /**
     * @param code
     * @return
     */
    @GET
    @Path("find-body")
    public String findBody(@QueryParam("code") String code) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
            //测试用
            SnsToken snsToken=getSnsToken(code);
            response.sendRedirect("/views/his/public/app-select-body.html?openId=" + snsToken.getOpenid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * app预约挂号选择科室
     *
     * @param code
     * @return
     */
    @GET
    @Path("find-dept-pre")
    public String findDeptPre(@QueryParam("code") String code, @QueryParam("openId") String openId) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
            //测试用
            SnsToken snsToken = null;
            String openIdStr = "";
            if (openId == null || openId == "") {
//                AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//                snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//                System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
                snsToken= getSnsToken(code);
                openIdStr = snsToken.getOpenid();
            } else {
                openIdStr = openId;
            }
            /**
             * 如果次微信用户有绑定的患者，则跳到挂号页面，否则跳到绑卡页面
             */
            boolean flag = patVsUserFacade.findIsExistsPatInfo(openIdStr);
            if (flag) {//绑定和患者
                response.sendRedirect("/views/his/public/app-dept-dict-pre.html?param=" + openIdStr);
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openIdStr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Path("query-string-pre")
    public String queryStringPre(@QueryParam("openId") String openId, @QueryParam("deptId") String deptId) throws IOException {
        response.sendRedirect("/views/his/public/app-doct-info-pre.html?openId=" + openId + "&deptId=" + deptId);
        return "";
    }

    /**
     * 就医反馈
     *
     * @param code
     * @return
     */
    @GET
    @Path("feed-back")
    public String feedBack(@QueryParam("code") String code) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
            //测试用
//            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//            System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
            SnsToken snsToken =getSnsToken(code);
            boolean flag = patVsUserFacade.findIsExistsPatInfo(snsToken.getOpenid());
            if (flag) {//绑定和患者
                response.sendRedirect("/views/his/public/app-feed-back.html?openId=" + snsToken.getOpenid());
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
// find-master?openId="+openId;

    @GET
    @Path("find-master")
    public String findmaster(@QueryParam("openId") String openId,@QueryParam("doctFlag") String doctFlag) {
        try {
             if(doctFlag!=null&&!"".equals(doctFlag)){
                 response.sendRedirect("/views/his/public/app-doct-record.html?openId=" + openId);
              }else{
                 response.sendRedirect("/views/his/public/app-master-record.html?openId=" + openId);
             }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @GET
    @Path("find-content")
    public String findContent(@QueryParam("openId") String openId, @QueryParam("feedTargetId") String feedTargetId) {
        try {
            response.sendRedirect("/views/his/public/app-feed-content.html?openId=" + openId + "&feedTargetId=" + feedTargetId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
//        @GET
    @Path("body-list")
    public String bodyList(@QueryParam("sexValue") String sexValue,@QueryParam("openId") String openId) {
        try {
           response.sendRedirect("/views/his/public/app-body-list.html?sexValue="+sexValue);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @GET
    @Path("my-master")
    public String myMaster(@QueryParam("code") String code) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);
//            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
//            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//            System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
            SnsToken snsToken= getSnsToken(code);
            boolean flag = patVsUserFacade.findIsExistsPatInfo(snsToken.getOpenid());
            if (flag) {//绑定了患者
                response.sendRedirect("/views/his/public/app-regist-list.html?openId=" + snsToken.getOpenid());
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 我的收藏
     * @param openId
     * @return
     */
    @GET
    @Path("find-my-collection")
    public String findMyCollection(@QueryParam("openId") String openId){
        try {
            response.sendRedirect("/views/his/public/app-my-collection.html?openId="+openId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Path("regist-list")
    public String registList(@QueryParam("openId") String openId) {
        try {
            // SnsToken snsToken = SnsAPI.oauth2AccessToken(APP_ID, APP_SERECT, code);

            boolean flag = patVsUserFacade.findIsExistsPatInfo(openId);
            if (flag) {//绑定了患者
                response.sendRedirect("/views/his/public/app-regist-list.html?openId=" +openId);
            } else {//没有绑定患者,跳转到用户绑定页面
                response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
//    window.location.href="/api/wx-service/query-doct-like?likeSearch="+likeSearch+"&openId="+openId;

//        　window.location.href="/api/wx-service/query-doct-info?id="+id;

    @GET
    @Path("query-doct-info")
    public String queryDoctInfo(@QueryParam("id") String id,@QueryParam("openId") String openId){
          try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.sendRedirect("/views/his/public/app-collection-doct-info.html?collectionId="+id+"&openId="+openId);
         } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

//
    @GET
    @Path("query-doct-like")
    public String likeSearch(@QueryParam("likeSearch") String likeSearch,@QueryParam("openId") String openId,@QueryParam("flag") String flag){
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            if("pre".equals(flag)){
                response.sendRedirect("/views/his/public/app-doct-info-pre.html?likeSearch="+ URLEncoder.encode(likeSearch,"UTF-8")+"&openId="+openId);
            }else{
                response.sendRedirect("/views/his/public/app-doct-info.html?likeSearch="+URLEncoder.encode(likeSearch,"UTF-8")+"&openId="+openId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取snsToken
     * @param code
     * @return
     */
    private SnsToken getSnsToken(String code){
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        return snsToken;
    }

//
}
