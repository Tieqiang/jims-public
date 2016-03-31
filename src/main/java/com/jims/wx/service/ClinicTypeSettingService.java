package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicTypeSettingFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-type-setting")
@Produces("application/json")
public class ClinicTypeSettingService {
    private ClinicTypeSettingFacade clinicTypeSettingFacade ;

    @Inject
    public ClinicTypeSettingService( ClinicTypeSettingFacade clinicTypeSettingFacade) {
        this.clinicTypeSettingFacade =clinicTypeSettingFacade;
    }
    //根据id查询
    @GET
    @Path("find-By-Id")
    public List<ClinicTypeSetting> findById(@QueryParam("id")String id){
        return clinicTypeSettingFacade.findById(id);
    }


    //查询全部
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ClinicTypeSetting> listAll() {
        return clinicTypeSettingFacade.findAll(ClinicTypeSetting.class);
    }
    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<ClinicTypeSetting> beanChangeVo) {
        try {
            List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
            newUpdateDict = clinicTypeSettingFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else if (errorException.getErrorMessage().toString().indexOf("违反完整约束") != -1) {
                errorException.setErrorMessage("数据存在关联数据，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }


}
