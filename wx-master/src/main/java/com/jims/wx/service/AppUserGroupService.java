package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.AppUserGroups;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.AppUserGroupFacade;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.Group;
import weixin.popular.support.TokenManager;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
@Path("app-user-group")
@Produces("application/json")
public class AppUserGroupService {
    private AppUserGroupFacade appUserGroupFacade;
    private AppUserFacade appUserFacade;


    @Inject
    public AppUserGroupService(AppUserGroupFacade appUserGroupFacade, AppUserFacade appUserFacade) {
        this.appUserGroupFacade = appUserGroupFacade;
        this.appUserFacade = appUserFacade;
    }
    /**
     * 从微信公众平台获取所有的分组信息保存到数据库
     *
     */
    @POST
    @Path("syn")
    public void getGroups(){
        String token = TokenManager.getDefaultToken() ;
        Group group  = UserAPI.groupsGet(token) ;
        FollowResult followResult = UserAPI.userGet(token,"") ;
        appUserGroupFacade.synUserGroup(group,followResult) ;
    }

    /**
     * 新建分组（网往微信公众平台发建组请求，成功后保存数据）
     */
    @POST
    @Path("add")
    public void addGroup(String group){
        Group g = UserAPI.groupsCreate(TokenManager.getDefaultToken(),group) ;
        if(g.getErrcode() ==null){
            appUserGroupFacade.addUserGroup(g) ;
        }
    }

    /**
     * 修改分组名
     * @param id
     * @param groupName
     */
    @POST
    @Path("modify")
    public void modifyGroup(@QueryParam("id")String id,@QueryParam("groupName")String groupName) {
        BaseResult baseResult = UserAPI.groupsUpdate(TokenManager.getDefaultToken(),id,groupName) ;
        if("0".equals(baseResult.getErrcode())){
            appUserGroupFacade.modifyGroup(id,groupName) ;
        }
    }

    /**
     * 删除分组
     * @param groupId
     */
    @POST
    @Path("del")
    public void delGroup(String groupId) {
        BaseResult baseResult = UserAPI.groupsDelete(TokenManager.getDefaultToken(),groupId) ;
        if("0".equals(baseResult.getErrcode())){
            appUserGroupFacade.deleteUserGroup(groupId) ;
        }
    }

    /**
     * 查询用户所在分组
     * @param accessToken
     */
    public void getGroupId(String accessToken) {

    }

    /**
     * 查询本地用户分组列表
     * @return
     */
    @GET
    @Path("list-all")
    public List<AppUserGroups> listAll() {
        return appUserGroupFacade.findAll(AppUserGroups.class);
    }


    /**
     * 移动用户所在的分组
     * @return
     */
    @POST
    @Path("move-group/{openId}/{targetId}/{currentId}")
    public Response moveUserGroup(@PathParam("openId")String openId,@PathParam("targetId")String targetId,
                                  @PathParam("currentId")String currentId){
        BaseResult baseResult = UserAPI.groupsMembersUpdate(TokenManager.getDefaultToken(), openId, targetId) ;
        if("0".equals(baseResult.getErrcode())){
            appUserFacade.updateGroup(openId,targetId,currentId) ;
        }
        return null ;
    }


}
