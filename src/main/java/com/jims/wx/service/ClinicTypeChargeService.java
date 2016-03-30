package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicTypeCharge;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicTypeChargeFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-type-charge")
@Produces("application/json")
public class ClinicTypeChargeService {
    private ClinicTypeChargeFacade clinicTypeChargeFacade;

    @Inject
    public ClinicTypeChargeService(ClinicTypeChargeFacade clinicTypeChargeFacade) {
        this.clinicTypeChargeFacade = clinicTypeChargeFacade;
    }

    /**
     * 查询所有号类
     * @return
     */
    @GET
    @Path("findAllClinicType")
    public List<ClinicTypeSetting> findAllClinicType(){
        return clinicTypeChargeFacade.findAll(ClinicTypeSetting.class);
    }

    /**
     * 根据号类ID查询该号类下属所有收费列表
     * @param id
     * @return
     */
    @GET
    @Path("findById")
    public List<ClinicTypeCharge> findById(@QueryParam("id")String id){
        return clinicTypeChargeFacade.findById(id);
    }

    /**
     * 保存增删改
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<ClinicTypeCharge> beanChangeVo){
        try {
            List<ClinicTypeCharge> clinicTypeCharges = new ArrayList<>();
            clinicTypeCharges = clinicTypeChargeFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(clinicTypeCharges).build();
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

    @POST
    @Path("save")
    public Response saveRequestMsg(List<ClinicTypeCharge> updateList) {
        try {
            List<ClinicTypeCharge> newUpdateDict = new ArrayList<>();
            if (updateList != null) {
                newUpdateDict = clinicTypeChargeFacade.save(updateList);
            }
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
        } catch (Exception e) {
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
