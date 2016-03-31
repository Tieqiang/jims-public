package com.jims.wx.service;

import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.HospitalInfoFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dt on 2016/3/4.
 */
@Path("hospital-info")
public class HospitalInfoService {

    private HospitalInfoFacade hospitalInfoFacade;

    @Inject
    public HospitalInfoService(HospitalInfoFacade hospitalInfoFacade){
        this.hospitalInfoFacade = hospitalInfoFacade;
    }

    @Path("list-all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalInfo> findAllHospital(){
        return hospitalInfoFacade.findAll(HospitalInfo.class) ;
    }

    /**
     * 保存修改
     *
     * @param hospitalInfo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(@QueryParam("tranContent") String tranContent, HospitalInfo hospitalInfo ) {
        try {
            if (null != hospitalInfo) {
                hospitalInfo.setContent(tranContent.getBytes("UTF-8"));
                hospitalInfo = hospitalInfoFacade.save(hospitalInfo);
            }
            return Response.status(Response.Status.OK).entity(hospitalInfo).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据重复，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }
}
