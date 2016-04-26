package com.jims.wx.service;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Created by heren on 2016/4/26.
 */
@Produces("application/json")
@Path("oa-service")
public class OaService {


    @Path("send-message")
    @POST
    public Response sendOaMessage(String personId,String message){
        //根据personId获取 openId
        //第二步获取 公众号的ID
        //第三步 创建XMLMessage
        //第四步通过XMLmessage对象发送消息。
        return Response.status(Response.Status.OK).build() ;
    }


}
