package com.jims.wx.service;

import com.jims.wx.entity.ResponseMessage;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ResponseMessageFacade;
import com.jims.wx.util.XmlUtil;

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

    /**
     * 查询回复消息列表
     * @return
     */
    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ResponseMessage> listAll() {
        return responseMessageFacade.findAll(ResponseMessage.class);
    }

    /**
     * 回复消息（保存）
     * @param msg
     * @return
     */
    @POST
    @Path("reply")
    public Response save(ResponseMessage msg) {
        try {
            if (null != msg) {
                msg = responseMessageFacade.save(msg);
                //String xmlStr = XmlUtil.beanToXmlString(msg);
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

    /**
     * 根据ID删除msg
     * @param id
     * @return
     */
    @DELETE
    @Path("del/{id}")
    public Response delDeptDict(@PathParam("id") String id) {
        ResponseMessage msg = responseMessageFacade.deleteById(id);
        return Response.status(Response.Status.OK).entity(msg).build();
    }
}
