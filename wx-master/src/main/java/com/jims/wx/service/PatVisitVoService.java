package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.PatInfoFacade;
import com.jims.wx.facade.PatVisitFacade;
import com.jims.wx.facade.PatVsUserFacade;
import com.jims.wx.vo.PatVisitVo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
/**
 * Created by wei on 2016/4/22.
 */
@Path("pat-visit")
@Produces("application/json")
public class PatVisitVoService {

    private PatVisitFacade patVisitFacade;
    private AppUserFacade appUserFacade;
    private PatInfoFacade patInfoFacade;
    private PatVsUserFacade patVsUserFacade;
    @Inject
    public PatVisitVoService(PatVisitFacade patVisitFacade,AppUserFacade appUserFacade,PatInfoFacade patInfoFacade,PatVsUserFacade patVsUserFacade) {
        this.patVisitFacade = patVisitFacade;
        this.appUserFacade=appUserFacade;
        this.patInfoFacade=patInfoFacade;
        this.patVsUserFacade=patVsUserFacade;
    }

    @GET
    @Path("list")
    public List<PatVisitVo> list(@QueryParam("openId")String openId){

         AppUser appUser=appUserFacade.findAppUserByOpenId(openId);

         List<PatInfo> patInfos=patVsUserFacade.findPatInfosByAppUserId(appUser.getId());

         String patientIds="";

        for(PatInfo patInfo:patInfos){
            patientIds+="'"+patInfo.getPatientId()+"'"+",";
        }
        if(patientIds!=null&&!"".equals(patientIds)){
            patientIds=patientIds.substring(0,patientIds.length()-1);
        }
        return  patVisitFacade.listPatVisitVo(patientIds);
    }
}
