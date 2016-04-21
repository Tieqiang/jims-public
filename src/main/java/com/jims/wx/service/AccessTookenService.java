package com.jims.wx.service;

import com.jims.wx.entity.AccessTooken;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.AccessTookenFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 2016/4/5.
 */
@Path("access-tooken")
@Produces("application/json")
public class AccessTookenService {
    private AccessTookenFacade accessTookenFacade;

    @Inject
    public AccessTookenService (AccessTookenFacade accessTookenFacade){
        this.accessTookenFacade=accessTookenFacade;
    }

    @GET
    @Path("list-all")
    @Produces({MediaType.APPLICATION_JSON})
    public List<AccessTooken> listAll() {
        return accessTookenFacade.findAll(AccessTooken.class);
    }

    /**
     * 保存公众号信息
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<AccessTooken> beanChangeVo) {
        try {
            List<AccessTooken> newUpdateDict = new ArrayList<>();
            newUpdateDict = accessTookenFacade.save(beanChangeVo);
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
