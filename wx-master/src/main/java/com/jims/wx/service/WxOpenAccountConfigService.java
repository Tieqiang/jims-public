package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.entity.WxOpenAccountConfig;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.WxOpenAccountConfigFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fyg on 2016/3/31.
 */
@Path("wx-open-account-config")
@Produces("application/json")
public class WxOpenAccountConfigService {
    private WxOpenAccountConfigFacade wxOpenAccountConfigFacade;
    @Inject
    public WxOpenAccountConfigService(WxOpenAccountConfigFacade wxOpenAccountConfigFacade) {
        this.wxOpenAccountConfigFacade = wxOpenAccountConfigFacade;
    }

    /**
     * 根据医院ID查询该医院的公众号
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list")
    public List<WxOpenAccountConfig> findById(@QueryParam("hospitalId")String hospitalId){
        return wxOpenAccountConfigFacade.findById(hospitalId);
    }

    /**
     * 保存公众号信息
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<WxOpenAccountConfig> beanChangeVo) {
        try {
            List<WxOpenAccountConfig> newUpdateDict = new ArrayList<>();
            newUpdateDict = wxOpenAccountConfigFacade.save(beanChangeVo);
            return Response.status(Response.Status.OK).entity(newUpdateDict).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else if (errorException.getErrorMessage().toString().indexOf("违反完整约束") != -1) {
                errorException.setErrorMessage("数据存在关联数据，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }
}
