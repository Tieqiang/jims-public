package com.jims.wx.service;


import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.MessageVo;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.bean.material.*;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.*;
import weixin.popular.bean.message.Article;
import weixin.popular.bean.message.massmessage.*;
import weixin.popular.bean.message.preview.ImagePreview;
import weixin.popular.bean.message.preview.MpnewsPreview;
import weixin.popular.bean.message.preview.Preview;
import weixin.popular.bean.message.preview.TextPreview;
import weixin.popular.bean.user.FollowResult;
import weixin.popular.bean.user.User;
import weixin.popular.bean.user.UserInfoList;
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

import java.util.*;

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
    private AppUserFacade appUserFacade;
    private MediaService mediaService;

    @Inject
    public SourceService(SourceImageFacade sourceImageFacade, HttpServletRequest request, SourceVideoFacade sourceVideoFacade, SourceImageFontFacade sourceImageFontFacade, RequestMessageFacade requestMessageFacade, HttpServletResponse response,AppUserFacade appUserFacade,MediaService mediaService) {
        this.sourceImageFacade = sourceImageFacade;
        this.request = request;
        this.sourceVideoFacade = sourceVideoFacade;
        this.sourceImageFontFacade = sourceImageFontFacade;
        this.requestMessageFacade = requestMessageFacade;
        this.response = response;
        this.appUserFacade=appUserFacade;
        this.mediaService=mediaService;
    }

