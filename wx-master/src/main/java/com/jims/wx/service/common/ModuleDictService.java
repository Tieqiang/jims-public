package com.jims.wx.service.common;



import com.jims.wx.entity.ModulDict;
import com.jims.wx.entity.ModuleVsMenu;
import com.jims.wx.entity.StaffVsModule;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.ModuleDictFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by heren on 2015/10/26.
 */
@Path("module-dict")
@Produces("application/json")
public class ModuleDictService {

    private ModuleDictFacade moduleDictFacade ;
    private HttpServletResponse resp;
    private HttpServletRequest request;
    private HttpSession httpSession ;

    @Inject
    public ModuleDictService(HttpServletRequest request, HttpServletResponse resp, ModuleDictFacade moduleDictFacade, HttpSession httpSession) {
        this.request = request;
        this.resp = resp;
        this.moduleDictFacade = moduleDictFacade;
        this.httpSession = httpSession;
    }

    /**
     * 获取所有的模块
     * @return
     */
    @GET
    @Path("list")
    public List<ModulDict> list(@QueryParam("name") String name, @QueryParam("hospitalId") String hospitalId){
        return moduleDictFacade.findAll(name, hospitalId) ;
    }
    /**
     * 获取模块所对应的路径
     * @return
     */
    @GET
    @Path("list-tabs")
    public List<ModulDict> listTabs(){
        String hospitalId = (String)httpSession.getAttribute("hospitalId") ;
        String moduleId = (String)httpSession.getAttribute("moduleId") ;
        String loginId = (String)httpSession.getAttribute("loginId") ;
        return moduleDictFacade.findAllTabs(loginId,moduleId, hospitalId) ;
    }

    @GET
    @Path("list-by-staff")
    public List<ModulDict> listByStaff(@QueryParam("hospitalId") String hospitalId) {
        HttpSession session = request.getSession();
        String staffId = (String) session.getAttribute("loginId");
        return moduleDictFacade.findByStaff(staffId, hospitalId);
    }

    @POST
    @Path("save")
    public Response save(BeanChangeVo<ModulDict> modulDictBeanChangeVo){
        try {
            moduleDictFacade.save(modulDictBeanChangeVo) ;
            return Response.status(Response.Status.OK).entity(modulDictBeanChangeVo).build() ;
        }catch(Exception e){
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
            return Response.status(Response.Status.OK).entity(errorException).build();
        }
    }


    /**
     * 保存模块菜单
     * @param moduleId 模块ID
     * @param menuIds 菜单ID
     * @return
     */
    @Path("add-module-menu/{moduleId}")
    @POST
    public Response saveModuleVsMenu(@PathParam("moduleId")String moduleId,List<String> menuIds){
        try {
            List<ModuleVsMenu> moduleVsMenus = moduleDictFacade.saveModuleVsMenu(moduleId, menuIds);
            return Response.status(Response.Status.OK).entity(moduleVsMenus).build() ;
        }catch(Exception e){
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
     * 保存模块菜单
     *
     * @param moduleId 模块ID
     * @param staffIds  staffID
     * @return
     */
    @Path("add-module-staff/{moduleId}")
    @POST
    public Response saveStaffVsModule(@PathParam("moduleId") String moduleId, List<String> staffIds) {
        try {
            List<StaffVsModule> staffVsModules = moduleDictFacade.saveStaffVsModule(moduleId, staffIds);
            return Response.status(Response.Status.OK).entity(staffVsModules).build();
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

}
