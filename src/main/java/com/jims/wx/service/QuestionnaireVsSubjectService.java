package com.jims.wx.service;

import com.jims.wx.entity.QuestionnaireVsSubject;
import com.jims.wx.facade.QuestionnaireVsSubjectFacade;
import com.jims.wx.expection.ErrorException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by zhu on 2016/3/18.
 */
@Path("questionnaire-vs-subject")
@Produces("application/json")
public class QuestionnaireVsSubjectService {
    private QuestionnaireVsSubjectFacade questionnaireVsSubjectFacade;

    @Inject
    public QuestionnaireVsSubjectService(QuestionnaireVsSubjectFacade questionnaireVsSubjectFacade){
        this.questionnaireVsSubjectFacade=questionnaireVsSubjectFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<QuestionnaireVsSubject> listAll(){
        return questionnaireVsSubjectFacade.findAll(QuestionnaireVsSubject.class);
    }

    //新增多条记录
    @POST
    @Path("add-many")
    public Response save (QuestionnaireVsSubject questionnaireVsSubject){
        try {
            if(null!=questionnaireVsSubject){
                questionnaireVsSubject=questionnaireVsSubjectFacade.save(questionnaireVsSubject);
            }
            return Response.status(Response.Status.OK).entity(questionnaireVsSubject).build();
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
