package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.AppDoctInfoVo;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.ClinicForRegistVO;
import com.jims.wx.vo.ComboboxVo;
import freemarker.template.SimpleDate;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangjing on 2016/3/21.
 */
@Path("clinic-for-regist")
@Produces("application/json")
public class ClinicForRegistService {
    private ClinicTypeChargeFacade clinicTypeChargeFacade;
    private ClinicForRegistFacade clinicForRegistFacade;
    private ClinicIndexFacade clinicIndexFacade;
    private ClinicTypeSettingFacade clinicTypeSettingFacade;
    private DeptDictFacade deptDictFacade;
    private DoctInfoFacade doctInfoFacade;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    @Inject
    public ClinicForRegistService(ClinicForRegistFacade clinicForRegistFacade, ClinicIndexFacade clinicIndexFacade, ClinicTypeChargeFacade clinicTypeChargeFacade, ClinicTypeSettingFacade clinicTypeSettingFacade,DeptDictFacade deptDictFacade,DoctInfoFacade doctInfoFacade) {
        this.clinicForRegistFacade = clinicForRegistFacade;
        this.clinicIndexFacade = clinicIndexFacade;
        this.clinicTypeChargeFacade = clinicTypeChargeFacade;
        this.clinicTypeSettingFacade = clinicTypeSettingFacade;
        this.deptDictFacade=deptDictFacade;
        this.doctInfoFacade=doctInfoFacade;
    }

//    find-by-dept-id?deptId="+deptId
    @GET
    @Path("find-by-dept-id")
    public List<AppDoctInfoVo> findByDeptId(@QueryParam("deptId") String deptId){
//        DeptDict
        List<AppDoctInfoVo> appDoctInfoVos=new ArrayList<AppDoctInfoVo>();
        /*
        * 获取当前日期 String
         */
        String currentDateStr=sdf.format(new Date());

        String deptName=this.deptDictFacade.findDeptDictByDeptId(deptId);

        List<ClinicIndex> list=clinicForRegistFacade.findClinicIndexAll();

        for(ClinicIndex clinicIndex:list){
            //一个号别只有一个doct
            if(deptName.equals(clinicIndex.getClinicDept())){
                String doctId=clinicIndex.getDoctorId();
                DoctInfo doctInfo=this.doctInfoFacade.findById(doctId);
                 /**
                 * 如果这个医生当天有出诊就显示当天
                 * 如果没有就显示里今天最近的一天
                 */
                 ClinicForRegist clinicForRegist=clinicForRegistFacade.findRegistInfo(currentDateStr);
                 AppDoctInfoVo appDoctInfoVo=new AppDoctInfoVo();
                 appDoctInfoVo.setName(doctInfo.getName());
                 appDoctInfoVo.setTitle(doctInfo.getTitle());
                 appDoctInfoVo.setHeadUrl(doctInfo.getHeadUrl());
                 appDoctInfoVo.setDescription(doctInfo.getTranDescription2());
                 appDoctInfoVo.setCurrentNum(clinicForRegist.getRegistrationNum());
                 appDoctInfoVo.setEnabledNum(clinicForRegist.getRegistrationLimits()-clinicForRegist.getRegistrationNum());
                 appDoctInfoVo.setTimeDesc(clinicForRegist.getTimeDesc());
                 appDoctInfoVo.setDeptName(deptName);
                 appDoctInfoVos.add(appDoctInfoVo);
             }
//            appDoctInfoVo.setD
        }
        return appDoctInfoVos;
    }
    /**
     * 根据id查询号表详细信息
     * @param id
     * @return
     */
    @GET
    @Path("find-info")
    public ClinicForRegistVO findInfo(@QueryParam("id") String id) {
        ClinicForRegistVO c = this.clinicForRegistFacade.findInfo(id);
        return c;
    }

