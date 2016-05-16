package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.AppSetVo;
import weixin.popular.api.UserAPI;
import weixin.popular.bean.BaseResult;
import weixin.popular.support.TokenManager;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjing on 2016/3/14.
 */
@Path("feed-back")
@Produces("application/json")
public class FeedBackService {



    private FeedBackTargetFacade feedBackTargetFacade;
    private FeedBackResultFacade feedBackResultFacade;
    private AppUserFacade appUserFacade;
    private PatInfoFacade patInfoFacade;
    private PatVsUserFacade patVsUserFacade;
    private HttpServletResponse response;
    @Inject
    public FeedBackService(FeedBackTargetFacade feedBackTargetFacade,FeedBackResultFacade feedBackResultFacade,AppUserFacade appUserFacade,PatInfoFacade patInfoFacade,HttpServletResponse response,PatVsUserFacade patVsUserFacade) {
        this.feedBackTargetFacade = feedBackTargetFacade;
        this.feedBackResultFacade=feedBackResultFacade;
        this.appUserFacade=appUserFacade;
        this.patInfoFacade=patInfoFacade;
        this.response=response;
        this.patVsUserFacade=patVsUserFacade;
    }
    @GET
    @Path("find-content-by-name")
    public FeedBackTarget getInfo(@QueryParam("feedTargetId") String feedTargetId){
        return this.feedBackTargetFacade.findByName(feedTargetId);
    }
     /**
      * 保存用户反馈的结果
     * @param feedTargetId
     * @return
     */
    @GET
    @Path("submit-result")
    public String submitResult(@QueryParam("feedTargetId") String feedTargetId,@QueryParam("openId") String openId,@QueryParam("feedcBackResult") String feedcBackResult){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            AppUser appUser=appUserFacade.findAppUserByOpenId(openId);
            PatInfo patInfo=patInfoFacade.findById(appUser.getPatId());
            FeedBackResult f=new FeedBackResult();
            f.setFeedBackContent(feedcBackResult);
            f.setFeedBackTargetId(feedBackTargetFacade.findByName(feedTargetId).getId());
            f.setFeedBackTargetName(feedBackTargetFacade.findByName(feedTargetId).getFeedTarget());
            f.setPatName(patInfo.getName());
            f.setFeedTime(sdf.format(new Date()));
            feedBackResultFacade.save(f);
            response.sendRedirect("/views/his/public/user-feed-success.html");
         } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @GET
    @Path("find-all")
    public List<FeedBackTarget> findAll(){
        return feedBackTargetFacade.findAll(FeedBackTarget.class);
    }
    @Path("save")
    @POST
    public Response save(FeedBackTarget feedBackTarget){
        try {
            feedBackTargetFacade.save(feedBackTarget);
            return Response.status(Response.Status.OK).entity(feedBackTarget).build() ;
        }catch(Exception e ){
            ErrorException errorException = new ErrorException() ;
            errorException.setMessage(e);
            return Response.status(Response.Status.OK).entity(errorException).build() ;
        }
    }
    @GET
    @Path("feed-back-result")
    public List<FeedBackResult> feedBackResult(){
       return feedBackResultFacade.findAll(FeedBackResult.class);
    }

      @POST
      @Path("delete-result")
      public Response delete(@QueryParam("ids") String ids) {
            try {
                String[] idStr = ids.split(",");
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < idStr.length; i++) {
                    list.add(idStr[i]);
                }
                feedBackResultFacade.removeByIds(FeedBackResult.class, list);
                return Response.status(Response.Status.OK).entity(list).build();
            } catch (Exception e) {
                ErrorException errorException = new ErrorException();
                errorException.setMessage(e);
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
            }
        }
}
