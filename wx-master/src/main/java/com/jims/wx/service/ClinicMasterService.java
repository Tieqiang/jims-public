package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.ClinicMaster;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.ClinicMasterFacade;
import com.jims.wx.facade.PatInfoFacade;
import com.jims.wx.facade.PatVsUserFacade;
import com.jims.wx.vo.ClinicMasterVo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-master")
@Produces("application/json")
public class ClinicMasterService {
    private ClinicMasterFacade clinicMasterFacade;
    private AppUserFacade appUserFacade;
    private PatInfoFacade patInfoFacade;

    private PatVsUserFacade patVsUserFacade;

    @Inject
    public ClinicMasterService(ClinicMasterFacade clinicMasterFacade, AppUserFacade appUserFacade, PatInfoFacade patInfoFacade, PatVsUserFacade patVsUserFacade) {
        this.clinicMasterFacade = clinicMasterFacade;
        this.appUserFacade = appUserFacade;
        this.patInfoFacade = patInfoFacade;
        this.patVsUserFacade = patVsUserFacade;
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

    /**
     * 查询我的挂号信息
     * 可以为多个人挂号
     * flag=1 今天
     * flag=0 历史
      * @return
     */
    @GET
    @Path("find-my-regist")
    public Map<String, Object> findMyRegist(@QueryParam("openId") String openId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ClinicMasterVo> clinicMasterVos = new ArrayList<ClinicMasterVo>();
        List<List<ClinicMasterVo>> listList = new ArrayList<List<ClinicMasterVo>>();
        AppUser appUser = this.appUserFacade.findAppUserByOpenId(openId);
        List<PatInfo> patInfos = patVsUserFacade.findPatInfosByAppUserId(appUser.getId());
        List<String> patientIds = new ArrayList<String>();
        for (PatInfo patInfo : patInfos) {
            String patientId = patInfo.getPatientId();
            if (patientId != null && !"".equals(patientId)) {
                patientIds.add(patientId);
            }
        }
        map = clinicMasterFacade.findMyRegist(patientIds);
        return map;
    }
}
