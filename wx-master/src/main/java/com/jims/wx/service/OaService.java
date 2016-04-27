package com.jims.wx.service;

import com.jims.wx.entity.AccessTooken;
import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.HospitalInfoFacade;
import com.jims.wx.facade.HospitalStaffFacade;
import com.jims.wx.vo.AppSetVo;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.support.TokenManager;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    private AppUserFacade appUserFacade ;
    private HospitalInfoFacade hospitalInfoFacade;
    private HospitalStaffFacade hospitalStaffFacade;

    @Inject
    public OaService(AppUserFacade appUserFacade,HospitalInfoFacade hospitalInfoFacade,HospitalStaffFacade hospitalStaffFacade){
        this.appUserFacade=appUserFacade;
        this.hospitalInfoFacade=hospitalInfoFacade;
        this.hospitalStaffFacade=hospitalStaffFacade;
    }

    @Path("send-message")
    @POST
    public Response sendOaMessage(String personId ,String message) throws IOException {
        //根据personId获取 openId

        List<HospitalStaff> staffList=hospitalStaffFacade.findById(personId);
        String openId="";
        if(staffList.size()>0){
            openId=staffList.get(0).getOpenId();
        }

        //第二步获取 公众号的ACCESSTOOKEN
        String accessToken =TokenManager.getDefaultToken() ;

        //第三步 拼写json发送消息
        if(openId != "") {
            String jsonMessage="{\"touser\":\""+openId+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+message+"\"}} ";
            BaseResult a=MessageAPI.messageCustomSend(accessToken, jsonMessage);
        }
        return Response.status(Response.Status.OK).build() ;
    }

}
