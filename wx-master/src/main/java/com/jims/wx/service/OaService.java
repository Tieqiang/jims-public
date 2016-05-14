package com.jims.wx.service;

import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.facade.HospitalStaffFacade;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.support.TokenManager;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.List;

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
    @POST
    public Response sendOaMessage(@QueryParam("id") String id ,@QueryParam("message") String message) throws IOException {
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
        System.out.print(s);
        String state = "ok";
        for(int i=0;i<openId.length;i++) {
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
}
