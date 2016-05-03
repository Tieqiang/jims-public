package com.jims.wx.service;

import com.jims.wx.vo.MediaVo;
import com.sun.jersey.core.header.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;
import org.apache.commons.io.FileUtils;
import org.eclipse.jetty.http.HttpTester;
import org.hibernate.annotations.SourceType;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MediaAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.material.Description;
import weixin.popular.bean.material.MaterialBatchgetResult;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.UploadimgResult;
import weixin.popular.bean.message.Article;
import weixin.popular.bean.message.MessageSendResult;
import weixin.popular.bean.message.massmessage.Filter;
import weixin.popular.bean.message.massmessage.MassImageMessage;
import weixin.popular.bean.message.massmessage.MassMPnewsMessage;
import weixin.popular.bean.message.massmessage.MassMessage;
import weixin.popular.bean.message.message.NewsMessage;
import weixin.popular.bean.token.Token;
import weixin.popular.support.TokenManager;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.net.URI;
import java.util.List;

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
    public MaterialBatchgetResult getMediaList(@QueryParam("type") String type) throws IOException {
        MaterialBatchgetResult materialBatchgetResult = MaterialAPI.materialBatchget_material(TokenManager.getDefaultToken(), type, 0, 1000);
        byte[] bytes = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(),"8KeHRodiHmGAgrDEQbupZyiKdWahBi-XoXl32JHWTKI") ;
        File file = new File("d:\\ztq.jpg") ;
        FileOutputStream fileOutputStream= new FileOutputStream(file) ;
        fileOutputStream.write(bytes);
        fileOutputStream.flush();
        fileOutputStream.close();

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
//        return Response.status(Response.Status.OK).entity(uploadimgResult).build();
//    }


    @POST
    @Path("save")
    public Response saveArticle(List<Article> articles){
        String token = TokenManager.getDefaultToken() ;
        for(Article article:articles){
            String imgUrl = article.getThumb_media_id() ;
            URI uri = URI.create(imgUrl) ;
            //Media media = MaterialAPI.materialAdd_material(token, weixin.popular.bean.media.MediaType.image,uri,new Description());
            Media media = MediaAPI.mediaUpload(token, weixin.popular.bean.media.MediaType.image,uri);
            article.setThumb_media_id(media.getMedia_id());
            article.setShow_cover_pic("1");
        }
        //Media media = MaterialAPI.materialAdd_news(TokenManager.getDefaultToken(),articles) ;
        //MessageAPI.messageMassSendall()
        Media media1 = MessageAPI.mediaUploadnews(token,articles) ;
        MassMPnewsMessage massMessage = new MassMPnewsMessage(media1.getMedia_id());
        Filter filter = new Filter(false,"0");
        massMessage.setFilter(filter);

        MessageSendResult messageSendResult = MessageAPI.messageMassSendall(token,massMessage) ;
        return Response.status(Response.Status.OK).entity(media1).build();
    }

}
//8KeHRodiHmGAgrDEQbupZ28dxspcu3KsQ8dtUVSKhog

