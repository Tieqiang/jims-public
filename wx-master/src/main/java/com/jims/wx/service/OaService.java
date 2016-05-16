package com.jims.wx.service;

import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.facade.HospitalStaffFacade;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.support.TokenManager;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 给某个用户
 * Created by heren on 2016/4/26.
 */
@Produces("application/json")
@Path("oa-service")
public class OaService {
    private HospitalStaffFacade hospitalStaffFacade;

    @Inject
    public OaService(HospitalStaffFacade hospitalStaffFacade){
        this.hospitalStaffFacade=hospitalStaffFacade;
    }

    @Path("send-message")
    @GET
    public Response sendOaMessage(@QueryParam("id") String id ,@QueryParam("message") String message,@QueryParam("code") String code) throws IOException {
        //根据personId获取 openId
        String[] idsArray = id.split(",");
        System.out.println(idsArray.length);
        String [] openId = new String[idsArray.length];

        if(idsArray.length>0) {
            for(int i=0;i<idsArray.length;i++) {
                List<HospitalStaff> staffList = hospitalStaffFacade.getById(idsArray[i]);
                if (staffList.size() > 0) {
                    openId[i] = staffList.get(0).getOpenId();
                }
            }
        }

        //第二步获取 公众号的ACCESSTOOKEN
        String accessToken =TokenManager.getDefaultToken() ;

        //第三步 拼写json发送消息
        String s = new String(message.getBytes("GB2312"),"UTF-8");
//        System.out.print(s);
        String state = "ok";
        for(int i=0;i<openId.length;i++) {
//            http://oa_url/main/openmsgsj.asp?id={id}&code={code}
//            message=message+"http://oa_url/main/openmsgsj.asp?id={"+idsArray[i]+"}&code={"+code+"}";
//            message=message.replace("id=","");
//            您有新的流程待办单，信息如下：
//            编码[]
//            安保人员工资审核表：物业经理审批
//            时间：[2016/5/12 17:25:30]
//            发起人：[admin]
//            请及时登录OA进行处理


            String target="";
            String target2="";
//            String regex2="code=(.+)";
            String regex="id=(.+)";
            Pattern pattern=Pattern.compile(regex);
            Matcher matcher=pattern.matcher(message);
            while(matcher.find()){
                 target=matcher.group();
            }
            String result="id={"+ openId[i]+"}";
            message=message.replace(target,result);
            String jsonMessage = "{\"touser\":\"" + openId[i] + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + message + "\"}} ";
            BaseResult aa = MessageAPI.messageCustomSend(accessToken, jsonMessage);
            if(!aa.getErrmsg().equals("ok")){
                state ="error";
            }
        }
        if(state.equals("ok")){
            return Response.status(Response.Status.OK).entity(state).build() ;
        }else {
            return null;
        }
    }


//    public static void main(String[] args){
////        id={1231231231}&code={code}
//        String message=" http://oa_url/main/openmsgsj.asp?id={1231231231}&code={code}";
//        String regex="id=(.+)";
//        String regex2="code=(.+)";
//        Pattern pattern=Pattern.compile(regex2);
//        Matcher matcher=pattern.matcher(message);
//        while(matcher.find()){
//            String str=matcher.group();
//            System.out.println(str);
//
//        }
//    }
}
