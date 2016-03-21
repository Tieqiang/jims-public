package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicTypeSettingFacade;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-type-setting")
@Produces("application/json")
public class ClinicTypeSettingService {
    private ClinicTypeSettingFacade clinicTypeSettingFacade;

    @Inject
    public ClinicTypeSettingService(ClinicTypeSettingFacade clinicTypeSettingFacade) {
        this.clinicTypeSettingFacade = clinicTypeSettingFacade;
    }


    @GET
    @Path("list-all")
    public List<ClinicTypeSetting> listAll() {
        return clinicTypeSettingFacade.findAll(ClinicTypeSetting.class);
    }

    @POST
    @Path("save")
    public Response saveRequestMsg(List<ClinicTypeSetting> updateList) {
        try {
            List<ClinicTypeSetting> newUpdateDict = new ArrayList<>();
            if (updateList != null) {
                newUpdateDict = clinicTypeSettingFacade.save(updateList);
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