//    @Inject
//    public SourceService(SourceImageFacade sourceImageFacade,SourceImageFontFacade sourceImageFontFacade){
//        this.sourceImageFacade=sourceImageFacade;
//        this.sourceImageFontFacade=sourceImageFontFacade;
//    }

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
    public Response saveImageFont( SourceImageFont sourceImageFont, @QueryParam("content") String content) {
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
            if ("".equals(media.getMedia_id())) {
                ErrorException errorException = new ErrorException();
                errorException.setMessage(new IllegalArgumentException());
                errorException.setErrorMessage(media.getErrmsg());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
            }
            sourceImageFont.setMediaId(media.getMedia_id());
            sourceImageFont.setContent(bytes);
            sourceImageFont = sourceImageFontFacade.save(sourceImageFont);
//            MessageSendResult messageSendResult=send(sourceImageFont);
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
    public Response save(@QueryParam("update")  String updateFlag, SourceImage sourceImage, @QueryParam("saveName") String saveName) {
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
                        Media media = MaterialAPI.materialAdd_material(TokenManager.getDefaultToken(), MediaType.thumb, inputStream, new Description());
                        inputStream.close();
                        if (media.getMedia_id()!=null) {
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
        String addr = getRequestUrl();
        List<SourceImage> list = sourceImageFacade.findAll(SourceImage.class);//查找本地保存的图片
        for (SourceImage sourceImage : list) {
            sourceImage.setImage("<img src=" + (addr + sourceImage.getImageLocalUrl()) + " style='width:100%;height:100%'/>");
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
                        if (!"0".equals(media.getErrcode())) {
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
        //拿到服务器上的数量

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
             MessageSendResult messageSendResult=send(list.get(0));
//           MessageSendResult messageSendResult = MessageAPI.messageMassSend(TokenManager.getDefaultToken(), massMessage);
             if (messageSendResult.getErrcode().equals("0")) {
                return Response.status(Response.Status.OK).entity(messageSendResult).build();
            } else {
                ErrorException errorException = new ErrorException();
                errorException.setMessage(new IllegalArgumentException());
                errorException.setErrorMessage(messageSendResult.getErrmsg());
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
            }
        } else if (sendType.equals("2")) {//文字
            MassMessage massMessage = new MassMessage();
            Filter filter = new Filter(true,null);
            massMessage.setFilter(filter);
            massMessage.setText(new Text(sendMessage.getContent()));
            massMessage.setMsgtype("text");
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
            if (messageSendResult.getErrcode().equals("0")) {
                return Response.status(Response.Status.OK).entity(messageSendResult).build();
            }
            ErrorException errorException = new ErrorException();
            errorException.setMessage(new IllegalArgumentException());
            errorException.setErrorMessage(messageSendResult.getErrmsg());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
        } else if (sendType.equals("3")) {//图片
            MassMessage massMessage = new MassMessage();
            Filter filter = new Filter(true,null);
            massMessage.setFilter(filter);
            List<SourceImage> list = sourceImageFacade.findById(sendMessage.getIds());
            massMessage.setImage(new Image(list.get(0).getMediaId()));
            massMessage.setMsgtype("image");
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
            if (messageSendResult.getErrcode().equals("0")) {
                return Response.status(Response.Status.OK).entity(messageSendResult).build();
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
        //  保证本地服务器上图文素材和微信服务器上图文素材是同步的
        String addr = getRequestUrl();
        List<ImageVo> imageVos = new ArrayList<ImageVo>();
        List<SourceImageFont> list = sourceImageFontFacade.findAll(SourceImageFont.class);


//        MaterialcountResult materialcountResult = getWxSourceCount();
//        Integer newsCount = materialcountResult.getNews_count();//图文消息的总数
//        MaterialBatchgetResult materialBatchgetResult = null;
//        List<String> medias = new ArrayList<String>();
//        if (newsCount != null ? (newsCount <= 20 && newsCount != 0 ? true : false) : false) {
//            materialBatchgetResult = getWxSource("news", 0, newsCount);//从第一个开始取，取newsCount个
//            medias = getWxSourceMedias(materialBatchgetResult, medias);
//
//        }
//        if (newsCount != null ? (newsCount > 20 ? true : false) : false) {
//            int count = newsCount / 20 + 1;//可以取几次
//            int amout = newsCount;//剩余数量
//            for (int i = 1; i <= count; i++) {
//                materialBatchgetResult = getWxSource("news", i == 1 ? 0 : (20 * (i - 1)) + 1, amout);//从第一个开始取，取newsCount个
//                medias = getWxSourceMedias(materialBatchgetResult, medias);
//                if (i == count) {
//                    amout = 0;
//                    break;
//                }
//                amout = amout - 20;
//            }
//        }
//        if (list != null ? (list.size() == medias.size() ? true : false) : false) {// 是同步的
//            return imageVos;
//        }
//        if (list != null ? (list.size() > medias.size() ? true : false) : false) {
//            for (SourceImageFont sourceImageFont : list) {
//                if (!medias.contains(sourceImageFont.getMediaId())) {//服务器上没有这个图文
//                    //将本地删除
//                    sourceImageFontFacade.delete(sourceImageFont);
//                    //list 集合删除
//                    list.remove(sourceImageFont);
//                }
//            }
//
//        }
//        if (list != null ? (list.size() < medias.size() ? true : false) : false) {//微信服务去上数量多余本地
//            //找出缺失的些
//            List<String> result = new ArrayList<String>();
//            for (SourceImageFont sourceImageFont : list) {
//                if (!result.contains(sourceImageFont.getMediaId()))
//                    result.add(sourceImageFont.getMediaId());
//            }
//            for (String media : medias) {
//                if (!result.contains(media)) {
//                    NewsItem newsItem = MaterialAPI.materialGet_material_newsItem(TokenManager.getDefaultToken(), media);
//                    List<Article> articles = newsItem.getNews_item();
//                    list = findWxAndsaveLocal(list, media);
//                }
//            }
//        }
//        if (list == null && list.isEmpty() ? (medias.size() > 0 ? true : false) : false) {
//            for (String media : medias) {
//                list = findWxAndsaveLocal(list, media);
//            }
//        }
        for (SourceImageFont sourceImageFont : list) {
            ImageVo imageVo = new ImageVo();
            imageVo.setId(sourceImageFont.getId());
            imageVo.setMediaId(sourceImageFont.getMediaId());
            String imageLocalUrl = addr + sourceImageFacade.findImageLocalUrlByMediaId(sourceImageFont.getThumbMediaId());
            StringBuffer sb = new StringBuffer();
            sb.append("<div>");
            sb.append("<div>" + sourceImageFont.getTitle() + "</div>");
            sb.append("<div><img src=" + imageLocalUrl + " style=width:100%,height:100%;></div>");
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
                imageVo.setImage("<img src='" + (addr + sourceImage.getImageLocalUrl()) + "' style=width:100%,height:100%;>");
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
    public Response repplyUser(@QueryParam("openId") String fromUserName, @QueryParam("replyContet") String replyContent, @QueryParam("toUserName") String toUserName,@QueryParam("id") String id) {
        if (toUserName == null || toUserName.equals("")) {
            toUserName = "jimscloud";
        }
        String jsonMessage = "{\"touser\":\"" + fromUserName + "\",\"msgtype\":\"text\",\"text\":{\"content\":\"" + replyContent + "\"}} ";
        BaseResult baseResult = MessageAPI.messageCustomSend(TokenManager.getDefaultToken(), jsonMessage);
        if (baseResult.getErrcode().equals("0")){
            if(id!=null&&!"".equals(id)){//单条回复成功
                requestMessageFacade.updateData(id, replyContent);
            }
            return Response.status(Response.Status.OK).entity(baseResult).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(baseResult).build();
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
     *
     * @return
     */
    private String getUploadPath() {
        return request.getSession().getServletContext().getRealPath("upload");
    }

    /**
     * 将微信服务器上的图片放到项目上
     * 并且保存相关信息到数据库
     */
    private SourceImage uploadWxSourceAndSave(byte[] image, String media) {
        long imageUploadName = new Date().getTime();
        File imageFile = new File(getUploadPath() + "//" + imageUploadName + ".jpg");
        if (!imageFile.exists()) {
            try {
                imageFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(imageFile);
            outputStream.write(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SourceImage sourceImage = new SourceImage();
        sourceImage.setMediaId(media);//媒体ID
        sourceImage.setImageName(String.valueOf(imageUploadName) + ".jpg");
        sourceImage.setImageLocalUrl("/upload/" + imageUploadName + ".jpg");
        sourceImage.setImageSize(String.valueOf(image.length));
        return sourceImageFacade.save(sourceImage);
    }

    /**
     * @param list
     * @param media
     * @return
     */
    private List<SourceImageFont> findWxAndsaveLocal(List<SourceImageFont> list, String media) {
        NewsItem newsItem = MaterialAPI.materialGet_material_newsItem(TokenManager.getDefaultToken(), media);
        List<Article> articles = newsItem.getNews_item();
        //将其保存到数据库
        if (articles != null && !articles.isEmpty()) {
            SourceImageFont sourceImageFont = new SourceImageFont();
            try {
                sourceImageFont.setContent(articles.get(0).getContent().getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            sourceImageFont.setMediaId(media);
            sourceImageFont.setAuthor(articles.get(0).getAuthor());
            sourceImageFont.setContentSourceUrl(articles.get(0).getContent_source_url());
            sourceImageFont.setDelFlag("0");
            sourceImageFont.setDigest(articles.get(0).getDigest());
            sourceImageFont.setShowCoverPic(articles.get(0).getShow_cover_pic());
            sourceImageFont.setThumbMediaId(articles.get(0).getThumb_media_id());
            sourceImageFont.setTitle(articles.get(0).getTitle());
            String localUrl = sourceImageFontFacade.findByMediaId(articles.get(0).getThumb_media_id());
            if (localUrl != null ? true : false) {
                sourceImageFont.setLocalUrl(localUrl);
            }
            sourceImageFont = sourceImageFontFacade.save(sourceImageFont);
            list.add(sourceImageFont);
            return list;
        }
        return null;
    }

    /**
     * 预览
     *
     * @return
     */
    @POST
    @Path("preview")
    public MessageSendResult preview(@QueryParam("selectedType") String selectedType, @QueryParam("mediaId") String mediaId, @QueryParam("sendContent") String sendContent,@QueryParam("openId") String openId) {
        if (selectedType.equals("1")) {//图文
            MpnewsPreview mpnewsPreview = new MpnewsPreview(mediaId);
            mpnewsPreview.setTouser(openId);
            return MessageAPI.messageMassPreview(TokenManager.getDefaultToken(), mpnewsPreview);
        } else if (selectedType.equals("2")) {//文本
            TextPreview textPreview = new TextPreview(sendContent);
            textPreview.setTouser(openId);
            return MessageAPI.messageMassPreview(TokenManager.getDefaultToken(), textPreview);
        }
        ImagePreview imagePreview = new ImagePreview(mediaId);
        imagePreview.setTouser(openId);
        MessageSendResult messageSendResult = MessageAPI.messageMassPreview(TokenManager.getDefaultToken(), imagePreview);
        return messageSendResult;
    }

    /**
     * 删除永久素材
     *
     * @param mediaId
     * @param sendType
     * @return
     */
    @POST
    @Path("delete-source")
    public Response deletSource(@QueryParam("mediaId") String mediaId, @QueryParam("sendType") String sendType) {
        if (sendType.equals("3")) {
            //先将本地删除
            sourceImageFacade.delete(sourceImageFacade.findByMediaId(mediaId));
            return returnDeleteResult(mediaId);
        } else if (sendType.equals("1")) {
            SourceImageFont sourceImageFont = sourceImageFontFacade.findEntityByMediaId(mediaId);
            sourceImageFontFacade.delete(sourceImageFont);
            return returnDeleteResult(mediaId);
        }
        return null;
    }

    /**
     * @param mediaId
     * @return
     */
    private Response returnDeleteResult(String mediaId) {
        BaseResult baseResult = MaterialAPI.materialDel_material(TokenManager.getDefaultToken(), mediaId);
        if (baseResult.getErrcode().equals("0")) {
            return Response.status(Response.Status.OK).entity(baseResult).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(baseResult).build();
    }


    /**
     * 获取关注列表以及信息
     *
     * @return
     */
    @GET
    @Path("source-user")
    public List<User> sourceUserList() {
        FollowResult followResult = UserAPI.userGet(TokenManager.getDefaultToken(), null);
        FollowResult.Data data = followResult.getData();
        String[] openIds = data.getOpenid();
        List<String> openIdss = Arrays.asList(openIds);
        if(openIdss!=null&&openIdss.size()<=100){
            UserInfoList userInfoList = UserAPI.userInfoBatchget(TokenManager.getDefaultToken(), "zh-CN", openIdss);
            return userInfoList.getUser_info_list();
        }else{
             int count=openIdss.size()/100+1;//总共取几次
             int coutSy=openIdss.size();//总共有几个
             List<User> returnVal=new ArrayList<User>();
            //167   2
             for(int i=1;i<=count;i++){
                  List<String> target=openIdss.subList(i==1?0:(i-1)*100,i==1?100:(i==count?coutSy:i*100));
                  UserInfoList userInfoList=UserAPI.userInfoBatchget(TokenManager.getDefaultToken(),"zh-cn",target);
                  if(userInfoList.getUser_info_list()!=null) {
                      for(User user:userInfoList.getUser_info_list()){
                          if(!returnVal.contains(user)){
                              returnVal.add(user);
                          }
                      }
//                      if(i==count){
//                              coutSy=0;
//                              break;
//                      }
//                      coutSy-=100;
                  }
             }
             return returnVal;
        }
     }

    /**
     * 同步本地和服务器上的图文消息
     * listener 调用的方法
     */
    @POST
    @Path("synch-image-font")
    public Response threadMethod() {
        String addr = getRequestUrl();
        List<ImageVo> imageVos = new ArrayList<ImageVo>();
        List<SourceImageFont> list = sourceImageFontFacade.findAll(SourceImageFont.class);
        MaterialcountResult materialcountResult = getWxSourceCount();
        Integer newsCount = materialcountResult.getNews_count();//图文消息的总数
        MaterialBatchgetResult materialBatchgetResult = null;
        List<String> medias = new ArrayList<String>();
        if (newsCount != null ? (newsCount <= 20 && newsCount != 0 ? true : false) : false) {
            materialBatchgetResult = getWxSource("news", 0, newsCount);//从第一个开始取，取newsCount个
            medias = getWxSourceMedias(materialBatchgetResult, medias);
        }
        if (newsCount != null ? (newsCount > 20 ? true : false) : false) {
            int count = newsCount / 20 + 1;//可以取几次
            int amout = newsCount;//剩余数量
            for (int i = 1; i <= count; i++) {
                materialBatchgetResult = getWxSource("news", i == 1 ? 0 : (20 * (i - 1)) + 1, amout);//从第一个开始取，取newsCount个
                medias = getWxSourceMedias(materialBatchgetResult, medias);
                if (i == count) {
                    amout = 0;
                    break;
                }
                amout = amout - 20;
            }
        }
        if (list != null ? (list.size() == medias.size() ? true : false) : false) {// 是同步的

        }
        if (list != null ? (list.size() > medias.size() ? true : false) : false) {
            for (SourceImageFont sourceImageFont : list) {
                if (!medias.contains(sourceImageFont.getMediaId())) {//服务器上没有这个图文
                    //将本地删除
                    sourceImageFontFacade.delete(sourceImageFont);
                    //list 集合删除
                    list.remove(sourceImageFont);
                }
            }

        }
        if (list != null ? (list.size() < medias.size() ? true : false) : false) {//微信服务去上数量多余本地
            //找出缺失的些
            List<String> result = new ArrayList<String>();
            for (SourceImageFont sourceImageFont : list) {
                if (!result.contains(sourceImageFont.getMediaId()))
                    result.add(sourceImageFont.getMediaId());
            }
            for (String media : medias) {
                if (!result.contains(media)) {
                    NewsItem newsItem = MaterialAPI.materialGet_material_newsItem(TokenManager.getDefaultToken(), media);
                    List<Article> articles = newsItem.getNews_item();
                    list = findWxAndsaveLocal(list, media);
                }
            }
        }
        if (list!=null && list.isEmpty() ? (medias.size() > 0 ? true : false) : false) {
            for (String media : medias) {
                list = findWxAndsaveLocal(list, media);
            }
        }
        return Response.status(Response.Status.OK).entity(list).build();
    }

    /**
     * 同步图片消息
     */
    @POST
    @Path("synch-image")
    public Response threadMethod2() {
        List<String> medias = new ArrayList<String>();
        //获取微信服务器上的总数
        MaterialcountResult materialcountResult = getWxSourceCount();
        Integer imageCount = materialcountResult.getImage_count();
        if (imageCount != null ? (imageCount <= 20 ? true : false) : false) {
            MaterialBatchgetResult materialBatchgetResult = getWxSource("image", 0, imageCount);
            medias = getWxSourceMedias(materialBatchgetResult, medias);
        }
        if (imageCount != null ? (imageCount > 20 ? true : false) : false) {
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
        if (list != null && !list.isEmpty() ? (list.size() == medias.size() ? true : false) : false) {

        }
//        if (list != null && !list.isEmpty() ? (list.size() > medias.size() ? true : false) : false) {//本地的数量多余服务器上的数量
//            for (SourceImage sourceImage : list) {
//                byte[] image = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(), sourceImage.getMediaId());
//                if (image != null && !"".equals(image)) {//服务器上也存在这个图片
//
//                } else {
//                    list.remove(sourceImage);
//                    //从本地删除
//                    sourceImageFacade.delete(sourceImage);
//                }
//            }
//        }
        if (list != null && !list.isEmpty() ? (list.size() < medias.size() ? true : false) : false) {//本地的数量小于服务器上的数量
            List<String> result = new ArrayList<String>();
            for (SourceImage sourceImage : list) {
                result.add(sourceImage.getMediaId());
            }
            for (String media : medias) {
                SourceImage sourceImage = null;
                if (!result.contains(media)) {
                    byte[] image = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(), media);
                    // 将image上传到自己的服务器
                    try {
                        sourceImage = uploadWxSourceAndSave(image, media);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        if (list!=null && list.isEmpty() ? (medias != null && medias.size() > 0 ? true : false) : false) {
            for (String media : medias) {
                SourceImage sourceImage = null;
                byte[] image = MaterialAPI.materialGet_material(TokenManager.getDefaultToken(), media);
                // 将image上传到自己的服务器
                try {
                    sourceImage = uploadWxSourceAndSave(image, media);
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return Response.status(Response.Status.OK).entity(list).build();
    }

    /**
     * 同步关注用户
     * @return
     */
    @POST
    @Path("synch-user")
    public Response synchUser(){
        FollowResult followResult=null;
        try {
            followResult=UserAPI.userGet(TokenManager.getDefaultToken(),null);
            FollowResult.Data data=followResult.getData();
            String [] openIdArr=data.getOpenid();
            List<String> openIds=Arrays.asList(openIdArr);

            List<AppUser> list=appUserFacade.findAll(AppUser.class);
            if(list!=null&&!list.isEmpty()?(list.size()==openIds.size()?true:false):false){
                return Response.status(Response.Status.OK).entity(openIds).build();
            }
            if(list!=null&&!list.isEmpty()?(list.size()<openIds.size()?true:false):false){
                //本地的关注用户小于真实的关注用户
                List<String> strings=getOpenIds(list);
                synchAndSaveUser(openIds,strings,"less");
            }

            if(list!=null&&!list.isEmpty()?(list.size()>openIds.size()?true:false):false)
            {
                //本地的多于服务器上的
                List<String> strings=getOpenIds(list);
                synchAndSaveUser(strings,openIds,"more");
            }
            if(list!=null&&list.isEmpty()?(openIds.size()>0?true:false):false){
                //本地没有 存在关注用户
               for(String openId:openIds){
                   saveUser(openId);
               }
            }
            return Response.status(Response.Status.OK).entity(followResult).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(followResult).build();
        }
     }
//    public static void main(String[] args){
//        long a=new Date().getTime()/1000;
//        System.out.println(a);
//    }

    /**
     * 获取openIds
     * @param list
     * @return
     */
    private List<String> getOpenIds(List<AppUser> list){
        List<String> strings=new ArrayList<String>();
        for(AppUser appUser:list){
            if(!strings.contains(appUser.getOpenId())){
                strings.add(appUser.getOpenId());
            }
        }
        return strings;
    }

    /**
     * 查找不同步的用户将其保存或者删除
     * @param openIds
     * @param strings
     */
    private void synchAndSaveUser(List<String> openIds,List<String> strings,String str){
        for(String openId:openIds){
            if(!strings.contains(openId)){
                if(str.equals("less")){
                    saveUser(openId);
                }if(str.equals("more")){
                    appUserFacade.deletebyOpenId(openId);
                }
             }
        }
    }

    /**
     * 保存
     * @param openId
     */
    private void saveUser(String openId){
        User user=UserAPI.userInfo(TokenManager.getDefaultToken(),openId);
        AppUser appUser=new AppUser();
        appUser.setOpenId(openId);
        appUser.setCity(user.getCity());
        appUser.setCountry(user.getCountry());
        appUser.setLanguage(user.getLanguage());
        appUser.setNickName(user.getNickname());
        appUser.setSex(user.getSex());
        appUser.setSubscribe(user.getSubscribe());
        appUser.setHeadImgUrl(user.getHeadimgurl());
        appUser.setGroupId(user.getGroupid());
        appUserFacade.saveAppUser(appUser);
    }

    /**
     * 发送消息
     * @param sourceImageFont
     * @return
     */
    private MessageSendResult send(SourceImageFont sourceImageFont){
        MassMessage massMessage = new MassMessage();
        Filter filter = new Filter(true,null);
        massMessage.setFilter(filter);
//            Set<String> set=new HashSet<String>();
//            set.add("ogtZUw0uS2KiPorCm1zdp-FmelQY");
//            set.add("ogtZUw_hUp_C7s4bm4yzL-2viQ8w");
//            set.add("ogtZUw6CDDKCTkF61a4mxRZzfcS8");
//            massMessage.setTouser(set);
        massMessage.setMpnews(new Mpnews(sourceImageFont.getMediaId()));
        massMessage.setMsgtype("mpnews");
//            messageMassSend
       return  MessageAPI.messageMassSendall(TokenManager.getDefaultToken(), massMessage);
    }



}
