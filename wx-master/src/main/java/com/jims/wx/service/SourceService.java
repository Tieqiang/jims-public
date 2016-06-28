package com.jims.wx.service;


import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.MessageVo;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.material.Description;
import weixin.popular.bean.material.MaterialBatchgetResult;
import weixin.popular.bean.material.MaterialBatchgetResultItem;
import weixin.popular.bean.material.MaterialcountResult;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.*;
import weixin.popular.bean.message.Article;
import weixin.popular.bean.message.massmessage.*;
import weixin.popular.bean.xmlmessage.XMLMessage;
import weixin.popular.bean.xmlmessage.XMLTextMessage;
import weixin.popular.support.TokenManager;

import javax.inject.Inject;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * cxy
 */
@Path("source")
@Produces("application/json")
public class SourceService {
    private SourceImageFacade sourceImageFacade;
    private HttpServletRequest request;
    private SourceVideoFacade sourceVideoFacade;
    private SourceImageFontFacade sourceImageFontFacade;
    private RequestMessageFacade requestMessageFacade;
    private HttpServletResponse response;


    @Inject
    public SourceService(SourceImageFacade sourceImageFacade, HttpServletRequest request, SourceVideoFacade sourceVideoFacade, SourceImageFontFacade sourceImageFontFacade, RequestMessageFacade requestMessageFacade, HttpServletResponse response) {
        this.sourceImageFacade = sourceImageFacade;
        this.request = request;
        this.sourceVideoFacade = sourceVideoFacade;
        this.sourceImageFontFacade = sourceImageFontFacade;
        this.requestMessageFacade = requestMessageFacade;
        this.response = response;
    }

