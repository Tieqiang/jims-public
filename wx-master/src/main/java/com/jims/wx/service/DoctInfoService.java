package com.jims.wx.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.entity.DeptDict;
import com.jims.wx.entity.DoctInfo;
import com.jims.wx.entity.Subject;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.DoctInfoFacade;
import com.jims.wx.facade.HospitalDictFacade;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.env.SystemEnvironmentPropertySource;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.*;

/**
 * Created by chenxiaoyang on 2016/3/25.
 */
@Path("doct-info")
@Produces("application/json")
public class DoctInfoService {

    private DoctInfoFacade doctInfoFacade;
    private HttpServletRequest request;
    private HospitalDictFacade hospitalDictFacade;

    @Inject
    public DoctInfoService(DoctInfoFacade doctInfoFacade, HttpServletRequest request,HospitalDictFacade hospitalDictFacade) {
        this.doctInfoFacade = doctInfoFacade;
        this.request=request;
        this.hospitalDictFacade=hospitalDictFacade;
    }
     /**
     * 根据科室查询医生集合
     * @return
     */
    @GET
    @Path("find-by-dept-id")
    public List<DoctInfo> findByDeptId(@QueryParam("deptId") String deptId) {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<DoctInfo> doctInfos=new ArrayList<DoctInfo>();
        List<DoctInfo> list= doctInfoFacade.findAll(DoctInfo.class);
        for(DoctInfo doctInfo:list){
            doctInfo.setHeadUrl(addr+doctInfo.getHeadUrl());
            doctInfo.setImg(doctInfo.getHeadUrl());
            doctInfos.add(doctInfo);
        }
        return doctInfos;
    }






    /**
     * 查询全部医生信息
     * @return
     */
    @GET
    @Path("get-list")
    public List<DoctInfo> getList() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<DoctInfo> deptDicts = new ArrayList<DoctInfo>();
        List<DoctInfo> list= doctInfoFacade.findAll(DoctInfo.class);
        for(DoctInfo doctInfo:list){
            doctInfo.setHospitalName(hospitalDictFacade.findHospitalDictById(doctInfo.getHospitalId()).getHospitalName());
            doctInfo.setImg(addr+doctInfo.getHeadUrl());
            deptDicts.add(doctInfo);
         }
        return deptDicts;
    }

    /**
     * 条件查询医生相关信息
     * @param name
     * @param hospitalId
     * @return
     */
    @GET
    @Path("query-by-condition")
    public List<DoctInfo> queryByCondition(@QueryParam("name") String name,@QueryParam("hospitalId") String hospitalId) {
        return  doctInfoFacade.queryByCondition(DoctInfo.class,name,hospitalId);
    }
    /**
     * 保存增改
     * @param doctInfo
     * @return
     */
    @POST
    @Path("save")
    public Response save(@QueryParam("description") String description, DoctInfo doctInfo) {
        try {

            doctInfo.setDescription(description.getBytes("UTF-8"));
            doctInfo = doctInfoFacade.save(doctInfo);
            return Response.status(Response.Status.OK).entity(doctInfo).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }

    /**
     * 根据ids批量或单条删除数据
     * @param ids
     * @return
     */
    @POST
    @Path("delete")
    public Response delete(@QueryParam("ids") String ids) {
        try {
            String[] idStr = ids.split(",");
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < idStr.length; i++) {
                list.add(idStr[i]);
            }
            doctInfoFacade.removeByIds(DoctInfo.class, list);
            return Response.status(Response.Status.OK).entity(list).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }
}
