package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.AppUser;
import com.jims.wx.facade.AppUserFacade;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
@Path("app-user")
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
        String url = " https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
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
            JSONObject data = jsonObj.getJSONObject("data");
            System.out.println(data);
            //获取所有关注人的openid，
            if (data != null) {
                if (data != null && !data.equals("")) {
                    JSONArray jsonArray = data.getJSONArray("openid");
                    System.out.println(jsonArray);
                    if (jsonArray != null && jsonArray.length() > 0) {
                        String id = "";
                        String info = "";
                        AppUser obj = new AppUser();
                        List<AppUser> result = new ArrayList<AppUser>();
                        //根据OPENID逐个获取关注人基本信息,并保存在对象里面
                        for (int i = 0; i < jsonArray.length(); i++) {
                            id = (String) jsonArray.get(i);
                            System.out.println(i + "==" + id);
                            url = " https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken + "&openid=" + id + "&lang=zh_CN";
                            url1 = new URL(url);
                            uri = new URI(url1.getProtocol(), url1.getHost(), url1.getPath(), url1.getQuery(), null);
                            response = httpClient.GET(uri);
                            info = response.getContentAsString();
                            System.out.println("info" + info);
                            //......
                            //result.add(obj);
                            //获取了关注人的基本信息以后，获取他所在分组

                        }
                        //把关注人存在数据库
                        //appUserFacade.save(result);
                    }
                }

            }

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
    public List<AppUser> listAll() {
        return appUserFacade.findAll(AppUser.class);
    }
}
