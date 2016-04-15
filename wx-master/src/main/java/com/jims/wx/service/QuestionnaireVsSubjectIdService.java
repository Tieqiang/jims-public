package com.jims.wx.service;

import com.jims.wx.entity.QuestionnaireVsSubjectId;

import com.jims.wx.facade.QuestionnaireVsSubjectIdFacade;

import com.jims.wx.expection.ErrorException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
/**
 * Created by zhu on 2016/3/21.
 */
@Path("questionnaire-vs-subjectid")
@Produces("application/json")
public class QuestionnaireVsSubjectIdService {
    private QuestionnaireVsSubjectIdFacade questionnaireVsSubjectIdFacade;

    @Inject
    public QuestionnaireVsSubjectIdService(QuestionnaireVsSubjectIdFacade questionnaireVsSubjectIdFacade){
        this.questionnaireVsSubjectIdFacade=questionnaireVsSubjectIdFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<QuestionnaireVsSubjectId> listAll(){
        return questionnaireVsSubjectIdFacade.findAll(QuestionnaireVsSubjectId.class);
    }

    //新增多条记录
    @POST
    @Path("add-many")
    public Response save (QuestionnaireVsSubjectId questionnaireVsSubjectId){
        try {
            if(null!=questionnaireVsSubjectId){
                questionnaireVsSubjectId=questionnaireVsSubjectIdFacade.save(questionnaireVsSubjectId);
            }
            return Response.status(Response.Status.OK).entity(questionnaireVsSubjectId).build();
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
