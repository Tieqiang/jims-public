package com.jims.wx.service;

import com.jims.wx.entity.AnswerSheet;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AnswerSheetFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
/**
 * Created by zhu on 2016/3/16.
 */
@Path("answer-sheet")
@Produces("application/json")
public class AnswerSheetService {
    private AnswerSheetFacade answerSheetFacade;

    @Inject
    public AnswerSheetService(AnswerSheetFacade answerSheetFacade){
        this.answerSheetFacade=answerSheetFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<AnswerSheet> listAll() {
        return answerSheetFacade.findAll(AnswerSheet.class);
    }

    //增加数据
    @POST
    @Path("add")
    public Response save(AnswerSheet answerSheet) {
        try {
            if (null != answerSheet) {
                answerSheet.setCreateTime(new Date());
                answerSheet = answerSheetFacade.save(answerSheet);
            }
            return Response.status(Response.Status.OK).entity(answerSheet).build();
        }catch (Exception e) {
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
