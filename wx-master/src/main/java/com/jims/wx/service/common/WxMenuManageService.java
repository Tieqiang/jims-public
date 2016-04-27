package com.jims.wx.service.common;

import com.google.inject.Inject;
import org.hibernate.annotations.SourceType;
import weixin.popular.api.MenuAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.menu.Menu;
import weixin.popular.bean.menu.MenuButtons;
import weixin.popular.support.TokenManager;
import weixin.popular.util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;

/**
 * Created by lgx on 2016/4/22.
 */
@Path("wx-menu")
@Produces(MediaType.APPLICATION_JSON)
public class WxMenuManageService {


    @POST
    @Path("createMenu")
    public BaseResult createMenu(@QueryParam("menu") String menu){
        return MenuAPI.menuCreate(TokenManager.getDefaultToken(), menu);
    }

    @GET
    @Path("getMenu")
    public Menu getMenu(){
        return MenuAPI.menuGet(TokenManager.getDefaultToken());
    }

    @GET
    @Path("deleteMenu")
    public BaseResult deleteMenu(){
        return MenuAPI.menuDelete(TokenManager.getDefaultToken());
    }

}
