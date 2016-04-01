package com.jims.wx.service;

import com.jims.wx.entity.ClinicMaster;
import com.jims.wx.entity.PayWayDict;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.PayWayDictFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fyg on 2016/3/21.
 */
@Path("pay-way-dict")
@Produces("application/json")
public class PayWayDictService {
    private PayWayDictFacade payWayDictFacade;

    @Inject
    public PayWayDictService(PayWayDictFacade payWayDictFacade) {
        this.payWayDictFacade = payWayDictFacade;
    }

    //查询所有支付方式
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public List<PayWayDict> findAllPayWayDict(){
        List<PayWayDict> payWayDicts = payWayDictFacade.findAll(PayWayDict.class);
        for (PayWayDict payWayDict : payWayDicts){
            System.out.println("支付方式:" + payWayDict.getPayWayName());
        }
        return payWayDicts;
    }

    /**
     * 保存增删改
     *
     * @param beanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response save(BeanChangeVo<PayWayDict> beanChangeVo) {
        try {
            List<PayWayDict> newUpdateDict = new ArrayList<>();
            newUpdateDict = payWayDictFacade.save(beanChangeVo);
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
