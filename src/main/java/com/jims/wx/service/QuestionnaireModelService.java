package com.jims.wx.service;

import com.jims.wx.entity.QuestionnaireModel;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.QuestionnaireModelFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 2016/3/17.
 */
@Path("questionnaire-model")
@Produces("application/json")
public class QuestionnaireModelService {
    private QuestionnaireModelFacade questionnaireModelFacade;

    @Inject
    public QuestionnaireModelService(QuestionnaireModelFacade questionnaireModelFacade){
        this.questionnaireModelFacade=questionnaireModelFacade;
    }

    //listAll
    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<QuestionnaireModel> listAll(){
        return questionnaireModelFacade.findAll(QuestionnaireModel.class);
    }

    //条件查询
//    @GET
//    @Path("find-by-id")
//    public QuestionnaireModel findById(@QueryParam("id") String id){
//        return questionnaireModelFacade.getById(id);
//    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save (BeanChangeVo<QuestionnaireModel> beanChangeVo){
        try {
            List<QuestionnaireModel> newUpdateModel = new ArrayList<>();
            newUpdateModel = questionnaireModelFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateModel).build();

        }catch (Exception e) {
            ErrorException errorException = new ErrorException();
            e.printStackTrace();
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

    @POST
    @Path("add")
    public Response save(QuestionnaireModel questionnaireModel) {
        try {
            if (null != questionnaireModel) {
                questionnaireModel = questionnaireModelFacade.save(questionnaireModel);
            }
            return Response.status(Response.Status.OK).entity(questionnaireModel).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            e.printStackTrace();
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

    @POST
    @Path("del")
    public void delQuestionnaireModel(@QueryParam("modelId") String modelIds){
        System.out.println(modelIds);
        questionnaireModelFacade.delQuestionnaireModel(modelIds);
    }

    /**
     * 根据ID查询问题
     * @param id
     * @return
     */
    @GET
    @Path("find-by-id")
    public QuestionnaireModel findById(@QueryParam("id") String id){
        return questionnaireModelFacade.getById(id);
    }
}
