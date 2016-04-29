package com.jims.wx.service.common;


import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.HospitalInfoFacade;
import com.jims.wx.facade.ModuleDictFacade;
import com.jims.wx.facade.RoleDictFacade;
import com.jims.wx.facade.StaffDictFacade;
import com.jims.wx.util.reps.EnscriptAndDenScript;
import com.jims.wx.vo.AppSetVo;
import com.jims.wx.vo.Config;
import com.jims.wx.vo.ErrorMessager;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by heren on 2015/9/22.
 */
@Path("login")
@Produces("application/json")
public class LoginService {
    private ModuleDictFacade moduleDictFacade;
    private StaffDictFacade staffDictFacade ;
    private RoleDictFacade roleDictFacade ;
    private HttpServletResponse resp;
    private HttpServletRequest request;
    private HospitalInfoFacade hospitalInfoFacade ;


    @Inject
    public LoginService(HttpServletRequest request, HttpServletResponse resp, ModuleDictFacade moduleDictFacade, StaffDictFacade staffDictFacade, RoleDictFacade roleDictFacade, HospitalInfoFacade hospitalInfoFacade) {
        this.moduleDictFacade = moduleDictFacade;
        this.staffDictFacade = staffDictFacade;
        this.roleDictFacade = roleDictFacade;
        this.resp = resp;
        this.request = request;
        this.hospitalInfoFacade = hospitalInfoFacade;
    }



    @POST
    @Path("check-login")
    public Response loing(@QueryParam("loginName")String loginName ,@QueryParam("password")String password){
        try{
            StaffDict staffDict= staffDictFacade.findByLoginName(loginName) ;
            if(staffDict==null){
                ErrorMessager errorMessager = new ErrorMessager("错误的用户名", "系统登录");
                return Response.status(Response.Status.OK).entity(errorMessager).build() ;
            }
            String denPassword = EnscriptAndDenScript.denscriptFromHis(staffDict.getPassword()) ;
            if(!denPassword.equals(password)){
                ErrorMessager errorMessager = new ErrorMessager("错误的密码", "系统登录");
                return Response.status(Response.Status.OK).entity(errorMessager).build() ;
            }else{
                HttpSession session = request.getSession() ;
                session.setAttribute("loginName",staffDict.getLoginName());
                session.setAttribute("staffName", staffDict.getName());
                return Response.status(Response.Status.OK).entity(staffDict).build() ;
            }
        }catch(Exception e){
            ErrorException errorException= new ErrorException() ;
            errorException.setMessage(e);
            return Response.status(Response.Status.OK).build() ;
        }
    }

    @Path("get-login-info")
    @GET
    public Config getLoginInfo(){
        Config config = new Config() ;
        HttpSession session = request.getSession();
        AppSetVo appSetVo = hospitalInfoFacade.findAppSetVo() ;
        config.setAppId(appSetVo.getAppId());
        config.setHospitalName(appSetVo.getHospitalName());
        config.setModuleName("微信公众平台系统");
        config.setModuleId("402886f350a6bd4f0150a6c0c47c0000");
        //config.setLoginName(session.getAttribute("staffName").toString());
        config.setStaffName(session.getAttribute("loginName").toString());
        return config ;
    }
}


