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
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.support.TokenManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
@Path("app-user")
@Produces("application/json")
public class AppUserService {

    private AppUserFacade appUserFacade;

    @Inject
    public AppUserService(AppUserFacade appUserFacade) {
        this.appUserFacade = appUserFacade;
    }

    /**
     * 获取所有的关注人信息，保存到数据库
     * @param accessToken
     */
    public void getUsers(String accessToken){
    }

    /**
     * 查询本地用户分组列表
     *
     * @return
     */
    @GET
    @Path("list-all")
    public List<AppUser> listAll() {
        return appUserFacade.findAll(AppUser.class);
    }

    /**
     * 根据分组groupId查找用户
     *
     * @param groupId
     * @return
     */
    @GET
    @Path("get-user-by-id")
    public List<AppUser> listUserByGroupId(@QueryParam("groupId") String groupId) {
        return appUserFacade.findByGroupId(groupId);
    }

    @POST
    @Path("update-tip")
    public Response save(AppUser user) {
        try {
            BaseResult baseResult= UserAPI.userInfoUpdateremark(TokenManager.getDefaultToken(),user.getOpenId(),user.getRemark()) ;
            if("0".equals(baseResult.getErrcode())){
                user = appUserFacade.updateTip(user);
            }
            return Response.status(Response.Status.OK).entity(user).build();
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
