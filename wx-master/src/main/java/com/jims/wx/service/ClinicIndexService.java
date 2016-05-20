package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicIndexFacade;
import com.jims.wx.facade.ClinicTypeSettingFacade;
import com.jims.wx.facade.DoctInfoFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-index")
@Produces("application/json")
public class ClinicIndexService {
    private ClinicIndexFacade clinicIndexFacade;
    private DoctInfoFacade doctInfoFacade;
    private ClinicTypeSettingFacade clinicTypeSettingFacade;

    @Inject
    public ClinicIndexService(ClinicIndexFacade clinicIndexFacade) {
        this.clinicIndexFacade = clinicIndexFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<ClinicIndex> listAll() {
        return clinicIndexFacade.findAll(ClinicIndex.class);
    }

    @GET
    @Path("find-by-type-id")
    public List<ClinicIndex> findById(@QueryParam("typeId")String typeId){
        return clinicIndexFacade.findByTypeId(typeId);
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<ClinicIndex> beanChangeVo) {
        try {
            List<ClinicIndex> newUpdateDict = new ArrayList<>();
            newUpdateDict = clinicIndexFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("医生重复，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }
}