    /**
     * 加载号类数据
     * @return
     */
    @POST
    @Path("find-clinic-setting-type")
    public List<ComboboxVo> findSettingType() {
        List<ComboboxVo> list = clinicTypeSettingFacade.findComboxData();
        return list;
    }
    /**
     * 号表查询
     * @param clinicIndexName
     * @param date
     * @return
     */
    @GET
    @Path("list-all")
    public List<ClinicForRegist> listAll(@QueryParam("likeName") String clinicIndexName, @QueryParam("likeDate") String date) {
        List<ClinicForRegist> list = this.clinicForRegistFacade.findAllData(ClinicForRegist.class, clinicIndexName, date);
        return list;
    }
    /**
     * 查询号别信息
     * @return
     */
    @POST
    @Path("find-clinic-index-type")
    public List<ComboboxVo> findClinicIndexType(@QueryParam("typeId") String typeId) {
        List<ComboboxVo> list = clinicIndexFacade.findClinicIndexType(typeId);
        return list;
    }

    /**
     * 判断是否可以生成号表
     * @param date
     * @param clinicIndexId
     * @param date1
     * @return
     */
    @GET
    @Path("judge-is-regist")
    public Map<String, Object> judgeIsRegist(@QueryParam("date") String date, @QueryParam("clinicIndexId") String clinicIndexId, @QueryParam("date1") String date1) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = clinicForRegistFacade.judgeIsRegist(date, clinicIndexId, date1, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 生成号表
     * @param date
     * @param clinicIndexId
     * @return
     */
    @GET
    @Path("regist-table")
    public Map<String, Object> registTable(@QueryParam("date") String date, @QueryParam("clinicIndexId") String clinicIndexId, @QueryParam("date1") String date1, @QueryParam("desc") String desc, @QueryParam("id") String id) {
        Map<String, Object> map = new HashMap<String, Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.format(sdf.parse(date));
            date1 = sdf.format(sdf.parse(date1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        map = clinicForRegistFacade.registTable(date, clinicIndexId, date1, desc, id);
        return map;
    }

    /**
     * 保存号表
     * @param clinicTypeId
     * @param date
     * @param clinicForRegist
     * @return
     */
    @POST
    @Path("save")
    public Response saveRequestMsg(@QueryParam("clinicTypeId") String clinicTypeId, @QueryParam("date") String date, ClinicForRegist clinicForRegist) {
        try {
            boolean returnVal = clinicForRegistFacade.isExists(clinicTypeId, date, 1);
            if (returnVal) {
                ClinicIndex ci = clinicIndexFacade.findById(clinicTypeId);
                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date + "00:00:00");
                clinicForRegist.setClinicDate(d);
                clinicForRegist.setClinicIndex(ci);
                clinicForRegist.setCurrentNo(0);
                clinicForRegist.setRegistrationNum(0);
                clinicForRegistFacade.save(clinicForRegist);
                return Response.status(Response.Status.OK).entity(clinicForRegist).build();
            } else {
                return Response.status(Response.Status.FORBIDDEN).entity(clinicForRegist).build();
            }
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1 ) {
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
     * 批量删除
     * @param ids
     * @return
     */
    @POST
    @Path("delete")
    public Response delete(@QueryParam("ids") String ids) {
        try {
            List<String> list = clinicForRegistFacade.delete(ClinicForRegist.class, ids);
            System.out.println(Response.status(Response.Status.OK).entity(list).build().getStatus());
            return Response.status(Response.Status.OK).entity(list).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            e.printStackTrace();
            errorException.setMessage(e);
            System.out.println(Response.status(Response.Status.OK).entity(errorException).build().getStatus());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }

    /**
     * 修改号表
     * @param clinicIndexId
     * @param date
     * @param clinicForRegist
     * @return
     */
    @POST
    @Path("update")
    public Response update(@QueryParam("clinicIndexId") String clinicIndexId, @QueryParam("date") String date, ClinicForRegist clinicForRegist) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            clinicForRegist.setClinicDate(sdf.parse(date));
            clinicForRegist.setClinicIndex(clinicIndexFacade.findById(clinicIndexId));
            ClinicForRegist v = clinicForRegistFacade.save(clinicForRegist);
            return Response.status(Response.Status.OK).entity(v).build();
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
