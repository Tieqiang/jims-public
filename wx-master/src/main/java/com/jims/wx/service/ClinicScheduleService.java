package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicSchedule;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicScheduleFacade;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.ClinicTypeIndexVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-schedule")
@Produces("application/json")
public class ClinicScheduleService {
    private ClinicScheduleFacade clinicScheduleFacade;

    @Inject
    public ClinicScheduleService(ClinicScheduleFacade clinicScheduleFacade) {
        this.clinicScheduleFacade = clinicScheduleFacade;
    }
//根据id查询
    @GET
    @Path("find-by-id")
    public List<ClinicSchedule> findById(@QueryParam("id")String id){
        return clinicScheduleFacade.findByTypeId(id);
    }


//查询全部
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClinicSchedule> listAll() {
        return clinicScheduleFacade.findAll(ClinicSchedule.class);
    }
    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<ClinicSchedule> beanChangeVo) {
        try {
            List<ClinicSchedule> newUpdateDict = new ArrayList<>();
            newUpdateDict = clinicScheduleFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
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

    @GET
    @Path("list-tree")
    public List<ClinicTypeIndexVo> listTree(){
        return clinicScheduleFacade.listTree();
    }

}
