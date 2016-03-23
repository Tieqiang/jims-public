package com.jims.wx.service.common;


import com.jims.wx.entity.AppConfigerBaseinfo;
import com.jims.wx.facade.AppConfigerBaseinfoFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by txb on 2015/10/21.
 */
@Path("app-configer-baseinfo")
@Produces("application/json")
public class AppConfigerBaseinfoService {
    private AppConfigerBaseinfoFacade appConfigerBaseinfoFacade;

    @Inject
    public AppConfigerBaseinfoService(AppConfigerBaseinfoFacade appConfigerBaseinfoFacade) {
        this.appConfigerBaseinfoFacade = appConfigerBaseinfoFacade;
    }

    @GET
    @Path("list")
    @Produces("application/json")
    public List<AppConfigerBaseinfo> listBaseDict(@QueryParam("name") String name){
        return appConfigerBaseinfoFacade.findAll(name) ;
    }

    //保存
    @POST
    @Path("save")
    public Response save(AppConfigerBaseinfo appConfigerBaseinfo){
        if (appConfigerBaseinfo.getParameterNo() == null){
            appConfigerBaseinfo.setParameterNo(appConfigerBaseinfoFacade.findMaxParameterNo().intValue() +  1);
        }
        AppConfigerBaseinfo baseinfo = appConfigerBaseinfoFacade.saveAppConfigerBaseinfo(appConfigerBaseinfo);
        return Response.status(Response.Status.OK).entity(baseinfo).build();
    }
    //删除
    @POST
    @Path("delete")
    public Response delete(AppConfigerBaseinfo appConfigerBaseinfo){
        AppConfigerBaseinfo baseinfo = appConfigerBaseinfoFacade.deleteAppConfigerBaseinfo(appConfigerBaseinfo);
        return Response.status(Response.Status.OK).entity(baseinfo).build();
    }
}
