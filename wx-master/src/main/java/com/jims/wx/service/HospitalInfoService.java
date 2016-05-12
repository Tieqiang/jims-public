package com.jims.wx.service;

import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.HospitalInfoFacade;
import com.jims.wx.vo.AppSetVo;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dt on 2016/3/4.
 */
@Path("hospital-info")
@Produces("application/json")
public class HospitalInfoService {

    private HospitalInfoFacade hospitalInfoFacade;
    private HttpServletRequest request;

    @Inject
    public HospitalInfoService(HospitalInfoFacade hospitalInfoFacade, HttpServletRequest request){
        this.hospitalInfoFacade = hospitalInfoFacade;
        this.request=request;
    }


    @Path("list-all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalInfo> findAllHospital(){
        List<HospitalInfo> hospitalInfos=new ArrayList<HospitalInfo>();
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<HospitalInfo> list= hospitalInfoFacade.findAll(HospitalInfo.class) ;
        //重新赋值 imgUrl
        for(HospitalInfo hospitalInfo:list){
            hospitalInfo.setHospitalImg(addr+hospitalInfo.getHospitalImg());
            hospitalInfos.add(hospitalInfo);
        }
        return hospitalInfos;
    }

    /**
     * 保存修改
     *
     * @param hospitalInfo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(@QueryParam("tranContent") String tranContent, AppSetVo hospitalInfo ) {
        try {
            if (null != hospitalInfo) {
                hospitalInfo.setContent(tranContent.getBytes("UTF-8"));
                hospitalInfo = hospitalInfoFacade.save(hospitalInfo);
            }
            return Response.status(Response.Status.OK).entity(hospitalInfo).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据重复，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

    @Path("save")
    @POST
    public Response save(AppSetVo appSetVo){
        try {
            hospitalInfoFacade.addSet(appSetVo) ;
            return Response.status(Response.Status.OK).entity(appSetVo).build() ;
        }catch(Exception e ){
            ErrorException errorException = new ErrorException() ;
            errorException.setMessage(e);
            return Response.status(Response.Status.OK).entity(errorException).build() ;
        }
    }

    @Path("get")
    @GET
    public AppSetVo getAppSetVo(){
         return hospitalInfoFacade.findAppSetVo();
    }

}
