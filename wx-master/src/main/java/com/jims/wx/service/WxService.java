package com.jims.wx.service;


import com.jims.wx.entity.AppUser;

import com.jims.wx.facade.*;
import com.jims.wx.util.WeiXinPayUtils;
import com.jims.wx.vo.AppSetVo;
import org.springframework.web.bind.annotation.RequestBody;
import weixin.popular.api.SnsAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.sns.SnsToken;
import weixin.popular.bean.user.User;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.ExpireKey;
import weixin.popular.support.TokenManager;
import weixin.popular.support.expirekey.DefaultExpireKey;
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
    private static final String MCH_ID = "1318000301";//微信支付商号
    private static final String KEY = "jmyruanjianyouxiangongsi84923632";//API密钥 or 商户支付密钥
    private static final String APP_ID = "wxef5d38d8d6af065e";//商户的APP_ID
    private static final String APP_SERECT = "ace48490b06a1415a03a98c73b6252f5";
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
//        System.out.println(signature);
//        System.out.println(timestamp);
//        System.out.println(nonce);
//        System.out.println(echostr);
//        System.out.println(request.toString());
//        System.out.println(response.toString());
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
//            System.out.println(eventMessage.getContent());
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
                if(user==null){
                    throw new IllegalArgumentException("参数非法！user="+user);
                }
                AppUser appUser = appUserFacade.createUser(user);
                if (appUser != null && !"".equals(appUser)) {//关注成功
                    String message = "欢迎您关注我们，我们将以“母亲安全 、儿童健康”为神圣使命，全天候为您和宝宝保驾护航，24小时免费接诊电话：0314-8585407，腾讯微博：滦平县妇幼保健院，官网：www.lpfy.cn，联系方式 联系电话：0314-8589760 \n" +
                            "24小时免费接诊电话：0314-8585407  \n" +
                            "孕妇学校咨询电话：0314-8586813\n" +
                            "体检咨询电话：0314-8586803\n" +
                            "宝宝游泳电话：0314-8586856 ";

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
                if (fromUser==null || "".equals(fromUser)){
                    throw new IllegalArgumentException("openid 为空！");
                }
                AppUser appUser = appUserFacade.findAppUserByOpenId(fromUser);
                if (appUser != null) {
                    appUserFacade.deleteByObject(appUser);
                }else{
                    throw new IllegalArgumentException("删除appUser失败！appUser 为空！");
                }
                return;
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
        if(snsToken.getOpenid()==null || "".equals(snsToken.getOpenid())){
            throw new IllegalArgumentException("系统繁忙,请重试！snsToken.getOpenId()="+null);
        }
        AppUser appUser = appUserFacade.findAppUserByOpenId(snsToken.getOpenid());
        String patId = "";
        if (appUser!=null) {
            patId = appUser.getPatId();
        }
        response.sendRedirect("/views/his/public/questionnaire-survey.html?openId=" + snsToken.getOpenid() + "&patId=" + patId);
        return "http://www.baidu.com/";
    }

    @GET
    @Path("pat-visit")
    public String inp(@QueryParam("code") String code) throws IOException {
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        response.sendRedirect("/views/his/public/Pat-Visit.html?openId=" + snsToken.getOpenid());
        return "http://www.baidu.com/";
    }

    @GET
    @Path("user-bangker")
    public String userBangker(@QueryParam("code") String code) {
         //测试用
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        try {
            if(snsToken.getOpenid()==null || "".equals(snsToken.getOpenid())){
                throw new IllegalArgumentException("系统繁忙，snsToken.getOpenid()="+snsToken.getOpenid());
            }
            response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + snsToken.getOpenid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * app当天挂号选择科室
     * @param code
     * @return
     */
    @GET
    @Path("find-dept")
    public String findDept(@QueryParam("code") String code, @QueryParam("openId") String openId) {
        try {
            String openIdStr = "";
            SnsToken snsToken = null;
            if (openId == null || openId == "") {
                AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
                snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
                openIdStr = snsToken.getOpenid();
            } else {
                openIdStr = openId;
            }
            if(openIdStr==null || "".equals(openIdStr)){
                throw new IllegalArgumentException("参数违法，openId 为空!");
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
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数异常！ 非法的openId="+openId);
        }
        json = WeiXinPayUtils.weiXinPayNeedJson(price, openId, body, notifyUrl, ip);
        return json;
    }

    @GET
    @Path("rcpt-list")
    public String rcpt(@QueryParam("code") String code) throws IOException {
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        AppUser  appUser = appUserFacade.findAppUserByOpenId(snsToken.getOpenid());
        String patId = "";
        if (appUser!=null) {
            patId = appUser.getPatId();
        }
        response.sendRedirect("/views/his/public/rcpt-master.html?openId=" + snsToken.getOpenid());
        return "http://www.baidu.com/";
    }
//    @POST
//    @Path("pay-jsp")
//    public String payJs2() {
//        PayPackage packageParams = new PayPackage();
//        packageParams.setBank_type("WX");
//        packageParams.setBody("挂号");
//        packageParams.setFee_type("CHM");
//        packageParams.setInput_charset("UTF-8");
//        packageParams.setNotify_url("http://9tvafbgbdf.proxy.qqbrowser.cc/views/his/public/app-pay-success.html");
//        packageParams.setOut_trade_no("1");
//        packageParams.setPartner(MCH_ID);
//        packageParams.setSpbill_create_ip(request.getRemoteHost());
//        packageParams.setTotal_fee("10");
//        String json = PayUtil.generatePayJsRequestJson(packageParams, "wx890edf605415aaec", KEY, KEY);
//        return json;
//    }

    @GET
    @Path("find-pat-info")
    public String findPatInfo(@QueryParam("code") String code) throws IOException {
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
        SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
        if(snsToken.getOpenid()==null || "".equals(snsToken.getOpenid())){
            throw new IllegalArgumentException("系统繁忙，请稍后，openId="+null);
        }
        response.sendRedirect("/views/his/public/app-my-information.html?openId=" + snsToken.getOpenid());
        return "";
    }

    // window.location.href="/views/his/public/app-doct-info.html?deptId="+deptId+"&openId="+openId;
    @GET
    @Path("query-string")
    public String queryString(@QueryParam("openId") String openId, @QueryParam("deptId") String deptId) throws IOException {
        if(openId==null || "".equals(openId) || deptId==null || "".equals(deptId)){
            throw new IllegalArgumentException("参数非法,openId="+openId+"&deptId="+deptId);
        }
        response.sendRedirect("/views/his/public/app-doct-info.html?openId=" + openId + "&deptId=" + deptId);
        return "";
    }

    @GET
    @Path("app-pay")
    public String appPay(@QueryParam("openId") String openId, @QueryParam("clinicForRegistId") String clinicForRegistId, @QueryParam("price") String price) throws IOException {
        if(openId==null || "".equals(openId) || clinicForRegistId==null || "".equals(clinicForRegistId)){
            throw new IllegalArgumentException("参数非法,openId="+openId+"&clinicForRegistId="+clinicForRegistId);
        }
        response.sendRedirect("/views/his/public/app-pay.html?openId=" + openId + "&price=" + price + "&clinicForRegistId=" + clinicForRegistId);
        return "";
    }

    @GET
    @Path("get-param")
    public String getParam(@QueryParam("patId") String patId,@QueryParam("openId") String openId) throws IOException {
        if(patId==null || "".equals(patId)){
            throw new IllegalArgumentException("参数非法,patId="+patId);
        }
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数非法,openId="+openId);
        }
        response.sendRedirect("/views/his/public/app-pat-info.html?patId=" + patId+"&openId="+openId);
        return "";
    }


    @GET
    @Path("get-regist-id")
    public String getRegistId(@QueryParam("clinicForRegistId") String clinicForRegistId) throws IOException {
        if(clinicForRegistId==null || "".equals(clinicForRegistId)){
            throw new IllegalArgumentException("参数非法,clinicForRegistId="+clinicForRegistId);
        }
        response.sendRedirect("/views/his/public/app-regist-doct-info.html?clinicForRegistId=" + clinicForRegistId);
        return "";
    }

    @GET
    @Path("find-by-id")
    public String findById(@QueryParam("patId") String patId, @QueryParam("openId") String openId, @QueryParam("mid") String mid) throws IOException {
        if(patId==null || "".equals(patId) || openId==null || "".equals(openId) || mid==null || "".equals(mid)){
            throw new IllegalArgumentException("参数非法,patId="+patId+"&openId="+openId+"&mid="+mid);
        }
        response.sendRedirect("/views/his/public/app-pat-info.html?patId=" + patId + "&openId=" + openId + "&mid=" + mid);
        return "";
    }


    @GET
    @Path("query-dept")
    public String queryDept(@QueryParam("deptId") String deptId) throws IOException {
        if(deptId==null || "".equals(deptId)){
            throw new IllegalArgumentException("参数为空！deptId="+deptId);
        }
        response.sendRedirect("/views/his/public/app-dept-info.html?deptId=" + deptId);
        return "";
    }

    @GET
    @Path("change")
    public String change(@QueryParam("openId") String openId) throws IOException {
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数为空！openId="+openId);
        }
        response.sendRedirect("/views/his/public/app-my-information.html?openId=" + openId);
        return "";
    }

    //    query-open
    @GET
    @Path("query-open")
    public String queryOpen(@QueryParam("openId") String openId) throws IOException {
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数为空！openId="+openId);
        }
        response.sendRedirect("/views/his/public/app-user-bangker.html?param=" + openId);
        return "";
    }

    @GET
    @Path("regist-open")
    public String registOpen(@QueryParam("openId") String openId) throws IOException {
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数为空！openId="+openId);
        }
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
     * @throws IOException
     */
    @GET
    @Path("select-body")
    public String selectBody(@QueryParam("age") String age, @QueryParam("sexValue") String sexValue) throws IOException {
        if(sexValue==null || "".equals(sexValue) || age==null || "".equals(age)){
            throw new IllegalArgumentException("参数为空！sexValue="+sexValue+"&age="+age);
        }
        if (sexValue == "1")//男性
            response.sendRedirect("/views/his/public/app-select-body.html?age=" + age + "&sexValue=" + sexValue);
        response.sendRedirect("/views/his/public/app-select-body-woman.html?age=" + age + "&sexValue=" + sexValue);
        return "";
    }


    @GET
    @Path("query-symptom")
    public String querySymptom(@QueryParam("bodyId") String bodyId,@QueryParam("openId") String openId,@QueryParam("sexValue") String sexValue) throws IOException {
        if(sexValue==null || "".equals(sexValue) || bodyId==null || "".equals(bodyId) || openId==null || "".equals(openId)){
            throw new IllegalArgumentException("参数为空！sexValue="+sexValue+"&bodyId="+bodyId+"&openId="+openId);
        }
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
            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
//            System.out.println("snsToken.getOpenid()=" + snsToken.getOpenid());
            if(snsToken==null){
                throw new IllegalArgumentException("snsToken非法 ，snsToken 为空！");
            }
            if(snsToken.getOpenid()==null){
                throw new IllegalArgumentException("openId非法 ，openId 为空！");
            }
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
            SnsToken snsToken = null;
            String openIdStr = "";
            if (openId == null || "".equals(openId)) {
                AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
                snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
                 openIdStr = snsToken.getOpenid();
            } else {
                openIdStr = openId;
            }
            if(openIdStr==null || "".equals(openIdStr)){
                throw new IllegalArgumentException("openIdStr 为空!");
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
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("openId 为空！");
        }
        if(deptId==null || "".equals(deptId)){
            throw new IllegalArgumentException("deptId 为空！");
        }
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
            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
            if(snsToken.getOpenid()==null || "".equals(snsToken.getOpenid())){
                throw new IllegalArgumentException("系统繁忙,snsToken.getOpenId()="+snsToken.getOpenid());
            }
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
    public String findmaster(@QueryParam("openId") String openId) {
        try {
            if(openId==null || "".equals(openId)){
                throw new IllegalArgumentException("openId 为空");
            }
            response.sendRedirect("/views/his/public/app-master-record.html?openId=" + openId);
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
            AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo();
            SnsToken snsToken = SnsAPI.oauth2AccessToken(appSetVo.getAppId(), appSetVo.getAppSecret(), code);
            if(snsToken.getOpenid()==null || "".equals(snsToken.getOpenid())){
                throw new IllegalArgumentException("系统繁忙,snsToken.getOpenId()="+snsToken.getOpenid());
            }
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
     *
     * @param openId
     * @return
     */
    @GET
    @Path("query-doct-like")
    public String queryDoctLike(@QueryParam("openId") String openId,@QueryParam("likeSearch") String likeSearch,@QueryParam("flag") String flag){
        try {
            if(openId==null || "".equals(openId)){
                throw new IllegalArgumentException("openId为空！");
            }
            if(flag!=null&&!"".equals(flag)&&flag.equalsIgnoreCase("pre")){
                response.sendRedirect("/views/his/public/app-doct-info-pre.html?openId="+openId+"&likeSearch="+ URLEncoder.encode(likeSearch));
            }else{
                response.sendRedirect("/views/his/public/app-doct-info.html?openId="+openId+"&likeSearch="+ URLEncoder.encode(likeSearch));
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
            if(openId==null || "".equals(openId)){
                throw new IllegalArgumentException("openId为空！");
            }
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
            if(openId==null || "".equals(openId)){
                throw new IllegalArgumentException("openId为空！");
            }
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




    @GET
    @Path("query-doct-info")
    public String queryDoctInfo(@QueryParam("id") String id,@QueryParam("openId") String openId){
        try {
            if(openId!=null&&!"".equals(openId)){
                response.sendRedirect("/views/his/public/app-doct-info.html?openId="+openId);
            }else{
                throw new IllegalArgumentException("openId 为空！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
