package com.jims.wx.service;

import com.jims.wx.facade.RcptMasterFacade;
import com.jims.wx.vo.ClinicMasterVo;
import com.jims.wx.vo.OutpBillItemsVo;
import com.jims.wx.vo.OutpRcptMasterVo;
import com.jims.wx.vo.PatVsUserVo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;

/**
 * Created by zhu on 2016/4/22.
 */
@Path("rcpt-master")
@Produces("application/json")
public class RcptMasterService {
    private RcptMasterFacade rcptMasterFacade;

    @Inject
    public RcptMasterService(RcptMasterFacade rcptMasterFacade) {
        this.rcptMasterFacade = rcptMasterFacade;
    }

    /**
     * 根据PATID查询就诊记录
     * @param patId
     * @return
     */
    @GET
    @Path("find-by-pat-id")
    public List<ClinicMasterVo> findById(@QueryParam("patId") String patId){
        return rcptMasterFacade.getByPatId(patId);
    }

    /**
     * 根据patId查询
     * @param
     * @return
     */
    @GET
    @Path("find-by-patient")
    public List<OutpRcptMasterVo> findByPatientId(@QueryParam("patientId") String patientId){
        return rcptMasterFacade.getByPatientId(patientId);
    }


    /**
     * 根据RcptNo查询
     * @param
     * @return
     */

    @GET
    @Path("find-by-rcpt")
    public List<OutpBillItemsVo> findByRcptNo(@QueryParam("rcptNo") String rcptNo){
        return rcptMasterFacade.getByRcptNo(rcptNo);
    }

    @GET
    @Path("find-by-app-user")
    public List<PatVsUserVo> findByAppUser(@QueryParam("openId") String openId){
        if(openId!=null) {
            return rcptMasterFacade.getByAppUser(openId);
        }else {
            return null;
        }
    }
}
