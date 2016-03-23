package com.jims.wx.service.common;




import com.jims.wx.entity.RoleDict;
import com.jims.wx.entity.RoleVsMenu;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.RoleDictFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by heren on 2015/9/16.
 */
@Path("role-dict")
@Produces("application/json")
public class RoleDictService {

    private RoleDictFacade roleDictFacade ;

    @Inject
    public RoleDictService(RoleDictFacade roleDictFacade) {
        this.roleDictFacade = roleDictFacade;
    }

    @GET
    @Path("list")
    public List<RoleDict> expRoleDict(@QueryParam("name") String name){
        List<RoleDict> all = roleDictFacade.findAll(name);
        for(RoleDict dict:all){
            Set<RoleVsMenu> roleVsMenus = dict.getRoleVsMenus();
            StringBuffer menuIds = new StringBuffer();
            for(RoleVsMenu rol:roleVsMenus){
                menuIds.append(rol.getMenuDict().getId()).append(",");
            }
            dict.setMenuIds(menuIds.toString());
        }
        return all ;
    }




    @Path("add")
    @POST
    public Response addRoleDict(List<RoleDict> roleDicts){
        try{
            List<RoleDict> dicts = roleDictFacade.saveRoleDict(roleDicts, new ArrayList<RoleDict>(), new ArrayList<RoleDict>());
            return Response.status(Response.Status.OK).entity(dicts).build() ;
        }catch (Exception e){
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


    @Path("add-role-menu/{roleId}")
    @POST
    public Response addRoleDictVsMenuDict(@PathParam("roleId")String roleId,String[] menuId){
        try{
            System.out.println(roleId);
            System.out.println(menuId);
            RoleDict roleDict= roleDictFacade.saveRoleVsMenus(roleId,menuId) ;

            return Response.status(Response.Status.OK).entity(menuId).build() ;
        }catch (Exception e){
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


    @Path("delete")
    @POST
    public Response delRoleDict(List<RoleDict> roleDicts){
        try{
            List<RoleDict> dicts = roleDictFacade.saveRoleDict(new ArrayList<RoleDict>(), new ArrayList<RoleDict>(), roleDicts);
            return Response.status(Response.Status.OK).entity(dicts).build() ;
        }catch (Exception e){
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
