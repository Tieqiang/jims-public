package com.jims.wx.service;

import com.jims.wx.entity.AnswerSheet;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AnswerSheetFacade;
import com.jims.wx.vo.AnswerResultVo;
import com.jims.wx.vo.AnswerSheetVo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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

    /**
     * 根据modelId查询答题记录
     * @param modelId
     * @return
     */
    @GET
    @Path("find-by-model-id")
    public List<AnswerSheet> findByModelId(@QueryParam("modelId") String modelId){
        return answerSheetFacade.findByModelId(modelId);
    }

    /**
     * 根据sheetId查询某用户填写某个问卷的答案
     * @param id
     * @return
     */
    @GET
    @Path("find-by-id")
    public List<AnswerResultVo> findById(@QueryParam("id") String id){
        return answerSheetFacade.findById(id);
    }

     /**
     * 保存用户答题
     */
    @POST
    @Path("add")
    public Response saveAll(AnswerSheetVo answerSheetVo) {
        try{
            if(null!=answerSheetVo) {
                answerSheetVo = answerSheetFacade.saveAll(answerSheetVo);
            }
            return Response.status(Response.Status.OK).entity(answerSheetVo).build();
        }catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else if (errorException.getErrorMessage().toString().indexOf("违反完整约束") != -1){
                errorException.setErrorMessage("数据存在关联数据，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }
}
