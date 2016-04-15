package com.jims.wx.service.common;



import com.jims.wx.entity.AppConfigerParameter;
import com.jims.wx.facade.AppConfigerParameterFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by txb on 2015/10/21.
 */
@Path("app-configer-parameter")
@Produces("application/json")
public class AppConfigerParameterService {
    private AppConfigerParameterFacade appConfigerParameterFacade;

    @Inject
    public AppConfigerParameterService(AppConfigerParameterFacade appConfigerParameterFacade) {
        this.appConfigerParameterFacade = appConfigerParameterFacade;
    }

    @GET
    @Path("list")
    public List<AppConfigerParameter> list(@QueryParam("hospitalId") String hospitalId, @QueryParam("name") String name){
        return appConfigerParameterFacade.findCurParameterList(hospitalId,name);
    }

    @POST
    @Path("save")
    public Response save(AppConfigerParameter appConfigerParameter){
        AppConfigerParameter parameter= appConfigerParameterFacade.saveAppConfigerParameter(appConfigerParameter);
        return Response.status(Response.Status.OK).entity(parameter).build();
    }

    @POST
    @Path("delete")
    public Response delete(AppConfigerParameter appConfigerParameter){
        AppConfigerParameter parameter = appConfigerParameterFacade.deleteAppConfigerParameter(appConfigerParameter);
        return Response.status(Response.Status.OK).entity(parameter).build();
    }

    
}
