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
        //String url = " https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
        //SslContextFactory sslContextFactory = new SslContextFactory();
        //HttpClient httpClient = new HttpClient(sslContextFactory);
        //try {
        //    httpClient.start();
        //    URL url1 = new URL(url);
        //    URI uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
        //    ContentResponse response = httpClient.GET(uri);
        //    String json = response.getContentAsString();
        //    System.out.println("String content:" + json);
        //
        //
        //    JSONObject jsonObj = new JSONObject(json);
        //    JSONObject data = jsonObj.getJSONObject("data");
        //    System.out.println(data);
        //    //获取所有关注人的openid，
        //    if (data != null) {
        //        if (data != null && !data.equals("")) {
        //            JSONArray jsonArray = data.getJSONArray("openid");
        //            System.out.println(jsonArray);
        //            if (jsonArray != null && jsonArray.length() > 0) {
        //                String id = "";
        //                String info = "";
        //                AppUser user;
        //                List<AppUser> result = new ArrayList<AppUser>();
        //                //根据OPENID逐个获取关注人基本信息,并保存在对象里面
        //                for (int i = 0; i < jsonArray.length(); i++) {
        //                    id = (String) jsonArray.get(i);
        //                    System.out.println(i + "==" + id);
        //                    url = " https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + id + "&lang=zh_CN";
        //                    url1 = new URL(url);
        //                    uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
        //                    response = httpClient.GET(uri);
        //                    info = response.getContentAsString();
        //                    System.out.println("info" + info);
        //                    //将公众平台获取到的用户信息转化成本地数据库对象集合
        //                    JSONObject jsonUserObj = new JSONObject(info);
        //                    if (jsonUserObj != null) {
        //                        user = new AppUser();
        //
        //                        if (null != jsonUserObj.get("openid")) {
        //                            user.setOpenId(jsonUserObj.get("openid").toString());
        //                        }
        //                        if (null != jsonUserObj.get("nickname")) {
        //                            user.setNickName(jsonUserObj.get("nickname").toString());
        //                        }
        //                        if (null != jsonUserObj.get("subscribe")) {
        //                            user.setSubscribe(Integer.parseInt(jsonUserObj.get("subscribe").toString()));
        //                        }
        //                        if (null != jsonUserObj.get("sex")) {
        //                            user.setSex(Integer.parseInt(jsonUserObj.get("sex").toString()));
        //                        }
        //                        if (null != jsonUserObj.get("subscribe_time")) {
        //                            user.setSubscrbeTime(jsonUserObj.get("subscribe_time").toString());
        //                        }
        //                        if (null != jsonUserObj.get("headimgurl")) {
        //                            user.setHeadImgUrl(jsonUserObj.get("headimgurl").toString());
        //                        }
        //                        if (null != jsonUserObj.get("language")) {
        //                            user.setLanguage(jsonUserObj.get("language").toString());
        //                        }
        //                        if (null != jsonUserObj.get("city")) {
        //                            user.setCity(jsonUserObj.get("city").toString());
        //                        }
        //                        if (null != jsonUserObj.get("country")) {
        //                            user.setCountry(jsonUserObj.get("country").toString());
        //                        }
        //                        if (null != jsonUserObj.get("province")) {
        //                            user.setProvince(jsonUserObj.get("province").toString());
        //                        }
        //                        if (null != jsonUserObj.get("remark")) {
        //                            user.setRemark(jsonUserObj.get("remark").toString());
        //                        }
        //                        if (null != jsonUserObj.get("groupid")) {
        //                            user.setGroupId(jsonUserObj.get("groupid").toString());
        //                        }
        //                        user.setAppId(id);
        //
        //                        result.add(user);
        //                    }
        //
        //
        //                    //获取了关注人的基本信息以后，获取他所在分组
        //
        //                }
        //                //把关注人存在数据库
        //                appUserFacade.save(result);
        //            }
        //        }
        //
        //    }
        //
        //    System.out.println(response);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
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

            user = appUserFacade.updateTip(user);
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
