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
            //返回成功
            if(!json.contains("errcode")){
                JSONObject jsonObj = new JSONObject(json);
                //获取所有分组，
                if (jsonObj != null) {
                    JSONArray groupsArray = jsonObj.getJSONArray("groups");
                    System.out.println("groupsArray" + groupsArray);
                    AppUserGroups obj = new AppUserGroups();
                    List<AppUserGroups> result = new ArrayList<AppUserGroups>();
                    //根据OPENID逐个获取关注人基本信息,并保存在对象里面
                    AppUserGroups group;

                    for (int i = 0; i < groupsArray.length(); i++) {
                        JSONObject jo = (JSONObject) groupsArray.get(i);
                        System.out.println();
                        //将公众平台获取到的分组信息转化成本地数据库对象集合
                        group = new AppUserGroups();
                        if (null != jo.get("id")) {
                            group.setGroupId(jo.get("id").toString());
                        }
                        if (null != jo.get("name")) {
                            group.setName(jo.get("name").toString());
                        }
                        if (null != jo.get("count")) {
                            group.setCount(Double.parseDouble(jo.get("count").toString()));
                        }
                        group.setCreateTime(new Date());
                        result.add(group);

                    }
                    //把所有分组信息存在数据库
                    appUserGroupFacade.save(result);
                }
            }

            System.out.println(response.getContentAsString());
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新建分组（网往微信公众平台发建组请求，成功后保存数据）
     *
     */
    @POST
    @Path("add")
    public void addGroup(String group){
        String accessToken = "z9qx_-Pba6qYqppHrcXrpTYfd1SlnEk9dmmTWnMdjSNJqqKiplXVNiDB837FU2Grmt7yog2EWteN-p_dui3vCJT86cFvWiSc5DqeB_9LOlZnMyxJ-idNUWJirVeaut9pTGWeACADQG";
        try {
            //.....从对象转成JSON
            //String groupData = "{\"name\":\"test\"}";
            String groupData = URLDecoder.decode(group, "utf-8");
            System.out.println(groupData);
            String url = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=" + accessToken;
            SslContextFactory sslContextFactory = new SslContextFactory();
            HttpClient httpClient = new HttpClient(sslContextFactory);

            httpClient.start();
            //httpClient.addBean(groupData);

            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

            //Request request = httpClient.POST(uri).param("group", groupData);

            ContentResponse response = httpClient.POST(uri).param("group", groupData).send();
            System.out.println(response.getContentAsString());
            System.out.println(response);
            //保存分组信息到数据库
            if(!response.getContentAsString().contains("errcode")){
                //getGroups(accessToken);
                System.out.println("ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改分组名
     * @param group
     */
    @POST
    @Path("modify")
    public void modGroup(String group) {
        String accessToken = "z9qx_-Pba6qYqppHrcXrpTYfd1SlnEk9dmmTWnMdjSNJqqKiplXVNiDB837FU2Grmt7yog2EWteN-p_dui3vCJT86cFvWiSc5DqeB_9LOlZnMyxJ-idNUWJirVeaut9pTGWeACADQG";
        try {
            //.....从对象转成JSON
            String groupData = "{\"id\":1,\"name\":\"test\"}";//URLDecoder.decode(group, "utf-8");
            System.out.println(groupData);
            String url = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=" + accessToken;
            SslContextFactory sslContextFactory = new SslContextFactory();
            HttpClient httpClient = new HttpClient(sslContextFactory);

            httpClient.start();
            //httpClient.addBean(groupData);

            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

            //Request request = httpClient.POST(uri).param("group", groupData);

            ContentResponse response = httpClient.POST(uri).param("group", groupData).send();
            System.out.println(response.getContentAsString());
            System.out.println(response);
            //修改分组信息到数据库
            if (!response.getContentAsString().contains("ok")) {
                //getGroups(accessToken);
                System.out.println("ok");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除分组
     * @param group
     */
    @POST
    @Path("del")
    public void delGroup(String group) {
        String accessToken = "z9qx_-Pba6qYqppHrcXrpTYfd1SlnEk9dmmTWnMdjSNJqqKiplXVNiDB837FU2Grmt7yog2EWteN-p_dui3vCJT86cFvWiSc5DqeB_9LOlZnMyxJ-idNUWJirVeaut9pTGWeACADQG";
        try {
            //.....从对象转成JSON
            String groupData = "{\"id\":1}";//URLDecoder.decode(group, "utf-8");
            System.out.println(groupData);
            String url = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=" + accessToken;
            SslContextFactory sslContextFactory = new SslContextFactory();
            HttpClient httpClient = new HttpClient(sslContextFactory);

            httpClient.start();
            //httpClient.addBean(groupData);

            URL url1 = new URL(url);
            URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);

            //Request request = httpClient.POST(uri).param("group", groupData);

            ContentResponse response = httpClient.POST(uri).param("group", groupData).send();
            System.out.println(response.getContentAsString());
            System.out.println(response);
            //删除分组信息到数据库
            if (!response.getContentAsString().contains("ok")) {
                //getGroups(accessToken);
                System.out.println("ok");
                //注意本接口是删除一个用户分组，删除分组后，所有该分组内的用户自动进入默认分组。
            }
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



}
