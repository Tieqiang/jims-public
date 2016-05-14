package com.jims.wx.service;

import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.PatInfoFacade;
import com.jims.wx.facade.PatVsUserFacade;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 2016/4/22.
 */
@Path("rcpt-master")
@Produces("application/json")
public class RcptMasterService {
    private RcptMasterFacade rcptMasterFacade;
    private AppUserFacade appUserFacade;
    private PatInfoFacade patInfoFacade;
    private PatVsUserFacade patVsUserFacade;


    @Inject
    public RcptMasterService(RcptMasterFacade rcptMasterFacade,AppUserFacade appUserFacade,PatInfoFacade patInfoFacade,PatVsUserFacade patVsUserFacade) {
        this.rcptMasterFacade = rcptMasterFacade;
        this.appUserFacade=appUserFacade;
        this.patInfoFacade=patInfoFacade;
        this.patVsUserFacade=patVsUserFacade;
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
     * 根据patId查询就诊收费总额
     * @param
     * @return
     */

    @GET
    @Path("find-by-patient")
    public List<OutpRcptMasterVo> findByPatientId(@QueryParam("patientId") String patientId,@QueryParam("date") String date){
         return rcptMasterFacade.getByPatientId(patientId,date);
    }


    /**
     * 根据RcptNo查询详细收费
     * @param
     * @return
     */
    @GET
    @Path("find-by-rcpt")
    public List<OutpBillItemsVo> findByRcptNo(@QueryParam("rcptNo") String rcptNo){
        return rcptMasterFacade.getByRcptNo(rcptNo);
    }

    // 根据openId查询绑定用户
    @GET
    @Path("find-by-app-user")
    public List<PatVsUserVo> findByAppUser(@QueryParam("openId") String openId){
        if(openId!=null) {
            return rcptMasterFacade.getByAppUser(openId);
        }else {
            return null;
        }
    }

    /**
     * 查询是否有就诊记录
     * @param openId
     * @return
     */
    @GET
    @Path("find-by-open-id")
    public List<String> findByOpenId(@QueryParam("openId") String openId){
        List<String> lables=new ArrayList<String>();
        AppUser appUser=appUserFacade.findAppUserByOpenId(openId);
        List<PatInfo> lst=patVsUserFacade.findPatInfosByAppUserId(appUser.getId());
        for(PatInfo patInfo:lst){
            List<ClinicMasterVo> list= rcptMasterFacade.getByPatId(patInfo.getPatientId());
            if(!list.isEmpty()){
                for(ClinicMasterVo clinicMasterVo:list){
                    String clinicLabel=clinicMasterVo.getClinicLabel();
                    lables.add(clinicLabel);
                }
                return lables;
            }
        }
        return null;
    }
}
