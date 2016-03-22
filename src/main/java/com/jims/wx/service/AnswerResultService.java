package com.jims.wx.service;

import com.jims.wx.entity.AnswerResult;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AnswerResultFacade;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by zhu on 2016/3/16.
 */
@Path("answer-result")
@Produces("application/json")
public class AnswerResultService {
    private AnswerResultFacade answerResultFacade;

    @Inject
    public AnswerResultService(AnswerResultFacade answerResultFacade){
        this.answerResultFacade=answerResultFacade;
    }

    //查询全部
    @GET
    @Path("list-all")
    public List<AnswerResult> listAll(){
        return answerResultFacade.findAll(AnswerResult.class);
    }

    @POST
    @Path("add")
    public Response save(AnswerResult answerResult) {
        try {
            if (null != answerResult) {
                answerResult = answerResultFacade.save(answerResult);
            }
            return Response.status(Response.Status.OK).entity(answerResult).build();
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
