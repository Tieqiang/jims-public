package com.jims.wx.service;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MediaAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.material.Description;
import weixin.popular.bean.material.MaterialBatchgetResult;
import weixin.popular.bean.material.MaterialBatchgetResultItem;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.message.Article;
import weixin.popular.bean.message.MessageSendResult;
import weixin.popular.bean.message.massmessage.Filter;
import weixin.popular.bean.message.massmessage.MassMPnewsMessage;
import weixin.popular.support.TokenManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by heren on 2016/4/19.
 */
@Produces("application/json")
@Path("material")
public class MaterialService {
    private static final String BASE_MATERIAL_PATH = "e:\\weixin\\material"; //素材保存到本地的根目录
    private String token = TokenManager.getDefaultToken();                        //公众号唯一凭证
    /**
     * 获取永久素材列表
     * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     */
    @GET
    @Path("list")
    public MaterialBatchgetResult getMediaList(@QueryParam("type") String type) throws IOException {
        MaterialBatchgetResult materialBatchgetResult = MaterialAPI.materialBatchget_material(token, type, 0, 1000);
        List<MaterialBatchgetResultItem> items = materialBatchgetResult.getItem();
        for (MaterialBatchgetResultItem item : items) {
            System.out.println("item.name:" + item.getName());
            System.out.println("item.media_id:" + item.getMedia_id());
            System.out.println("item.update_time:" + item.getUpdate_time());
            System.out.println("url:" + item.getUrl());
        }
        return materialBatchgetResult;
    }

    /**
     * 新增永久图片素材
     * @param inputStream
     * @param fileDetail
     * @return
     * @throws java.io.FileNotFoundException
     */
    @POST
    @Path("upload-img")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFiel(@FormDataParam("file")InputStream inputStream,
                               @FormDataParam("file")FormDataContentDisposition fileDetail) throws IOException {
        //上传图片获取mediaId
        Media media=MaterialAPI.materialAdd_material(token, weixin.popular.bean.media.MediaType.image,inputStream, new Description());

        System.out.println("fileDetail-name:" + fileDetail.getName());  //file
        System.out.println("fileDetail-type:" + fileDetail.getType());  //form-data
        System.out.println("fileDetail-fileName:" + fileDetail.getFileName());  //csh.jpg
        System.out.println("mediaId:" + media.getMedia_id());
        System.out.println("type:" + media.getType());
        System.out.println("createdAt:" + media.getCreated_at());
        System.out.println("url:" + media.getUrl());

        //保存到本地
        byte[] bytes = MaterialAPI.materialGet_material(token, media.getMedia_id());    //获取到上传的图片文件
        //设置保存到本地的路径
        Date now = new Date();
        DateFormat dfp = new SimpleDateFormat("yyyyMMdd");
        String fullPath = BASE_MATERIAL_PATH + "\\" + dfp.format(now) + "\\image";
        File directoryPath = new File(fullPath);
        if (!directoryPath.exists()) {      //如果路径不存在，先创建
            directoryPath.mkdirs();
        }
        //设置保存文件名
        DateFormat dfn = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = new String(fullPath + "\\" + dfn.format(now) + ".jpg");
        File file = new File(fileName);
        file.createNewFile();
        //写到文件
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

        return Response.status(Response.Status.OK).entity(media).build();
    }

    @POST
    @Path("del")
    public Response delMaterial(String media_id){
        BaseResult result = MaterialAPI.materialDel_material(token, media_id);

        return Response.status(Response.Status.OK).entity(BaseResult.class).build();
    }

}