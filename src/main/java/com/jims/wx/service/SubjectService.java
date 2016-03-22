package com.jims.wx.service;

import com.jims.wx.entity.Subject;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.SubjectFacade;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by wei on 2016/3/16.
 */
@Path("subject")
@Produces("application/json")
public class SubjectService {

    private SubjectFacade subjectFacade;

    @Inject
    public SubjectService (SubjectFacade subjectFacade){
            this.subjectFacade=subjectFacade;
    }

    /**
     * 查询问题列表
     * @return
     */
    @GET
    @Path("list-sub")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Subject> listAll(){
        return subjectFacade.findAll(Subject.class);
    }

    /**
     * 保存问题
     * @param sub
     * @return
     */
    @POST
    @Path("save-sub")
    public Response save(Subject sub){
        try {
            if (null != sub) {
                subjectFacade.save(sub);
            }
            return Response.status(Response.Status.OK).entity(sub).build();
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

    /**
     * 根据ID查询问题
     * @param id
     * @return
     */
    @GET
    @Path("find-by-id")
    public Subject findById(@QueryParam("id") String id){
        return subjectFacade.getById(id);
    }

    /**
     * 根据id删除问题
     * @param ids
     */
    @POST
    @Path("del-subjects")
    public void delSubjects(@QueryParam("ids") String ids){
        subjectFacade.delSubjects(ids);
    }
}
