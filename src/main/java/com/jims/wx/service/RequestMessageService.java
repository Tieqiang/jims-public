package com.jims.wx.service;

import com.jims.wx.entity.RequestMessage;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.RequestMessageFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/8.
 */
@Path("req-msg")
@Produces("application/json")
public class RequestMessageService {

    private RequestMessageFacade requestMessageFacade;

    @Inject
    public RequestMessageService(RequestMessageFacade requestMessageFacade) {
        this.requestMessageFacade  = requestMessageFacade ;
    }

    /**
     * 查询接收消息列表
     * @return
     */
    @GET
    @Path("list-all")
    public List<RequestMessage> listAll() {
        return requestMessageFacade.findAll(RequestMessage.class);
    }


    @POST
    @Path("add")
    public Response saveRequestMsg(RequestMessage msg) {
        try {
            if(msg != null){
                msg = requestMessageFacade.save(msg);
            }
            return Response.status(Response.Status.OK).entity(msg).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，提交失败！");
            } else {
                errorException.setErrorMessage("提交失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }
}
