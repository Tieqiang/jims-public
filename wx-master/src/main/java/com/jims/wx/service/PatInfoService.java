package com.jims.wx.service;
import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.PatVsUser;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AppUserFacade;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private AppUserFacade appUserFacade;
    private HttpServletRequest request ;
    private HttpServletResponse response ;

    @Inject
    public PatInfoService(PatInfoFacade patInfoFacade,AppUserFacade appUserFacade,HttpServletRequest request,HttpServletResponse response){
        this.appUserFacade=appUserFacade;
        this.patInfoFacade=patInfoFacade;
        this.request=request;
        this.response=response;
     }

    /**
//     * 保存增改
//      * @param patInfo
     * @return
//     */
//    data:{name:name,idCard:idCard,cellphone:cellphone},
    @GET
    @Path("save")
    public void save(@QueryParam("openId")String openId,@QueryParam("name") String name,@QueryParam("idCard") String idCard,@QueryParam("cellphone") String cellphone) {
        try {
            PatInfo patInfo = new PatInfo();
            patInfo.setCellphone(cellphone);
            patInfo.setIdCard(idCard);
            patInfo.setName(name);
            patInfo = patInfoFacade.save(patInfo);
            if (StringUtils.isNotBlank(openId)) {
                AppUser appUser = appUserFacade.findAppUserByOpenId(openId);
                PatVsUser patVsUser = new PatVsUser();
                patVsUser.setAppUser(appUser);
                patVsUser.setPatInfo(patInfo);
                appUserFacade.savePatVsUser(patVsUser);
            }
            response.sendRedirect("/views/his/public/user-bangker-success.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 }

