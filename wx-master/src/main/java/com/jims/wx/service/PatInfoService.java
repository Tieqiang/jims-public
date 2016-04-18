package com.jims.wx.service;
import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AppUserFacade;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.google.inject.Inject;
import com.jims.wx.entity.PatInfo;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.PatInfoFacade;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created by chenxy on 2016/4/17.
 */
@Path("pat-info")
@Produces("application/json")
public class PatInfoService {

    private PatInfoFacade patInfoFacade;

    @Inject
    public PatInfoService(PatInfoFacade patInfoFacade){
        this.patInfoFacade=patInfoFacade;
    }

    /**
//     * 保存增改
//      * @param patInfo
     * @return
//     */
//    data:{name:name,idCard:idCard,cellphone:cellphone},
    @POST
    @Path("save")
    public Response save(PatInfo patInfo){
        try {
            System.out.println(1);
            System.out.println(1);
            System.out.println(patInfo);
//            patInfo = patInfoFacade.save(patInfo);
            return Response.status(Response.Status.OK).entity(null).build();
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
