package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.HospitalStaff;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.HospitalStaffFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wei on 2016/4/26.
 */
@Path("hospital-staff-service")
@Produces("application/json")
public class HospitalStaffService {

    private HospitalStaffFacade hospitalStaffFacade;
    @Inject
    public HospitalStaffService(HospitalStaffFacade hospitalStaffFacade) {
        this.hospitalStaffFacade = hospitalStaffFacade;
    }

    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalStaff> listAll() {
        return hospitalStaffFacade.findAll(HospitalStaff.class);
    }

    @GET
    @Path("find-by-personId")
    public Map<String,Object> findById(@QueryParam("personId")String personId,@QueryParam("openId")String openId){
        Map<String,Object> map=new HashMap<String,Object>();
        map=hospitalStaffFacade.findById(personId,openId);
        return map;
    }

    @GET
    @Path("find-by-open-id")
    public List<HospitalStaff> findByOpenId(@QueryParam("openId")String openId){
        return hospitalStaffFacade.findByOpenId(openId);
    }


    @POST
    @Path("save")
    public Response save(HospitalStaff hospitalStaff) {
        try {
            hospitalStaff = hospitalStaffFacade.save(hospitalStaff);
            return Response.status(Response.Status.OK).entity(hospitalStaff).build();
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
    @Path("merge")
    public Response save(BeanChangeVo<HospitalStaff> beanChangeVo) {
        try {
            List<HospitalStaff> newUpdateDict = new ArrayList<>();
            newUpdateDict = hospitalStaffFacade.savePc(beanChangeVo);
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
