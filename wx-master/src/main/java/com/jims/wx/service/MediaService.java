package com.jims.wx.service;

import com.sun.jersey.core.header.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.http.HttpTester;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MediaAPI;
import weixin.popular.bean.material.Description;
import weixin.popular.bean.material.MaterialBatchgetResult;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.UploadimgResult;
import weixin.popular.bean.token.Token;
import weixin.popular.support.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by heren on 2016/4/19.
 */
@Produces("application/json")
@Path("media")
public class MediaService {

    /**
     * 获取媒体类型
     *
     * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
     */
    @GET
    @Path("list")
    public MaterialBatchgetResult getMediaList(@QueryParam("type") String type) {
        MaterialBatchgetResult materialBatchgetResult = MaterialAPI.materialBatchget_material(TokenManager.getDefaultToken(), type, 0, 1000);
        return materialBatchgetResult;
    }


//    @POST
//    @Path("upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadFiel(@FormDataParam("file") InputStream inputStream,
//                               @FormDataParam("file") FormDataContentDisposition fileDetail) throws FileNotFoundException {
//
//
//        //Media media=MaterialAPI.materialAdd_material(TokenManager.getDefaultToken(), weixin.popular.bean.media.MediaType.image, inputStream, new Description());
//        //Media media = MediaAPI.mediaUpload(TokenManager.getDefaultToken(), weixin.popular.bean.media.MediaType.image,inputStream);
//        UploadimgResult uploadimgResult =MediaAPI.mediaUploadimg(TokenManager.getDefaultToken(),inputStream) ;
//        System.out.println(uploadimgResult.getUrl());
//        return Response.status(Response.Status.OK).build();
//    }


    private void writeToFile(InputStream uploadedInputStream,
                             String uploadedFileLocation) {

        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes));
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }

    }


}


