package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.entity.AppUserGroups;
import com.jims.wx.facade.AppUserFacade;
import com.jims.wx.facade.AppUserGroupFacade;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.HttpRequest;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
@Path("app-user-group")
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
     * @param accessToken
     */
    public void getGroups(String accessToken){
        String url = " https://api.weixin.qq.com/cgi-bin/groups/get?access_token=" + accessToken;
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory);
        try {
            httpClient.start();
            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
            ContentResponse response = httpClient.GET(uri);
            String json = response.getContentAsString();
            System.out.println("String content:" + json);


            JSONObject jsonObj = new JSONObject(json);

            //获取所有分组，
            if (jsonObj != null) {
                JSONArray groups = jsonObj.getJSONArray("groups");
                System.out.println("groups" + groups);
                AppUserGroups obj = new AppUserGroups();
                List<AppUserGroups> result = new ArrayList<AppUserGroups>();
                //根据OPENID逐个获取关注人基本信息,并保存在对象里面
                for (int i = 0; i < groups.length(); i++) {
                    //......
                    //result.add(obj);

                }
                //把所有分组信息存在数据库
                //appUserGroupFacade.save(result);
            }
            System.out.println(response.getContentAsString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建分组（网往微信公众平台发建组请求，成功后保存数据）
     * @param accessToken
     */
    public void addGroup(String accessToken){
        //.....从对象转成JSON
        String groupData = "{\"group\":{\"name\":\"test-group\"}}";
        System.out.println(groupData);
        String url = " https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + accessToken;
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory);
        try {
            httpClient.start();
            httpClient.addBean(groupData);
            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
            Request request = httpClient.POST(uri);
            ContentResponse response = request.send();
            System.out.println(response.getContentAsString());
            System.out.println(response);
            //保存分组信息到数据库
            //.....
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改分组名
     * @param accessToken
     */
    public void updateGroup(String accessToken) {
        //.....从对象转成JSON
        String groupData = "{\"group\":{\"id\":1,\"name\":\"modify-group-name\"}}";
        System.out.println(groupData);
        String url = " https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + accessToken;
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory);
        try {
            httpClient.start();
            httpClient.addBean(groupData);
            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
            Request request = httpClient.POST(uri);

            ContentResponse response = request.send();
            System.out.println(response.getContentAsString());
            System.out.println(response);
            //保存分组信息到数据库
            //.....
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询用户所在分组
     * @param accessToken
     */
    public void getGroupId(String accessToken) {
        //.....从对象转成JSON
        String groupData = "{\"openid\":\"oFt1mv4LJLeqt2DNv8Pb08ORohSU\"}";
        System.out.println(groupData);
        String url = " https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=" + accessToken;
        SslContextFactory sslContextFactory = new SslContextFactory();
        HttpClient httpClient = new HttpClient(sslContextFactory);
        try {
            JSONObject jsonObj = new JSONObject(groupData);
            httpClient.start();
            httpClient.addBean(jsonObj);
            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
            ContentResponse response = httpClient.GET(uri);
            System.out.println(response.getContentAsString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询本地用户分组列表
     *
     * @return
     */
    @GET
    @Path("list-all")
    public List<AppUserGroups> listAll() {
        return appUserGroupFacade.findAll(AppUserGroups.class);
    }

    /**
     * 根据分组groupId查找用户
     * @param groupId
     * @return
     */
    @GET
    @Path("get-user-by-id")
    public List<AppUser> listUserByGroupId(@QueryParam("groupId") String groupId) {
        return appUserFacade.findByGroupId(groupId);
    }

}
