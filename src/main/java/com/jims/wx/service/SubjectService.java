package com.jims.wx.service;

import com.jims.wx.entity.Subject;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.SubjectFacade;
import com.jims.wx.vo.BeanChangeVo;


import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
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
     * 查询全部
     *
     * @param name
     * @return
     */
    @GET
    @Path("list")
    public List<Subject> expExpCodingRule(@QueryParam("name") String name){
        return subjectFacade.findAll(name);
    }

    /**
     * 保存增改
     *
     * @param sub
     * @return
     */
    @POST
    @Path("save")
    public Response save(Subject sub) {
        try {
           sub = subjectFacade.save(sub);
            return Response.status(Response.Status.OK).entity(sub).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }
    /**
     * 删除
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("delete")
    public Response delete(BeanChangeVo<Subject> beanChangeVo) {
        try {
            List<Subject> newUpdateDict = new ArrayList<>();
            newUpdateDict = subjectFacade.delete(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            e.printStackTrace();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
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





}
