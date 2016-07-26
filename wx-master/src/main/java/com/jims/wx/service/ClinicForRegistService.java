package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.*;
import freemarker.template.SimpleDate;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang.StringUtils;
import weixin.popular.bean.message.EventMessage;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.util.XMLConverUtil;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cxy on 2016/3/21.
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
    private ClinicMasterFacade clinicMasterFacade;
    private PatVsUserFacade patVsUserFacade;
    private AppUserFacade appUserFacade;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private HttpServletResponse response;
    private PatInfoFacade patInfoFacade;
    private ClinicScheduleFacade clinicScheduleFacade;
    private HttpServletRequest request;
    private TakeRegistSeqFacade takeRegistSeqFacade;
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
    private UserCollectionFacade userCollectionFacade;

    @Inject
    public ClinicForRegistService(ClinicForRegistFacade clinicForRegistFacade, ClinicIndexFacade clinicIndexFacade, ClinicTypeChargeFacade clinicTypeChargeFacade, ClinicTypeSettingFacade clinicTypeSettingFacade, DeptDictFacade deptDictFacade, DoctInfoFacade doctInfoFacade, ClinicMasterFacade clinicMasterFacade, PatVsUserFacade patVsUserFacade, AppUserFacade appUserFacade, HttpServletResponse response, PatInfoFacade patInfoFacade, ClinicScheduleFacade clinicScheduleFacade, HttpServletRequest request, TakeRegistSeqFacade takeRegistSeqFacade, UserCollectionFacade userCollectionFacade) {
        this.clinicForRegistFacade = clinicForRegistFacade;
        this.clinicIndexFacade = clinicIndexFacade;
        this.clinicTypeChargeFacade = clinicTypeChargeFacade;
        this.clinicTypeSettingFacade = clinicTypeSettingFacade;
        this.deptDictFacade = deptDictFacade;
        this.doctInfoFacade = doctInfoFacade;
        this.clinicMasterFacade = clinicMasterFacade;
        this.patVsUserFacade = patVsUserFacade;
        this.appUserFacade = appUserFacade;
        this.response = response;
        this.patInfoFacade = patInfoFacade;
        this.clinicScheduleFacade = clinicScheduleFacade;
        this.request = request;
        this.takeRegistSeqFacade = takeRegistSeqFacade;
        this.userCollectionFacade = userCollectionFacade;
    }


    /**
     * @param price             挂号的价格
     * @param clinicForRegistId 号表的id
     * @return
     * @description 挂号的方法
     */
    @GET
    @Path("regist")
    public String regist(@QueryParam("price") String price, @QueryParam("prepareId") String prepareId, @QueryParam("clinicForRegistId") String clinicForRegistId, @QueryParam("openId") String openId) {
        try {
            if (StringUtils.isNotBlank(price) && StringUtils.isNotBlank(prepareId) && StringUtils.isNotBlank(clinicForRegistId)) {
                ClinicForRegist clinicForRegist = clinicForRegistFacade.findById(clinicForRegistId);
                if (clinicForRegist == null) {
                    throw new IllegalArgumentException("非法的号表主键=" + clinicForRegistId);
                }
                /**
                 *向clinic_master表中写入一条数据
                 */
                ClinicMaster clinicMaster = new ClinicMaster();
                clinicMaster.setClincRegistId(clinicForRegistId);//号表主键
                //            /**
                //             * 查找Idcard
                //             */
                AppUser appUser = appUserFacade.findAppUserByOpenId(openId);
                if (appUser == null) {
                    throw new IllegalArgumentException("非法的openId=" + openId);
                }
                String patId = appUser.getPatId();//appuser主键
                //            String idCard2 = patVsUserFacade.findPatIdById(appuserId);
                PatInfo patInfo = patInfoFacade.findById(patId);
                if (patInfo == null) {
                    throw new IllegalArgumentException("非法的patId=" + patId);
                }
                //            String idCard2 = patInfoFacade.findIdCard(patId);
                String patientId = patInfo.getPatientId();
                String registTime = clinicForRegist.getRegistTime();//就诊日期
                if (patientId != null && !"".equals(patientId)) {
                    clinicMaster.setPatientId(patientId);
                } else {
                    Integer sortMath = 1000;
                    TakeRegistSeq a = null;
                    TakeRegistSeq record = takeRegistSeqFacade.findByDate(registTime);
                    if (record != null && !"".equals(record)) {
                        sortMath = Integer.parseInt(record.getMath());
                        sortMath++;
                        clinicMaster.setPatientId(sdf2.format(sdf2.parse(registTime)) + String.valueOf(sortMath + 1));
                        record.setMath(String.valueOf(sortMath));
                        takeRegistSeqFacade.save(record);
                    } else {//今天是第一位取号的人员  1000放入数据库
                        a = takeRegistSeqFacade.save(new TakeRegistSeq("1000", registTime));
                        clinicMaster.setPatientId(sdf2.format(sdf2.parse(registTime)) + String.valueOf(1000));
                    }

                    patInfo.setPatientId(sdf2.format(sdf2.parse(registTime)) + String.valueOf(sortMath + 1));
                    patInfoFacade.save(patInfo);
                }
                clinicMaster.setRegistFee(0.0);//设为0
                clinicMaster.setClinicFee(0.0);///设为0
                clinicMaster.setOtherFee(Double.valueOf(price));//和
                clinicMaster.setClinicCharge(Double.valueOf(price));
                clinicMaster.setTakeStatus("0");//未取号
                clinicMaster.setRegistDate(new Date());
                clinicMaster.setVisitDate(sdf.parse(registTime));
//                clinicMaster.setVisitDate(sdf.parse(clinicForRegist.getRegistTime()));
                 /*
                 * 跟新号表的数据
                 * */
                ClinicMaster c = clinicMasterFacade.saveRecord(clinicMaster);
                //            ClinicForRegist clinicForRegist = clinicForRegistFacade.findById(clinicForRegistId);
                //当日已经挂号数+1
                clinicForRegist.setRegistrationNum(clinicForRegist.getRegistrationNum() + 1);
                //当前号+1
                clinicForRegist.setCurrentNo(clinicForRegist.getCurrentNo() + 1);
                //限约号数-1
                clinicForRegist.setAppointmentLimits(clinicForRegist.getAppointmentLimits() - 1);
                ClinicForRegist cfr = clinicForRegistFacade.save(clinicForRegist);
                if (c != null && cfr != null) {//保存成功
                    response.sendRedirect("/views/his/public/app-pay-success.html?clinicForRegistId=" + clinicForRegistId + "&price=" + cfr.getRegistPrice() + "&openId=" + openId + "&prepareId=" + prepareId);
                } else {
                    //跳转到操作失败页面
                    response.sendRedirect("/views/his/public/app-pay-failed.html");
                }
            } else {
                System.out.println("请求参数非法！");
                response.sendRedirect("/views/his/public/app-pay-failed.html");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 查询所有的号别 clinicIndex
     * 根据号别的Id查询号表信息
     *
     * @param deptId
     * @return
     */
    @GET
    @Path("find-by-dept-id")
    public List<AppDoctInfoVo> findByDeptId(@QueryParam("deptId") String deptId, @QueryParam("openId") String openId, @QueryParam("preFlag") String preFlag) {
        List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
        String addr = getRequestUrl();
        /**
         * 查询默认绑定患者
         */
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("openid 为空!");
        }
        if(deptId==null || deptId.equals("")){
            throw new IllegalArgumentException("deptId 为空!");
        }
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        if(appuser==null){
            throw new IllegalArgumentException("appuser 不存在!");
        }
        if(appuser.getPatId()==null || "".equals(appuser.getPatId())){
            throw new IllegalArgumentException("没有绑定默认的患者");
        }
        PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
        if (patInfo == null) {
            throw new IllegalArgumentException("没有找到默认绑定的患者！patId非法");
        }
        String currentDateStr = sdf.format(new Date());
        deptId = deptId.replaceAll("\\s*", "");
        deptId = deptId.replaceAll("", "");
        deptId = deptId.trim();
        DeptDict deptDict = this.deptDictFacade.findById(deptId);
        if(deptDict==null){
            throw new IllegalArgumentException("deptDict 没有找到！");
        }
        String deptName = deptDict.getDeptName();
        String deptCode = deptDict.getDeptCode();
        List<ClinicIndex> list = clinicForRegistFacade.findClinicIndexAll();
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("号别集合为空！退出程序！");
        }
        for (ClinicIndex clinicIndex : list) {
            DoctInfo doctInfo = null;
            //一个号别只有一个doct     clinicDept code
            if (deptCode.equals(clinicIndex.getClinicDept())) {
                String doctId = clinicIndex.getDoctorId();
                if (doctId != null && !"".equals(doctId)) {
                    doctInfo = this.doctInfoFacade.findById(doctId);
                    if(doctInfo==null){
                        continue;
                    }
                }
                ClinicForRegist clinicForRegist = clinicForRegistFacade.findRegistInfo(currentDateStr, clinicIndex.getId());
                if(clinicForRegist==null){
                    continue;
                }
                if (clinicForRegist != null && !"".equals(clinicForRegist)) {
                    AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
                    appDoctInfoVo.setClinicIndexId(clinicIndex == null ? null : clinicIndex.getId());
                    appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
                    appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
                    appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
                    appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
                    appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
                    appDoctInfoVo.setCurrentNum(clinicForRegist == null ? 0 : clinicForRegist.getRegistrationNum());
                    appDoctInfoVo.setEnabledNum(clinicForRegist == null ? 0 : clinicForRegist.getRegistrationLimits() - clinicForRegist.getRegistrationNum());
                    appDoctInfoVo.setTimeDesc(clinicForRegist == null ? sdf.format(new Date()) : clinicForRegist.getTimeDesc());
                    appDoctInfoVo.setDeptName(deptName);
                    appDoctInfoVo.setPrice(clinicForRegist == null ? 0 : clinicForRegist.getRegistPrice());
                    appDoctInfoVo.setRid(clinicForRegist == null ? null : clinicForRegist.getId());
                    appDoctInfoVo.setPatName(patInfo.getName());
                    boolean flag = userCollectionFacade.findISCollection(doctInfo.getId(), openId);
                    if (flag) {
                        appDoctInfoVo.setCollectionDesc("已收藏");
                    } else {
                        appDoctInfoVo.setCollectionDesc("收藏");
                    }
                    appDoctInfoVos.add(appDoctInfoVo);
                }
            }
        }
        return appDoctInfoVos;
    }

    /**
     * 根据id查询号表详细信息
     *
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
     *
     * @return
     */
    @POST
    @Path("find-clinic-setting-type")
    public List<ComboboxVo> findSettingType() {
        List<ComboboxVo> list = clinicTypeSettingFacade.findComboxData();
        return list;
    }

//    /**
//     * 号表查询
//     *
//     * @param clinicIndexName
//     * @param date
//     * @return
//     */
//    @GET
//    @Path("list-all")
//    public List<ClinicForRegist> listAll(@QueryParam("likeName") String clinicIndexName, @QueryParam("likeDate") String date) {
//        List<ClinicForRegist> list = this.clinicForRegistFacade.findAll(ClinicForRegist.class, clinicIndexName, date);
//        return list;
//    }

    /**
     * 查询号别信息
     *
     * @return
     */
    @POST
    @Path("find-clinic-index-type")
    public List<ComboboxVo> findClinicIndexType(@QueryParam("typeId") String typeId) {
        List<ComboboxVo> list = clinicIndexFacade.findClinicIndexType(typeId);
        return list;
    }

//    /**
//     * 判断是否可以生成号表
//     *
//     * @param date
//     * @param clinicIndexId
//     * @param date1
//     * @return
//     */
//    @GET
//    @Path("judge-is-regist")
//    public Map<String, Object> judgeIsRegist(@QueryParam("date") String date, @QueryParam("clinicIndexId") String clinicIndexId, @QueryParam("date1") String date1) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        try {
//            map = clinicForRegistFacade.judgeIsRegist(date, clinicIndexId, date1, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return map;
//    }

    /**
     * 生成号表
     *
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

//    /**
//     * 保存号表
//     *
//     * @param clinicTypeId
//     * @param date
//     * @param clinicForRegist
//     * @return
//     */
//    @POST
//    @Path("save")
//    public Response saveRequestMsg(@QueryParam("clinicTypeId") String clinicTypeId, @QueryParam("date") String date, ClinicForRegist clinicForRegist) {
//        try {
//            boolean returnVal = clinicForRegistFacade.isExists(clinicTypeId, date, 1);
//            if (returnVal) {
//                ClinicIndex ci = clinicIndexFacade.findById(clinicTypeId);
//                Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date + "00:00:00");
//                clinicForRegist.setClinicDate(d);
//                clinicForRegist.setClinicIndex(ci);
//                clinicForRegist.setCurrentNo(0);
//                clinicForRegist.setRegistrationNum(0);
//                clinicForRegistFacade.save(clinicForRegist);
//                return Response.status(Response.Status.OK).entity(clinicForRegist).build();
//            } else {
//                return Response.status(Response.Status.FORBIDDEN).entity(clinicForRegist).build();
//            }
//        } catch (Exception e) {
//            ErrorException errorException = new ErrorException();
//            errorException.setMessage(e);
//            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
//                errorException.setErrorMessage("输入数据超过长度！");
//            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
//                errorException.setErrorMessage("数据已存在，提交失败！");
//            } else {
//                errorException.setErrorMessage("提交失败！");
//            }
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
//        }
//    }

    /**
     * 批量删除
     *
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
     *
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

    /**
     * 查询确认订单的信息
     *
     * @param openId
     * @param clinicForRegistId
     * @return
     */
    @POST
    @Path("find-doct-regist")
    public AppDoctInfoVo findDoctRegist(@QueryParam("openId") String openId, @QueryParam("clinicForRegistId") String clinicForRegistId) {
        AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
        try {
            if (openId != null && !"".equals(openId)) {
                AppUser appUser = appUserFacade.findAppUserByOpenId(openId);
                PatInfo patInfo = patInfoFacade.findById(appUser.getPatId());
                appDoctInfoVo.setPatName(patInfo.getName());
                appDoctInfoVo.setIdCard(patInfo.getIdCard());
            }
            ClinicForRegist clinicForRegist = clinicForRegistFacade.findById(clinicForRegistId);
            appDoctInfoVo.setEnabledNum(clinicForRegist.getRegistrationLimits() - clinicForRegist.getRegistrationNum());
            appDoctInfoVo.setTimeDesc(clinicForRegist.getTimeDesc());
//            appDoctInfoVo.setDeptName(clinicForRegist.getClinicIndex().getClinicDept());
            DeptDict deptDict = deptDictFacade.findByCode(clinicForRegist.getClinicIndex().getClinicDept());
            appDoctInfoVo.setDeptName(deptDict.getDeptName());
            ClinicIndex clinicIndex = clinicForRegist.getClinicIndex();
            String clinicScheduletime = clinicScheduleFacade.findTime(clinicIndex.getId());
            appDoctInfoVo.setScheduleTime(clinicScheduletime);
            String docId = clinicIndex.getDoctorId();
            DoctInfo doctInfo = doctInfoFacade.findById(docId);
            appDoctInfoVo.setName(doctInfo.getName());
            appDoctInfoVo.setTitle(doctInfo.getTitle());
            appDoctInfoVo.setHeadUrl(doctInfo.getHeadUrl());
            appDoctInfoVo.setDeptAddr(deptDict.getDeptLocation());
            appDoctInfoVo.setDescription(doctInfo.getTranDescription2());
            appDoctInfoVo.setRegistTime(sdf.format(new Date()));
            return appDoctInfoVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appDoctInfoVo;
    }


    /**
     * 取号接口
     *
     * @param idCard
     * @param patientId
     * @return
     */
    @GET
    @Path("take-regist")
    public AppDoctInfoVo takeRegist(@QueryParam("idCard") String idCard, @QueryParam("patientId") String patientId, @QueryParam("clinicMasterId") String clinicMasterId) {
        if(idCard==null || "".equals(idCard)){
            throw new IllegalArgumentException("idCard 不能为空！");
        }
        if(patientId==null || "".equals(patientId)){
            throw new IllegalArgumentException("patientId 不能为空！");
        }
        if(clinicMasterId==null || "".equals(clinicMasterId)){
            throw new IllegalArgumentException("clinicMasterId 不能为空！");
        }
        if (idCard != null && !"".equals(idCard) && patientId != null && !"".equals(patientId)) {
            AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
            PatInfo patInfo = patInfoFacade.findByIdCard(idCard);
            if(patInfo==null){
                throw new IllegalArgumentException("非法的idCard,没有找到patInfo");
            }
            if (patInfo != null && !"".equals(patInfo)) {
                ClinicMaster clinicMaster = clinicMasterFacade.updateTakeRegistStatus(clinicMasterId);
                if(clinicMaster==null){
                    throw new IllegalArgumentException("非法的clinicMasterId,没有找到ClinicMaster");
                }
                if (clinicMaster != null && !"".equals(clinicMaster)) {//取票成功
                    patInfo.setPatientId(patientId);
                    patInfoFacade.save(patInfo);
                    appDoctInfoVo = findInfo(clinicMaster.getClincRegistId(), patInfo.getId());
                    return appDoctInfoVo;
                }
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * 通过号表ID和患者ID查询相关信息
     *
     * @param clinicForRegistId
     * @param patId
     * @return
     */
    private AppDoctInfoVo findInfo(String clinicForRegistId, String patId) {
        AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
        try {
            if (patId != null && !"".equals(patId)) {
                PatInfo patInfo = patInfoFacade.findById(patId);
                appDoctInfoVo.setPatName(patInfo.getName());
                appDoctInfoVo.setIdCard(patInfo.getIdCard());
            }
            ClinicForRegist clinicForRegist = clinicForRegistFacade.findById(clinicForRegistId);
            appDoctInfoVo.setEnabledNum(clinicForRegist.getRegistrationLimits() - clinicForRegist.getRegistrationNum());
            appDoctInfoVo.setTimeDesc(clinicForRegist.getTimeDesc());
            DeptDict deptDict = deptDictFacade.findByCode(clinicForRegist.getClinicIndex().getClinicDept());
            appDoctInfoVo.setDeptName(deptDict.getDeptName());
            ClinicIndex clinicIndex = clinicForRegist.getClinicIndex();
            String clinicScheduletime = clinicScheduleFacade.findTime(clinicIndex.getId());
            appDoctInfoVo.setScheduleTime(clinicScheduletime);
            String docId = clinicIndex.getDoctorId();
            DoctInfo doctInfo = doctInfoFacade.findById(docId);
            appDoctInfoVo.setName(doctInfo.getName());
            appDoctInfoVo.setTitle(doctInfo.getTitle());
            appDoctInfoVo.setHeadUrl(doctInfo.getHeadUrl());
            appDoctInfoVo.setDescription(doctInfo.getTranDescription2());
            appDoctInfoVo.setRegistTime(sdf.format(new Date()));
            return appDoctInfoVo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 我的收藏
     *
     * @return
     */
    @GET
    @Path("find-my-collection")
    public List<AppDoctInfoVo> findMyCollection(@QueryParam("openId") String openId) {
//        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String addr = getRequestUrl();
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
        List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
        List<UserCollection> userCollections = this.userCollectionFacade.findByOpenId(openId);
        if (userCollections != null && !userCollections.isEmpty()) {
            for (UserCollection userCollection : userCollections) {
                AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
                appDoctInfoVo.setId(userCollection.getId());
                DoctInfo doctInfo = doctInfoFacade.findById(userCollection.getDoctId());
                appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
                appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
                appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
                appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
                appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
                List<ClinicForRegist> clinicForRegists = clinicForRegistFacade.findRegistInfoCollection(sdf.format(new Date()), userCollection.getClinicIndexId());
                List<RegistInfoVO> registInfoVOs = new ArrayList<RegistInfoVO>();
                for (ClinicForRegist c : clinicForRegists) {
                    RegistInfoVO registInfoVO = new RegistInfoVO();
                    registInfoVO.setCurrentNum(c.getRegistrationNum());
                    registInfoVO.setEnabledNum(c.getRegistrationLimits() - c.getRegistrationNum());
                    registInfoVO.setRid(c.getId());
                    registInfoVO.setPrice(c.getRegistPrice());
                    registInfoVO.setTimeDesc(c.getTimeDesc());
                    registInfoVOs.add(registInfoVO);
                }
                appDoctInfoVo.setPatName(patInfo.getName());
                String deptCode = clinicIndexFacade.findDeptCodeByDoctId(doctInfo.getId());
                DeptDict deptDict = deptDictFacade.findByCode(deptCode);
                if (deptDict != null) {
                    appDoctInfoVo.setDeptName(deptDict.getDeptName());
                }
                appDoctInfoVo.setRegistInfoVOs(registInfoVOs);
                appDoctInfoVos.add(appDoctInfoVo);
            }
        }
        return appDoctInfoVos;
    }

    /**
     * 一个医生可能有多个出诊安排
     * 不包括当天的
     *
     * @param deptId
     * @param openId
     * @return
     */
    @GET
    @Path("find-by-dept-id-pre")
    public List<AppDoctInfoVo> findByDeptIdPre(@QueryParam("deptId") String deptId, @QueryParam("openId") String openId) {
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("非法的openId,openId 为空！");
        }
        if(deptId==null || "".equals(deptId)){
            throw new IllegalArgumentException("非法的deptId，deptId为空！");
        }
        List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
        String addr = getRequestUrl();
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        if(appuser==null){
            throw new IllegalArgumentException("appuser 为空");
        }
        if(appuser.getPatId()==null || "".equals(appuser.getPatId())){
            throw new IllegalArgumentException("appuser 不存在默认的绑定患者！");
        }
        PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
        if(patInfo==null){
            throw new IllegalArgumentException("patInfo 为空！");
        }
         /*
        * 获取当前日期 String
         */
        String currentDateStr = sdf.format(new Date());
        deptId = deptId.replaceAll("\\s*", "");
        deptId = deptId.replaceAll("", "");
        deptId = deptId.trim();
        DeptDict deptDict = this.deptDictFacade.findById(deptId);
        if(deptDict==null){
            throw new IllegalArgumentException("deptDict 为空！");
        }
        String deptName = deptDict.getDeptName();
        String deptCode = deptDict.getDeptCode();
        //号表中有多少可以挂号的号别
        List<ClinicIndex> list = clinicForRegistFacade.findClinicIndexAll();
        if(list==null || list.isEmpty()){
            return null;
        }
        for (ClinicIndex clinicIndex : list) {
            DoctInfo doctInfo = null;
            //一个号别只有一个doct     clinicDept code
            if (deptCode.equals(clinicIndex.getClinicDept())) {
                String doctId = clinicIndex.getDoctorId();
                if (doctId != null && !"".equals(doctId)) {
                    doctInfo = this.doctInfoFacade.findById(doctId);
                    if(doctInfo==null){
                        continue;
                    }
                }
                //查找这个号别 15天之内 对于这个科室的号表。
                List<ClinicForRegist> clinicForRegists = clinicForRegistFacade.findRegistInfoPre(currentDateStr, clinicIndex.getId());
                if(clinicForRegists==null || clinicForRegists.isEmpty()){
                   continue;
                }
                AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
                appDoctInfoVo.setClinicIndexId(clinicIndex == null ? null : clinicIndex.getId());
                appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
                appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
                appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
                appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
                appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
                boolean flag = userCollectionFacade.findISCollection(doctInfo.getId(), openId);
                if (flag) {
                    appDoctInfoVo.setCollectionDesc("已收藏");
                } else {
                    appDoctInfoVo.setCollectionDesc("收藏");
                }
                List<RegistInfoVO> registInfoVOs = new ArrayList<RegistInfoVO>();
                if (clinicForRegists != null && !clinicForRegists.isEmpty()) {
                    for (ClinicForRegist c : clinicForRegists) {
                        RegistInfoVO registInfoVO = new RegistInfoVO();
                        registInfoVO.setCurrentNum(c.getRegistrationNum());
                        registInfoVO.setEnabledNum(c.getRegistrationLimits() - c.getRegistrationNum());
                        registInfoVO.setRid(c.getId());
                        registInfoVO.setPrice(c.getRegistPrice());
                        registInfoVO.setTimeDesc(c.getTimeDesc());
                        registInfoVOs.add(registInfoVO);
                    }
                }
                appDoctInfoVo.setDeptName(deptName);
                appDoctInfoVo.setPatName(patInfo.getName());
                appDoctInfoVo.setRegistInfoVOs(registInfoVOs);
                appDoctInfoVos.add(appDoctInfoVo);
            }
        }
        return appDoctInfoVos;
    }


    /**
     * 模糊查询医生
     *
     * @param likeSearch
     * @param openId
     * @return
     */
    @GET
    @Path("find-by-dept-id-pre-like")
    public List<AppDoctInfoVo> findByDeptIdPreLike(@QueryParam("likeSearch") String likeSearch, @QueryParam("openId") String openId,@QueryParam("deptId") String deptId,@QueryParam("flag") String flag) {
        List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
        String addr = getRequestUrl();
        if(openId==null || "".equals(openId)){
            throw new IllegalArgumentException("openId 为空!");
        }
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        if(appuser==null){
            throw new IllegalArgumentException("appuser 为空！");
        }
        if(appuser.getPatId()==null || "".equals(appuser.getPatId())){
            throw new IllegalArgumentException("没有绑定默认的患者！");
        }
        PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
        if(patInfo==null){
            throw new IllegalArgumentException("patInfo 为空！");
        }
        List<DoctInfo> doctInfos = doctInfoFacade.queryByCondition(DoctInfo.class,likeSearch);
        if(doctInfos==null || doctInfos.isEmpty()){//没有查找的结果
            return null;
        }
        //查找所有存在号表的号别
        List<ClinicIndex> list = clinicForRegistFacade.findClinicIndexAll();
        if((deptId==null || "".equals(deptId))&&"".equals(flag)){
            throw new IllegalArgumentException("deptId 为空！");
        }
        DeptDict deptDict=null;
        if(flag==null || "".equals(flag)){
             deptDict=this.deptDictFacade.findById(deptId);
            if(deptDict==null){
                throw new IllegalArgumentException("deptDict 为空！");
            }

        }
        for (DoctInfo doctInfo : doctInfos) {
            for (ClinicIndex clinicIndex : list) {
                 if (flag.equalsIgnoreCase("pre")?clinicIndex.getDoctorId().equals(doctInfo.getId()):clinicIndex.getDoctorId().equals(doctInfo.getId())&&clinicIndex.getClinicDept().equals(deptDict.getDeptCode())) {//有号
                    List<ClinicForRegist> clinicForRegists = clinicForRegistFacade.findRegistInfoPre(sdf.format(new Date()), clinicIndex.getId());
                    AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
                    appDoctInfoVo.setClinicIndexId(clinicIndex == null ? null : clinicIndex.getId());
                    appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
                    appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
                    appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
                    appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
                    appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
                    boolean flag1 = userCollectionFacade.findISCollection(doctInfo.getId(), openId);
                    if (flag1) {
                        appDoctInfoVo.setCollectionDesc("已收藏");
                    } else {
                        appDoctInfoVo.setCollectionDesc("收藏");
                    }
                    List<RegistInfoVO> registInfoVOs = new ArrayList<RegistInfoVO>();
                    if (clinicForRegists != null && !clinicForRegists.isEmpty()) {
                        for (ClinicForRegist c : clinicForRegists) {
                            RegistInfoVO registInfoVO = new RegistInfoVO();
                            registInfoVO.setCurrentNum(c.getRegistrationNum());
                            registInfoVO.setEnabledNum(c.getRegistrationLimits() - c.getRegistrationNum());
                            registInfoVO.setRid(c.getId());
                            registInfoVO.setPrice(c.getRegistPrice());
                            registInfoVO.setTimeDesc(c.getTimeDesc());
                            registInfoVOs.add(registInfoVO);
                        }
                    }
                    appDoctInfoVo.setDeptName(deptDict==null?null:deptDict.getDeptName());
                    appDoctInfoVo.setPatName(patInfo.getName());
                    appDoctInfoVo.setRegistInfoVOs(registInfoVOs);
                    appDoctInfoVos.add(appDoctInfoVo);
                }
             }
        }
        return appDoctInfoVos;
    }

    /**
     * 模糊查询医生
     * 当天挂号
     *
     * @param likeSearch
     * @param openId
     * @return
     */
    @GET
    @Path("find-by-dept-id-like")
    public List<AppDoctInfoVo> findByDeptIdLike(@QueryParam("likeSearch") String likeSearch, @QueryParam("openId") String openId) {
        List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
//        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String addr = getRequestUrl();
        AppUser appuser = appUserFacade.findAppUserByOpenId(openId);
        PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
        List<DoctInfo> doctInfos = doctInfoFacade.queryByCondition(DoctInfo.class,likeSearch);
        List<ClinicIndex> list = clinicForRegistFacade.findClinicIndexAll();
//        List<UserCollection> userCollections=userCollectionFacade.findByOpenId(openId);
        for (DoctInfo doctInfo : doctInfos) {
            for (ClinicIndex clinicIndex : list) {
                if (clinicIndex.getDoctorId().equals(doctInfo.getId())) {//有号
                    ClinicForRegist clinicForRegist = clinicForRegistFacade.findRegistInfo(sdf.format(new Date()), clinicIndex.getId());
                    if (clinicForRegist != null && !"".equals(clinicForRegist)) {
                        AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
                        appDoctInfoVo.setClinicIndexId(clinicIndex == null ? null : clinicIndex.getId());
                        appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
                        appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
                        appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
                        appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
                        appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
                        appDoctInfoVo.setCurrentNum(clinicForRegist == null ? 0 : clinicForRegist.getRegistrationNum());
                        appDoctInfoVo.setEnabledNum(clinicForRegist == null ? 0 : clinicForRegist.getRegistrationLimits() - clinicForRegist.getRegistrationNum());
                        appDoctInfoVo.setTimeDesc(clinicForRegist == null ? sdf.format(new Date()) : clinicForRegist.getTimeDesc());
//                        appDoctInfoVo.setDeptName(deptName);
                        appDoctInfoVo.setPrice(clinicForRegist == null ? 0 : clinicForRegist.getRegistPrice());
                        appDoctInfoVo.setRid(clinicForRegist == null ? null : clinicForRegist.getId());
                        appDoctInfoVo.setPatName(patInfo.getName());
                        boolean flag = userCollectionFacade.findISCollection(doctInfo.getId(), openId);
                        if (flag) {
                            appDoctInfoVo.setCollectionDesc("已收藏");
                        } else {
                            appDoctInfoVo.setCollectionDesc("收藏");
                        }
                        appDoctInfoVos.add(appDoctInfoVo);
                    }
                }
            }
        }
        return appDoctInfoVos;
    }

    /**
     * 收藏显示医生相关信息
     *
     * @param collectionId
     * @return
     */
    @GET
    @Path("find-collection-doct-info")
    public List<AppDoctInfoVo> findCollectionDoctInfo(@QueryParam("collectionId") String collectionId) {
        UserCollection userCollection = this.userCollectionFacade.findById(collectionId);
        if (userCollection != null) {
            String addr = getRequestUrl();
            AppUser appuser = appUserFacade.findAppUserByOpenId(userCollection.getOpenId());
            PatInfo patInfo = patInfoFacade.findById(appuser.getPatId());
            List<AppDoctInfoVo> appDoctInfoVos = new ArrayList<AppDoctInfoVo>();
            AppDoctInfoVo appDoctInfoVo = new AppDoctInfoVo();
            appDoctInfoVo.setId(userCollection.getId());
            DoctInfo doctInfo = doctInfoFacade.findById(userCollection.getDoctId());
            appDoctInfoVo.setDocId(doctInfo == null ? null : doctInfo.getId());
            appDoctInfoVo.setName(doctInfo == null ? null : doctInfo.getName());
            appDoctInfoVo.setTitle(doctInfo == null ? null : doctInfo.getTitle());
            appDoctInfoVo.setHeadUrl(doctInfo == null ? null : addr + doctInfo.getHeadUrl());
            appDoctInfoVo.setDescription(doctInfo == null ? null : doctInfo.getTranDescription2());
            List<ClinicForRegist> clinicForRegists = clinicForRegistFacade.findRegistInfoCollection(sdf.format(new Date()), userCollection.getClinicIndexId());
            List<RegistInfoVO> registInfoVOs = new ArrayList<RegistInfoVO>();
            for (ClinicForRegist c : clinicForRegists) {
                RegistInfoVO registInfoVO = new RegistInfoVO();
                registInfoVO.setCurrentNum(c.getRegistrationNum());
                registInfoVO.setEnabledNum(c.getRegistrationLimits() - c.getRegistrationNum());
                registInfoVO.setRid(c.getId());
                registInfoVO.setPrice(c.getRegistPrice());
                registInfoVO.setTimeDesc(c.getTimeDesc());
                registInfoVOs.add(registInfoVO);
            }
            appDoctInfoVo.setPatName(patInfo.getName());
            appDoctInfoVo.setRegistInfoVOs(registInfoVOs);
            appDoctInfoVos.add(appDoctInfoVo);
            return appDoctInfoVos;
        }
        return null;
    }

    /**
     * 获取请求的路径
     *
     * @return
     */
    private String getRequestUrl() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return addr;
    }
}