    /**
     * 上传图文消息
     * 一次保存一个
     *
     * @param sourceImageFont
     * @param content
     * @return
     */
    @POST
    @Path("save-image-font")
    public Response saveImageFont(SourceImageFont sourceImageFont, @QueryParam("content") String content) {
        try {
            byte[] bytes = content.getBytes("UTF-8");
            sourceImageFont.setContent(bytes);
            List<weixin.popular.bean.message.Article> articles = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle(sourceImageFont.getTitle());
            article.setThumb_media_id(sourceImageFont.getThumbMediaId());
            article.setAuthor(sourceImageFont.getAuthor());
            article.setContent(content);
            article.setDigest(sourceImageFont.getDigest());
            article.setShow_cover_pic("1");//默认显示封面
            articles.add(article);
            Media media = MaterialAPI.materialAdd_news(TokenManager.getDefaultToken(), articles);
            if (media.getErrmsg() != null && !"".equals(media.getErrcode())) {
                ErrorException errorException = new ErrorException();
                errorException.setMessage(new IllegalArgumentException());
                errorException.setErrorMessage(media.getErrmsg());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
            }
            sourceImageFont.setMediaId(media.getMedia_id());
            sourceImageFont.setContent(bytes);
            sourceImageFont = sourceImageFontFacade.save(sourceImageFont);
            return Response.status(Response.Status.OK).entity(sourceImageFont).build();
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

    /**
     * 上传图片素材
     *
     * @param updateFlag
     * @param sourceImage
     * @return
     */
    @POST
    @Path("save")
    public Response save(@QueryParam("update") String updateFlag, SourceImage sourceImage, @QueryParam("saveName") String saveName) {
        try {
            if (updateFlag != null && !"".equals(updateFlag)) {
                sourceImage = sourceImageFacade.save(sourceImage);
            }
            if (updateFlag == null || "".equals(updateFlag)) {//新加的要上传到微信服务器
                String path = request.getSession().getServletContext().getRealPath("/upload");
                File file = new File(path);
                File[] files = file.listFiles();
                for (File file1 : files) {
                    if (file1.getName().equals(saveName)) {
                        InputStream inputStream = new FileInputStream(file1);
                        Media media = MaterialAPI.materialAdd_material(TokenManager.getDefaultToken(), MediaType.image, inputStream, new Description());
                        if (media.getErrmsg() != null && !"".equals(media.getErrcode())) {
                            sourceImage.setImageWxUrl(media.getUrl());
                            sourceImage.setMediaId(media.getMedia_id());
                            sourceImage = sourceImageFacade.save(sourceImage);
                            return Response.status(Response.Status.OK).entity(sourceImage).build();
                        }
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sourceImage).build();
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(sourceImage).build();
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

    /**
     * 获取本地图片素材库
     *
     * @return
     */
    @GET
    @Path("list")
    public List<SourceImage> list() {
        //  如果从微信公众号上操作 本地将保存新增的图片素材
        List<String> medias = new ArrayList<String>();
        //获取微信服务器上的总数
        MaterialcountResult materialcountResult = getWxSourceCount();
        Integer imageCount = materialcountResult.getImage_count();
        if (imageCount <= 20) {
            MaterialBatchgetResult materialBatchgetResult = getWxSource("image", 0, imageCount);
            medias = getWxSourceMedias(materialBatchgetResult, medias);
        } else {
            int count = imageCount / 20 + 1;//拿几回
            int countSy = imageCount;//剩余数量
            //45
            for (int i = 1; i <= count; i++) {
                MaterialBatchgetResult materialBatchgetResult = getWxSource("image", i == 1 ? (i - 1) * 20 : (i - 1) * 20 + 1, i == 1 ? 20 : (i == count ? countSy : 20));
                medias = getWxSourceMedias(materialBatchgetResult, medias);
                countSy = countSy - 20;
            }
        }
        String addr = getRequestUrl();
        List<SourceImage> list = sourceImageFacade.findAll(SourceImage.class);//查找本地保存的图片
        //获取微信服务器上的图片

            if (list.size() == medias.size()) {
                for (SourceImage sourceImage : list) {
                    sourceImage.setImage("<img src=" + (addr + sourceImage.getImageLocalUrl()) + " style='width:40px;height:40px'/>");
                }
                return list;
            }
            if (list.size() > medias.size()) {//本地的数量多余服务器上的数量
                for (SourceImage sourceImage : list) {
                    byte[] image = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(), sourceImage.getMediaId());
                    if (image != null && !"".equals(image)) {//服务器上也存在这个图片
                        sourceImage.setImage("<img src=" + (addr + sourceImage.getImageLocalUrl()) + " style='width:40px;height:40px;'/>");
                    } else {
                        list.remove(sourceImage);
                        //从本地删除
                        sourceImageFacade.delete(sourceImage);
                    }
                }
                return list;
            }
            if (list.size() < medias.size()) {//本地的数量小于服务器上的数量
                List<String> result = new ArrayList<String>();
                for (SourceImage sourceImage : list) {
                    result.add(sourceImage.getMediaId());
                }
                for (String media : medias) {
                    SourceImage sourceImage=null;
                    if (!result.contains(media)) {
                        byte[] image = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(), media);
                        // 将image上传到自己的服务器
                         try{
                             sourceImage=uploadWxSourceAndSave(image,media);
                             if(!list.contains(sourceImage)){
                                 sourceImage.setImage("<img src=" + (addr + sourceImage.getImageLocalUrl()) + " style='width:40px;height:40px;'/>");
                                 list.add(sourceImage);
                             }
                          }
                         catch(Exception e){
                             continue;
                         }
                     }
                }
            }

        return list;
    }

    /**
     * 上传视频素材
     *
     * @param sourceVideo
     * @param updateFlag
     * @param saveName
     * @param videoDescription
     * @return
     */
    @POST
    @Path("save-video")
    public Response saveVideo(SourceVideo sourceVideo, @QueryParam("update") String updateFlag, @QueryParam("saveName") String saveName, @QueryParam("videoDescription") String videoDescription) {
        try {
            byte[] bytes = videoDescription.getBytes("UTF-8");
            sourceVideo.setDescription(bytes);
            if (updateFlag != null && !"".equals(updateFlag)) {
                sourceVideoFacade.save(sourceVideo);
            }
            if (updateFlag == null || "".equals(updateFlag)) {//新加的要上传到微信服务器
                String path = request.getSession().getServletContext().getRealPath("/upload");
                File file = new File(path);
                File[] files = file.listFiles();
                for (File file1 : files) {
                    if (file1.getName().equals(saveName)) {
                        InputStream inputStream = new FileInputStream(file1);
                        Description description = new Description();
                        description.setTitle(sourceVideo.getTitle());
                        description.setIntroduction(new String(sourceVideo.getDescription(), "UTF-8"));
                        Media media = MaterialAPI.materialAdd_material(TokenManager.getDefaultToken(), MediaType.video, file1, description);
                        if (media.getErrmsg() != null && !"".equals(media.getErrcode())) {
                            ErrorException errorException = new ErrorException();
                            errorException.setMessage(new IllegalArgumentException());
                            errorException.setErrorMessage(media.getErrmsg());
                            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
                        } else {
                            sourceVideo.setVideoWxUrl(media.getUrl());
                            sourceVideo.setMediaId(media.getMedia_id());
                            sourceVideo = sourceVideoFacade.save(sourceVideo);
                        }
                        break;
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(sourceVideo).build();
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

    /**
     * 获取视频列表
     *
     * @return
     */
    @GET
    @Path("list-video")
    public List<SourceVideo> listVideo() {
        //todo 保证本地的素材和微信服务器上素材是同步的
        String addr = getRequestUrl();
        List<SourceVideo> list = sourceVideoFacade.findAll(SourceVideo.class);
        return list;
    }

    /**
     * 群发消息
     *
     * @param sendType    消息类型(image text 图文)
     * @param sendMessage 消息内容
     * @return
     */
    @POST
    @Path("send-all")
    public Response sendAll(@QueryParam("sendType") String sendType, SendMessage sendMessage) {
        if (sendType.equals("1")) {//图文
            List<SourceImageFont> list = sourceImageFontFacade.findSourceImageFontByIds(sendMessage.getIds());
            MassMessage massMessage = new MassMessage();
            Filter filter = new Filter(true, "");
            massMessage.setFilter(filter);
            massMessage.setMpnews(new Mpnews(list.get(0).getMediaId()));
            massMessage.setMsgtype("mpnews");
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
            if (messageSendResult.getErrcode() == null) {
                return Response.status(Response.Status.OK).entity(messageSendResult).build();
            } else {
                ErrorException errorException = new ErrorException();
                errorException.setMessage(new IllegalArgumentException());
                errorException.setErrorMessage(messageSendResult.getErrmsg());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
            }
        } else if (sendType.equals("2")) {//文字
            MassMessage massMessage = new MassMessage();
            Filter filter = new Filter(true, "");
            massMessage.setFilter(filter);
            massMessage.setText(new Text(sendMessage.getContent()));
            massMessage.setMsgtype("text");
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
            if (messageSendResult.getErrcode() == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
            }
            ErrorException errorException = new ErrorException();
            errorException.setMessage(new IllegalArgumentException());
            errorException.setErrorMessage(messageSendResult.getErrmsg());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
        } else if (sendType.equals("3")) {//图片
            MassMessage massMessage = new MassMessage();
            Filter filter = new Filter(true, "");
            massMessage.setFilter(filter);
            List<SourceImage> list = sourceImageFacade.findById(sendMessage.getIds());
            massMessage.setImage(new Image(list.get(0).getMediaId()));
            massMessage.setMsgtype("image");
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
            if (messageSendResult.getErrcode() == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
            }
            ErrorException errorException = new ErrorException();
            errorException.setMessage(new IllegalArgumentException());
            errorException.setErrorMessage(messageSendResult.getErrmsg());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
        }
        return null;
    }
     /**
     * 获取永久素材根据mediaId
     */
//    @POST
//    @Path("get-source-by-media-id")
//    public List<SourceImageFont> getSourceByMediaId(@QueryParam("typeFlag") String typeFlag) {
//        if (typeFlag.equals("1")) {//图文素材
//            List<SourceImageFont> sourceImageFonts = sourceImageFontFacade.findAll(SourceImageFont.class);
//            if (sourceImageFonts != null && !sourceImageFonts.isEmpty()) {
//                for (SourceImageFont sourceImageFont : sourceImageFonts) {
//                    String imageLocalUrl = sourceImageFacade.findImageLocalUrlByMediaId(sourceImageFont.getThumbMediaId());
//                    if (imageLocalUrl != null && !"".equals(imageLocalUrl)) {
//                        sourceImageFont.setImage("<img src=" + imageLocalUrl + " width=100%/>");
//                    }
//                }
//            }
//            return sourceImageFonts;
//        }
//        return null;
//    }

    /**
     * 加载图文素材
     *
     * @return
     */
    @GET
    @Path("load-image-font")
    public List<ImageVo> loadImageFont() {
        // todo 保证本地服务器上图文素材和微信服务器上图文素材是同步的
        String addr = getRequestUrl();
        List<ImageVo> imageVos = new ArrayList<ImageVo>();
        List<SourceImageFont> list = sourceImageFontFacade.findAll(SourceImageFont.class);
        for (SourceImageFont sourceImageFont : list) {
            ImageVo imageVo = new ImageVo();
            imageVo.setId(sourceImageFont.getId());
            imageVo.setMediaId(sourceImageFont.getMediaId());
            String imageLocalUrl = addr + sourceImageFacade.findImageLocalUrlByMediaId(sourceImageFont.getThumbMediaId());
            StringBuffer sb = new StringBuffer();
            sb.append("<div>");
            sb.append("<div>" + sourceImageFont.getTitle() + "</div>");
            sb.append("<div><img src=" + imageLocalUrl + " style=width:40px,height:40px;></div>");
            sb.append("<div>" + sourceImageFont.getDigest() + "</div>");
            sb.append("</div>");
            imageVo.setImage(sb.toString());
            imageVos.add(imageVo);
        }
        return imageVos;
    }

    /**
     * 加载图片素材
     *
     * @return
     */
    @GET
    @Path("load-image")
    public List<ImageVo> loadImage() {
        List<ImageVo> imageVos = new ArrayList<ImageVo>();
        List<SourceImage> list = this.list();
        String addr = getRequestUrl();
        if (list != null && !list.isEmpty()) {
            for (SourceImage sourceImage : list) {
                ImageVo imageVo = new ImageVo();
                imageVo.setImage("<img src='" + (addr + sourceImage.getImageLocalUrl()) + "' style=width:40px,height:40px;>");
                imageVo.setMediaId(sourceImage.getMediaId());
                imageVo.setId(sourceImage.getId());
                imageVos.add(imageVo);
            }
        }
        return imageVos;
    }

    /**
     * 内部类
     * 用于封装群发消息datagrid所需要的数据
     * 包括图片和图文数据
     */
    private class ImageVo {
        private String id;
        private String image;
        private String mediaId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMediaId() {
            return mediaId;
        }

        public void setMediaId(String mediaId) {
            this.mediaId = mediaId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    /**
     * 回复关注的用户
     *
     * @param fromUserName 用户
     * @param replyContent 回复内容
     * @param toUserName   公众号
     * @return
     */
    @POST
    @Path("reply-user")
    public Response repplyUser(@QueryParam("openId") String fromUserName, @QueryParam("replyContet") String replyContent, @QueryParam("toUserName") String toUserName) {
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //创建回复
        XMLMessage xmlTextMessage = new XMLTextMessage(
                toUserName,
                fromUserName,
                replyContent);
        //回复
        xmlTextMessage.outputStreamWrite(outputStream);
        return Response.status(Response.Status.OK).entity(xmlTextMessage).build();
    }

    /**
     * 查询关注者发送的消息
     *
     * @param startTime
     * @param endTime
     * @return 可以查找的内容类型为:Text image link
     */
    @GET
    @Path("load-message-list")
    public List<MessageVo> loadMessageList(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime) {
        return requestMessageFacade.findByTime(startTime, endTime);
    }

    /**
     * 获取访问项目名字的Ip+port
     *
     * @return
     */
    private String getRequestUrl() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        return addr;
    }

    /**
     * 获取微信服务器上的素材总数
     *
     * @return
     */
    private MaterialcountResult getWxSourceCount() {
        MaterialcountResult materialcountResult = MaterialAPI.materialGet_materialcount(TokenManager.getDefaultToken());
        return materialcountResult;
    }

    /**
     * 获取微信服务器上制定类型和指定数量的素材
     *
     * @param type
     * @param offest
     * @param count
     * @return
     */
    private MaterialBatchgetResult getWxSource(String type, int offest, int count) {
        return MaterialAPI.materialBatchget_material(TokenManager.getDefaultToken(), type, offest, count);
    }

    /**
     * 通过批量微信素材拿到mediaId 集合
     *
     * @param materialBatchgetResult
     * @return
     */
    private List<String> getWxSourceMedias(MaterialBatchgetResult materialBatchgetResult, List<String> medias) {
        List<MaterialBatchgetResultItem> list = materialBatchgetResult.getItem();
        if (list != null && !list.isEmpty()) {
            for (MaterialBatchgetResultItem m : list) {
                if (!medias.contains(m.getMedia_id()))
                    medias.add(m.getMedia_id());
            }
        }
        return medias;
    }

    /**
     * 获取upload 文件夹的真实路径
     * @return
     */
    private String getUploadPath(){
        return  request.getSession().getServletContext().getRealPath("upload");
    }

    /**
     *将微信服务器上的图片放到项目上
     * 并且保存相关信息到数据库
     */
    private SourceImage uploadWxSourceAndSave(byte[] image,String media){
        long imageUploadName=new Date().getTime();
        File imageFile=new File(getUploadPath()+"//"+imageUploadName+".jpg");
        if(!imageFile.exists()){
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStream=null;
        try {
            outputStream= new FileOutputStream(imageFile);
            outputStream.write(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SourceImage sourceImage = new SourceImage();
        sourceImage.setMediaId(media);//媒体ID
        sourceImage.setImageName(String.valueOf(imageUploadName)+".jpg");
        sourceImage.setImageLocalUrl("/upload/"+imageUploadName+".jpg");
        sourceImage.setImageSize(String.valueOf(image.length));
        return sourceImageFacade.save(sourceImage);
    }
}
