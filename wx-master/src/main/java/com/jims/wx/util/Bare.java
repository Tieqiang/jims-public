package com.jims.wx.util;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import weixin.popular.client.LocalHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

/**
 * Created by admin on 2016/4/18.
 */
public class Bare {
    public static void openURL(String url) {
        try {
            browse(url);
        } catch (Exception e) {
        }
    }



    private static void browse(String url) throws Exception {
        //获取操作系统的名字
        String osName = System.getProperty("os.name", "");
        if (osName.startsWith("Mac OS")) {
            //苹果的打开方式
            Class fileMgr = Class.forName("com.apple.eio.FileManager");
            Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
            openURL.invoke(null, new Object[] { url });
        } else if (osName.startsWith("andriod")) {
            //windows的打开方式。
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
        } else {
            // Unix or Linux的打开方式
            String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
            String browser = null;
            for (int count = 0; count < browsers.length && browser == null; count++)
                //执行代码，在brower有值后跳出，
                //这里是如果进程创建成功了，==0是表示正常结束。
                if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
                    browser = browsers[count];
            if (browser == null)
                throw new Exception("Could not find web browser");
            else
                //这个值在上面已经成功的得到了一个进程。
                Runtime.getRuntime().exec(new String[] { browser, url });
        }
    }



    public static void main(String[] args) {
//        HttpPost httpPost = new HttpPost("www.zy91.com/zndz//zyyy/intelligent/bodyList.html");
//        HttpResponse response = null;
//        String content = null;
//
//        try {
//            httpPost.setHeader(new BasicHeader(HttpHeaders.REFERER, "www.zy91.com/zndz//zyyy/intelligent/bodyList.html"));
//            httpPost.setHeader(new BasicHeader(HttpHeaders.CONTENT_TYPE,
//                    "application/x-www-form-urlencoded; charset=UTF-8"));
//            httpPost.setHeader(new BasicHeader("X-Requested-With", "XMLHttpRequest"));
//
//            response = LocalHttpClient.execute(httpPost);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                content = EntityUtils.toString(response.getEntity());
//            }else {
//                throw new Exception("访问错误！");
//            }
//            httpPost.abort();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println(content);
        HttpResponse response = null;
        String content = null;
         HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri("www.zy91.com/zndz//zyyy/intelligent/bodyList.html")
//                 .add
                .build();

         response=LocalHttpClient.execute(httpUriRequest);

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                content = EntityUtils.toString(response.getEntity());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                throw new Exception("访问错误！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(content);
    }
}
