package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicTypeCharge;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ClinicTypeChargeFacade;

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
@Path("clinic-type-charge")
@Produces("application/json")
public class ClinicTypeChargeService {
    private ClinicTypeChargeFacade clinicTypeChargeFacade;

    @Inject
    public ClinicTypeChargeService(ClinicTypeChargeFacade clinicTypeChargeFacade) {
        this.clinicTypeChargeFacade = clinicTypeChargeFacade;
    }


    @GET
    @Path("list-all")
    public List<ClinicTypeCharge> listAll() {
        return clinicTypeChargeFacade.findAll(ClinicTypeCharge.class);
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
