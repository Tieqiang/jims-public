package com.jims.wx.service;

import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.DeptDict;
import com.jims.wx.entity.DoctInfo;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.facade.*;
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
    private DeptDictFacade deptDictFacade;
    private ClinicIndexFacade clinicIndexFacade;
    private DoctInfoFacade doctInfoFacade;


    @Inject
    public RcptMasterService(RcptMasterFacade rcptMasterFacade,AppUserFacade appUserFacade,PatInfoFacade patInfoFacade,PatVsUserFacade patVsUserFacade,DeptDictFacade deptDictFacade,ClinicIndexFacade clinicIndexFacade,DoctInfoFacade doctInfoFacade) {
        this.rcptMasterFacade = rcptMasterFacade;
        this.appUserFacade=appUserFacade;
        this.patInfoFacade=patInfoFacade;
        this.patVsUserFacade=patVsUserFacade;
        this.deptDictFacade=deptDictFacade;
        this.clinicIndexFacade=clinicIndexFacade;
        this.doctInfoFacade=doctInfoFacade;
    }


    /**
     * 根据patId查询就诊收费总额
     * @param
     * @return
     */

    @GET
    @Path("find-by-patient")
    public List<OutpRcptMasterVo> findByPatientId(@QueryParam("patientId") String patientId,@QueryParam("date") String date,@QueryParam("visitNo") String visitNo){
         return rcptMasterFacade.getByPatientId(patientId,date,visitNo);
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
    public List<String> findByOpenId(@QueryParam("openId") String openId,@QueryParam("doctFlag") String doctFlag){
        List<String> lables=new ArrayList<String>();
        AppUser appUser=appUserFacade.findAppUserByOpenId(openId);
        List<PatInfo> lst=patVsUserFacade.findPatInfosByAppUserId(appUser.getId());
        for(PatInfo patInfo:lst){
            if(patInfo.getPatientId()==null || patInfo.getPatientId().equals("")){
                continue;
            }
            List<ClinicMasterVo> list= rcptMasterFacade.getByPatId(patInfo.getPatientId());
            if(!list.isEmpty()){
                for(ClinicMasterVo clinicMasterVo:list){
                    String clinicLabel=clinicMasterVo.getClinicLabel();//号别
                    if(clinicLabel!=null&&!"".equals(clinicLabel)){
                          if(doctFlag!=null&&!"".equals(doctFlag)){//医生评价
                                 String doctId=clinicIndexFacade.findDoctInfo(clinicLabel);
                                 if(doctId!=null&&!"".equals(doctId)){
                                    DoctInfo doctInfo=doctInfoFacade.findById(doctId);
                                    lables.add(doctInfo.getName());
                                 }
                          }else{
                              String deptCode=clinicIndexFacade.findDeptInfo(clinicLabel);
                              DeptDict deptDict=deptDictFacade.findByCode(deptCode);
                              if(deptDict!=null&&!"".equals(deptDict)){
                                  if(!lables.contains(deptDict.getDeptName())){
                                      lables.add(deptDict.getDeptName());
                                  }
                               }
                           }
                     }
                 }
             }
         }
        return lables;
    }   /**
     * 根据PATID查询就诊记录
     * @param patId
     * @return
     */
    @GET
    @Path("find-by-pat-id")
    public List<ClinicMasterVo> findById(@QueryParam("patId") String patId){
        List<ClinicMasterVo> list= rcptMasterFacade.getByPatId(patId);
        if(!list.isEmpty()){
            for(ClinicMasterVo clinicMasterVo:list){
                String clinicLabel=clinicMasterVo.getClinicLabel();//号别
                if(clinicLabel!=null&&!"".equals(clinicLabel)){
                    String deptCode=clinicIndexFacade.findDeptInfo(clinicLabel);
                    DeptDict deptDict=deptDictFacade.findByCode(deptCode);
                    clinicMasterVo.setClinicLabel(deptDict.getDeptName());
                }
            }
            return list;
        }else{
            return null;
        }
    }



}
