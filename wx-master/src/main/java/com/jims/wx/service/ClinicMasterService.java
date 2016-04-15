package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicMaster;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicMasterFacade;

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
@Path("clinic-master")
@Produces("application/json")
public class ClinicMasterService {
    private ClinicMasterFacade clinicMasterFacade;

    @Inject
    public ClinicMasterService(ClinicMasterFacade clinicMasterFacade) {
        this.clinicMasterFacade = clinicMasterFacade;
    }


    @GET
    @Path("list-all")
    public List<ClinicMaster> listAll() {
        return clinicMasterFacade.findAll(ClinicMaster.class);
    }

    @POST
    @Path("save")
    public Response saveRequestMsg(List<ClinicMaster> updateList) {
        try {
            List<ClinicMaster> newUpdateDict = new ArrayList<>();
            if (updateList != null) {
                newUpdateDict = clinicMasterFacade.save(updateList);
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
