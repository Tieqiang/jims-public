package com.jims.wx.service.common;

import com.jims.wx.entity.MenuDict;
import com.jims.wx.entity.ModulDict;
import com.jims.wx.entity.ModuleVsMenu;
import com.jims.wx.facade.MenuDictFacade;
import com.jims.wx.facade.ModuleDictFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by heren on 2015/9/11.
 */
@Path("menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuDictService {

    private MenuDictFacade menuDictFacade ;
    private ModuleDictFacade moduleDictFacade ;


    @Inject
    public MenuDictService(MenuDictFacade menuDictFacade, ModuleDictFacade moduleDictFacade){
        this.menuDictFacade = menuDictFacade ;
        this.moduleDictFacade = moduleDictFacade;
    }

    @GET
    @Path("list")
    public List<MenuDict> findAllMenuDict(){
        List<MenuDict> all = menuDictFacade.findAllByPosition();
        return all ;
    }

    /**
     * 根据模块加载此模块的菜单
     * @param moduleId
     * @return
     */
    @Path("list-by-module")
    @GET
    public List<MenuDict> findByModule(@QueryParam("moduleId")String moduleId ){
        ModulDict modulDict = moduleDictFacade.get(ModulDict.class,moduleId) ;
        Set<ModuleVsMenu> moduleVsMenus = modulDict.getModuleVsMenus();
        List<MenuDict> menuDicts = new ArrayList<>() ;
        for(ModuleVsMenu moduleVsMenu:moduleVsMenus){
            menuDicts.add(moduleVsMenu.getMenuDict()) ;
        }

        return menuDicts ;
    }

    @Path("list-login-module")
    @GET
    public List<MenuDict> findByLoginNameAndModuleId(@QueryParam("moduleId")String moduleId ,@QueryParam("loginName")String loginName){
        return menuDictFacade.findByLoginNameAndModuleId(moduleId,loginName) ;
    }
    /**
     * 添加一个菜单项
     * @param menuDict
     * @return
     */
    @POST
    @Path("add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMenuDict(MenuDict menuDict){
        MenuDict md = menuDictFacade.addMenuDict(menuDict) ;
        return Response.status(Response.Status.OK).entity(md).build() ;
    }


    /**
     * 删除一个菜单项
     * @param id
     * @return
     */
    @POST
    @Path("del/{id}")
    public Response deleteMenuDict(@PathParam("id")String id){
        MenuDict menuDict = menuDictFacade.deleteMenuDictById(id) ;
        return Response.status(Response.Status.OK).entity(menuDict).build() ;
    }


    /**
     * 修改一个菜单想
     * @param menuDict
     * @return
     */
    @PUT
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateMenuDict(MenuDict menuDict){
        return Response.status(Response.Status.OK).entity(menuDict).build() ;
    }


}
