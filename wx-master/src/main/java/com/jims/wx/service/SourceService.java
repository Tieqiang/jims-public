package com.jims.wx.service;

import com.alibaba.fastjson.JSONObject;
import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.MessageVo;
import weixin.popular.api.MaterialAPI;
import weixin.popular.api.MessageAPI;
import weixin.popular.bean.material.Description;
import weixin.popular.bean.media.Media;
import weixin.popular.bean.media.MediaType;
import weixin.popular.bean.message.*;
import weixin.popular.bean.message.Article;
import weixin.popular.bean.message.massmessage.*;
import weixin.popular.bean.message.preview.MpnewsPreview;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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

    @POST
    @Path("save-image-font")
    public Response saveImageFont(SourceImageFont sourceImageFont, @QueryParam("content") String content) {
        try {
            byte[] bytes = content.getBytes("UTF-8");
            sourceImageFont.setContent(bytes);
//            List<Article> articles=new ArrayList<Article>();
            List<weixin.popular.bean.message.Article> articles = new ArrayList<Article>();
            Article article = new Article();
            article.setTitle(sourceImageFont.getTitle());
            article.setThumb_media_id(sourceImageFont.getThumbMediaId());
            article.setAuthor(sourceImageFont.getAuthor());
            article.setContent(content);
            article.setDigest(sourceImageFont.getDigest());
            article.setShow_cover_pic("1");
            articles.add(article);
            Media media = MaterialAPI.materialAdd_news(TokenManager.getToken("wx43a128a6adebadd4"), articles);
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
     * 上传本地之后 上传微信服务器和保存相关数据到数据库
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
                        sourceImage.setImageWxUrl(media.getUrl());
                        sourceImage.setMediaId(media.getMedia_id());
                        sourceImage = sourceImageFacade.save(sourceImage);
                        break;
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


    @GET
    @Path("list")
    public List<SourceImage> list() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<SourceImage> list = sourceImageFacade.findAll(SourceImage.class);
        for (SourceImage sourceImage : list) {
            sourceImage.setImage("<img src=" + (addr + sourceImage.getImageLocalUrl()) + " style='width:100%;'/>");
        }
        return list;
    }


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
                        Media media = MaterialAPI.materialAdd_material(TokenManager.getToken("wx43a128a6adebadd4"), MediaType.video, file1, description);
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
     * <embed src="URL" widht=播放显示宽度 height=播放显示高度 autostart=true/false loop=true/false></embed>
     * init datagrid
     */
    @GET
    @Path("list-video")
    public List<SourceVideo> listVideo() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<SourceVideo> list = sourceVideoFacade.findAll(SourceVideo.class);
//        for (SourceVideo sourceVideo : list) {
//            sourceVideo.setVideo("<embed> src='"+(addr+sourceVideo.getVideoLocalUrl())+"' ,width=20%, height=20%,autostart=false,loop=false ");
//        }
        return list;
    }

    /**
     * 群发消息
     *
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
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getToken("wx43a128a6adebadd4"), massMessage);
            if (messageSendResult.getErrmsg() == null) {
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
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getToken("wx43a128a6adebadd4"), massMessage);
            if (messageSendResult.getErrmsg() == null) {
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
            MessageSendResult messageSendResult = MessageAPI.messageMassSendall(TokenManager.getToken("wx43a128a6adebadd4"), massMessage);
            if (messageSendResult.getErrmsg() == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
            }
            ErrorException errorException = new ErrorException();
            errorException.setMessage(new IllegalArgumentException());
            errorException.setErrorMessage(messageSendResult.getErrmsg());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(messageSendResult).build();
        }
        return null;
    }

//    materialGet_material

    /**
     * 获取永久素材根据mediaId
     */
    @POST
    @Path("get-source-by-media-id")
    public List<SourceImageFont> getSourceByMediaId(@QueryParam("typeFlag") String typeFlag) {
        if (typeFlag.equals("1")) {//图文素材
            List<SourceImageFont> sourceImageFonts = sourceImageFontFacade.findAll(SourceImageFont.class);
            if (sourceImageFonts != null && !sourceImageFonts.isEmpty()) {
                for (SourceImageFont sourceImageFont : sourceImageFonts) {
//                   byte[] bytes=MaterialAPI.materialGet_material(TokenManager.getToken("wx43a128a6adebadd4"),sourceImageFont.getMediaId());
//                   String source=new String(bytes);
//                   JSONObject.parse(bytes);
//                   System.out.println(source);
                    String imageLocalUrl = sourceImageFacade.findImageLocalUrlByMediaId(sourceImageFont.getThumbMediaId());
                    if (imageLocalUrl != null && !"".equals(imageLocalUrl)) {
                        sourceImageFont.setImage("<img src=" + imageLocalUrl + " width=100%/>");
                    }
                }
            }
            return sourceImageFonts;
        }
        return null;
    }

    @GET
    @Path("load-image-font")
    public List<ImageVo> loadImageFont() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
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
            sb.append("<div><img src=" + imageLocalUrl + " style=width:100%;></div>");
            sb.append("<div>" + sourceImageFont.getDigest() + "</div>");
            sb.append("</div>");
            imageVo.setImage(sb.toString());
            imageVos.add(imageVo);
        }
        return imageVos;
    }

    /**
     * @return
     */
    @GET
    @Path("load-image")
    public List<ImageVo> loadImage() {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<ImageVo> imageVos = new ArrayList<ImageVo>();
        List<SourceImage> list = sourceImageFacade.findAll(SourceImage.class);
        if (list != null && !list.isEmpty()) {
            for (SourceImage sourceImage : list) {
                ImageVo imageVo = new ImageVo();
                imageVo.setImage("<img src='" + (addr + sourceImage.getImageLocalUrl()) + "' style=width:100%;>");
                imageVo.setMediaId(sourceImage.getMediaId());
                imageVo.setId(sourceImage.getId());
                imageVos.add(imageVo);
            }
        }
        return imageVos;
    }


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
     * 回复user
     *
     * @param fromUserName
     * @param replyContent
     * @return
     */
    @POST
    @Path("reply-user")
    public Response repplyUser(@QueryParam("openId") String fromUserName, @QueryParam("replyContet") String replyContent, @QueryParam("toUserName") String toUserName) {
        // todo 回复方法的实现
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
     * @return
     */
    @GET
    @Path("load-message-list")
    public List<MessageVo> loadMessageList(@QueryParam("startTime") String startTime, @QueryParam("endTime") String endTime) {
        return requestMessageFacade.findByTime(startTime, endTime);
    }


}
