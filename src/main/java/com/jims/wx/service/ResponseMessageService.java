package com.jims.wx.service;

import com.jims.wx.entity.ResponseMessage;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ResponseMessageFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by wangjing on 2016/3/8.
 */
@Path("resp-msg")
@Produces("application/json")
public class ResponseMessageService {

    private ResponseMessageFacade responseMessageFacade;

    @Inject
    public ResponseMessageService(ResponseMessageFacade responseMessageFacade) {
        this.responseMessageFacade = responseMessageFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ResponseMessage> listAll() {
        return responseMessageFacade.findAll();
    }


    @POST
    @Path("reply")
    public Response save(ResponseMessage msg) {

        try {
            if (null != msg) {
                msg = responseMessageFacade.save(msg);
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
