package com.jims.wx.service.common;


import com.jims.wx.entity.RoleDict;
import com.jims.wx.entity.StaffDict;
import com.jims.wx.entity.StaffVsRole;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.RoleDictFacade;
import com.jims.wx.facade.StaffDictFacade;
import com.jims.wx.util.reps.EnscriptAndDenScript;
import com.jims.wx.vo.StaffDictVo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by heren on 2015/9/22.
 */
@Path("staff-dict")
@Produces("application/json")
public class StaffDictService {

    private StaffDictFacade staffDictFacade ;
    private RoleDictFacade roleDictFacade ;

    @Inject
    public StaffDictService(StaffDictFacade staffDictFacade, RoleDictFacade roleDictFacade) {
        this.staffDictFacade = staffDictFacade;
        this.roleDictFacade = roleDictFacade;
    }

    /**
     * 保存员工信息
     * @param vo
     * @return
     */
    @Path("add")
    @POST
    public Response addStaffDict(StaffDictVo vo){

        try{
            StaffDict staffDict = new StaffDict();
            EnscriptAndDenScript ed = new EnscriptAndDenScript();
            staffDict.setDeptDict(vo.getDeptDict());
            staffDict.setId(vo.getId());
            staffDict.setName(vo.getName());
            staffDict.setJob(vo.getJob());
            staffDict.setLoginName(vo.getLoginName());
            staffDict.setPassword(ed.enScript(vo.getPassword()));
            staffDict.setTitle(vo.getTitle());
            staffDict.setHospitalId(vo.getHospitalId());
            staffDict.setName(vo.getName());
            Set<StaffVsRole> staffVsRoles = new HashSet<>() ;
            for(String str:vo.getIds()){
                RoleDict roleDict = roleDictFacade.get(RoleDict.class, str);
                StaffVsRole staffVsRole = new StaffVsRole() ;
                staffVsRole.setRoleDict(roleDict);
                staffVsRole.setStaffDict(staffDict);
                staffVsRoles.add(staffVsRole) ;
            }
            staffDict.setStaffVsRoles(staffVsRoles);

            StaffDict staffDict1 = staffDictFacade.saveStaffDict(staffDict);
            return Response.status(Response.Status.OK).entity(staffDict1).build();
        }catch (Exception e){
            e.printStackTrace();
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


    @Path("list")
    @GET
    public List<StaffDict> listStaffDict(){
        List<StaffDict> all = staffDictFacade.findAll(StaffDict.class);
        for(StaffDict dict :all){
            Set<StaffVsRole> staffVsRoles = dict.getStaffVsRoles();
            StringBuffer roldIds = new StringBuffer() ;
            StringBuffer roleNames = new StringBuffer() ;
            for (StaffVsRole rol:staffVsRoles){
                roldIds.append(rol.getRoleDict().getId()).append(",") ;
                roleNames.append(rol.getRoleDict().getRoleName()).append(",") ;
            }
            dict.setRoleIds(roldIds.toString());
            dict.setRoleNames(roleNames.toString());
        }
        return all ;
    }


    /***
     * 获取某一个医院为对照的工作人员
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list-no-acct")
    public List<StaffDict> listWithNoAcctDeptId(@QueryParam("hospitalId")String hospitalId){
        String hql = "from StaffDict as staff where staff.acctDeptId is null  order by staff.name " ;
        return staffDictFacade.createQuery(StaffDict.class,hql,new ArrayList<Object>()).getResultList() ;
    }

    /**
     * 根据核算单元查找对应的人员信息
     * @param hospitalId
     * @param acctDeptId
     * @return
     */
    @GET
    @Path("list-with-acct")
    public List<StaffDict> listWithAcctDeptId(@QueryParam("hospitalId")String hospitalId,@QueryParam("acctDeptId")String acctDeptId){
        String hql = "from StaffDict as staff where staff.acctDeptId='"+acctDeptId+"' order by staff.name" ;
        return staffDictFacade.createQuery(StaffDict.class,hql,new ArrayList<Object>()).getResultList() ;
    }

    /**
     * 获取密码
     * @param hospitalId
     * @param loginId
     * @return
     */
    @GET
    @Path("edit-pwd")
    public StaffDict listEditPwdStaffDict(@QueryParam("hospitalId") String hospitalId, @QueryParam("loginId") String loginId){
        StaffDict sd = staffDictFacade.findByLoginId(hospitalId,loginId);
        EnscriptAndDenScript eads = new EnscriptAndDenScript();
        sd.setPassword(eads.denscriptFromHis(sd.getPassword()));
        return sd ;
    }

    @Path("edit-pwd-save")
    @POST
    public Response editPassWordStaffDictById(StaffDict staffDict){
        staffDictFacade.editPasswordByLoginId(staffDict.getHospitalId(), staffDict.getId(), staffDict.getPassword());
        return Response.status(Response.Status.OK).entity(staffDict).build();
    }

    @DELETE
    @Path("del/{id}")
    public Response delDeptDict(@PathParam("id")String id){
        StaffDict staffDict = staffDictFacade.deleteById(id);
        return Response.status(Response.Status.OK).entity(staffDict).build() ;
    }

    @GET
    @Path("list-by-hospital")
    public List<StaffDict> listStaffDict(@QueryParam("hospitalId")String hospitalId, @QueryParam("q") String q){
        List<StaffDict> all = staffDictFacade.findByHospital(hospitalId,q);
        EnscriptAndDenScript ed = new EnscriptAndDenScript();
        for(StaffDict dict :all){
            Set<StaffVsRole> staffVsRoles = dict.getStaffVsRoles();
            StringBuffer roldIds = new StringBuffer() ;
            StringBuffer roleNames = new StringBuffer() ;
            for (StaffVsRole rol:staffVsRoles){
                roldIds.append(rol.getRoleDict().getId()).append(",") ;
                roleNames.append(rol.getRoleDict().getRoleName()).append(",") ;
            }
            dict.setRoleIds(roldIds.toString());
            dict.setRoleNames(roleNames.toString());
            dict.setPassword(ed.denscriptFromHis(dict.getPassword()));
        }
        return all ;
    }

    /**
     * 保存所属核算单元
     * @param staffDicts
     * @return
     */
    @Path("save-acct")
    @POST
    public Response saveDeptDictId(List<StaffDict> staffDicts){
        try{
            List<StaffDict> staffDict1 = staffDictFacade.megerStaffWidthAcct(staffDicts) ;
            return Response.status(Response.Status.OK).entity(staffDict1).build();
        }catch (Exception e){
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

}
