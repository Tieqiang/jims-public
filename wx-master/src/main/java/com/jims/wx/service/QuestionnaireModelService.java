package com.jims.wx.service;

import com.jims.wx.entity.QuestionnaireModel;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.QuestionnaireModelFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String,Object> delQuestionnaireModel(@QueryParam("modelId") String modelIds){
        Map<String,Object> map=new HashMap<String,Object>();
//        System.out.println(modelIds);
        try {
            questionnaireModelFacade.delQuestionnaireModel(modelIds);
            map.put("isSuccess",true);
            map.put("msg","删除成功！！");
        }catch(Exception e){
            map.put("isSuccess",false);
            map.put("msg","删除失败！");
            e.printStackTrace();
        }
         return map;
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
