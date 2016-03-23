package com.jims.wx.service.common;


import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ModuleDictFacade;
import com.jims.wx.facade.RoleDictFacade;
import com.jims.wx.facade.StaffDictFacade;
import com.jims.wx.util.reps.EnscriptAndDenScript;
import com.jims.wx.vo.Config;
import com.jims.wx.vo.ErrorMessager;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by heren on 2015/9/22.
 */
@Path("login")
@Produces("application/json")
public class LoginService {
    private ModuleDictFacade moduleDictFacade;
    private StaffDictFacade staffDictFacade ;
    private RoleDictFacade roleDictFacade ;
    private HttpServletResponse resp;
    private HttpServletRequest request;



    @Inject
    public LoginService(HttpServletRequest request, HttpServletResponse resp, ModuleDictFacade moduleDictFacade, StaffDictFacade staffDictFacade, RoleDictFacade roleDictFacade) {
        this.moduleDictFacade = moduleDictFacade;
        this.staffDictFacade = staffDictFacade;
        this.roleDictFacade = roleDictFacade;
        this.resp = resp;
        this.request = request;
    }

    /**
     * 根据用户名密码医院查找用户
     * @param loginName
     * @param password
     * @param hospitalId
     * @return
     */
    @POST
    @Path("login")
    public StaffDict loginStaffDict(@FormParam("loginName") String loginName, @FormParam("password") String password, @FormParam("validateCode") String validateCode,@FormParam("hospitalId")String hospitalId){
        HttpSession session = request.getSession();
        StaffDict staff = new StaffDict();

        return staff;
    }

    @POST
    @Path("check-login")
    public Response loing(@QueryParam("loginName")String loginName ,@QueryParam("password")String password){
        try{
            StaffDict staffDict= staffDictFacade.findByLoginName(loginName) ;
            if(staffDict==null){
                ErrorMessager errorMessager = new ErrorMessager("错误的用户名", "系统登录");
                return Response.status(Response.Status.OK).entity(errorMessager).build() ;
            }
            String denPassword = EnscriptAndDenScript.denscriptFromHis(staffDict.getPassword()) ;
            if(!denPassword.equals(password)){
                ErrorMessager errorMessager = new ErrorMessager("错误的密码", "系统登录");
                return Response.status(Response.Status.OK).entity(errorMessager).build() ;
            }else{
                HttpSession session = request.getSession() ;
                session.setAttribute("loginName",staffDict.getLoginName());
                session.setAttribute("staffName", staffDict.getName());
                session.setAttribute("loginId",staffDict.getId());
                session.setAttribute("deptId",staffDict.getDeptDict().getId());
                return Response.status(Response.Status.OK).entity(staffDict).build() ;
            }
        }catch(Exception e){
            ErrorException errorException= new ErrorException() ;
            errorException.setMessage(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build() ;
        }
    }
    /**
     * 根据staffId查询staff-vs-role表,查出用户的role-dict,再根据role查role-vs-menu表查出用户的menu集合
     * @param staffId
     * @return
     */
    public List<MenuDict> listMenuByStaffRole(String staffId){
        List<MenuDict> result = new ArrayList<>();
        //根据staffId查找出所有的staffVsRole
        List<StaffVsRole> staffRoleList = staffDictFacade.listRolesByStaff(staffId);
        if(null != staffRoleList && staffRoleList.size() > 0){
            Iterator<StaffVsRole> staffRoleListIte = staffRoleList.iterator();
            while(staffRoleListIte.hasNext()){
                StaffVsRole staffRole = staffRoleListIte.next();
                //根据roleId取出所有的RoleVsMenu
                List<RoleVsMenu> roleMenuList= roleDictFacade.listMenusByRole(staffRole.getRoleDict().getId());
                if(null != roleMenuList && roleMenuList.size() > 0){
                    Iterator<RoleVsMenu> roleMenuListIte = roleMenuList.iterator();
                    while (roleMenuListIte.hasNext()) {
                        RoleVsMenu roleMenu = roleMenuListIte.next();
                        //把得到的menu放到返回集合
                        result.add(roleMenu.getMenuDict());
                    }
                }
            }
        }
        return result;
    }

    /**
     * 根据模块查modul-vs-menu选择模块对应的menu集合
     * @param moduleId
     * @return
     */
    public List<MenuDict> listMenuByModule(String moduleId) {
        List<MenuDict> result = new ArrayList<>();
        List<ModuleVsMenu> moduleMenuList =moduleDictFacade.listMenusByModule(moduleId);
        if(null != moduleMenuList && moduleMenuList.size() > 0){
            Iterator<ModuleVsMenu> moduleMenuListIte = moduleMenuList.iterator();
            while(moduleMenuListIte.hasNext()){
                ModuleVsMenu moduleMenu = moduleMenuListIte.next();
                result.add(moduleMenu.getMenuDict());
            }
        }
        return result;
    }

    /**
     * 根据交集找出menus
     * @param staffId
     * @param moduleId
     * @return
     */
    @GET
    @Path("list-menu-by-staff")
    public List<MenuDict> listMenuByStaff(@QueryParam("staffId") String staffId, @QueryParam("moduleId") String moduleId) {
        List<MenuDict> byRole = listMenuByStaffRole(staffId);
        List<MenuDict> byModule = listMenuByModule(moduleId);
        if(null != byRole && null != byModule){
            byRole.retainAll(byModule);
        }
        return byRole;
    }





    @Path("add-login-info")
    @POST
    public Config addLoginInfo(Config config){
        HttpSession session = request.getSession();
        if(null != config.getHospitalId() && !config.getHospitalId().trim().equals("")){
            HospitalDict hospitalDict = staffDictFacade.get(HospitalDict.class, config.getHospitalId());
            session.setAttribute("hospitalId", hospitalDict.getId());
            session.setAttribute("hospitalName", hospitalDict.getHospitalName());
            config.setHospitalName(hospitalDict.getHospitalName());
        }
        if(null != config.getModuleId() && !config.getModuleId().trim().equals("")){
            ModulDict modulDict = staffDictFacade.get(ModulDict.class, config.getModuleId());
            session.setAttribute("moduleId", modulDict.getId());
            session.setAttribute("moduleName", modulDict.getModuleName());
            config.setModuleName(modulDict.getModuleName());
        }
        return config ;

    }

    @Path("get-login-info")
    @GET
    public Config getLoginInfo(){
        Config config = new Config() ;
        HttpSession session = request.getSession();
        config.setHospitalId((String) session.getAttribute("hospitalId"));
        config.setHospitalName((String)session.getAttribute("hospitalName"));
        config.setModuleName((String)session.getAttribute("moduleName"));
        config.setModuleId((String)session.getAttribute("moduleId"));
        config.setLoginId((String)session.getAttribute("loginId"));
        config.setLoginName((String)session.getAttribute("loginName"));
        config.setStaffName((String) session.getAttribute("staffName"));
        config.setStorageCode((String) session.getAttribute("storageCode"));
        config.setStorageName((String) session.getAttribute("storageName"));
        config.setAcctDeptId((String)session.getAttribute("acctDeptId"));
        config.setDefaultReportPath("http://192.168.6.68:8080/webReport/ReportServer?reportlet=");
        return config ;
    }
}


