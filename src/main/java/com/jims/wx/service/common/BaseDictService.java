package com.jims.wx.service.common;


import com.jims.wx.entity.BaseDict;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.BaseDictFacade;
import com.jims.wx.vo.BeanChangeVo;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by heren on 2015/10/9.
 */
@Path("base-dict")
@Produces("application/json")
public class BaseDictService {


    private BaseDictFacade baseDictFacade ;


    @Inject
    public BaseDictService(BaseDictFacade baseDictFacade) {
        this.baseDictFacade = baseDictFacade;
    }

    @GET
    @Path("list")
    @Produces("application/json")
    public List<BaseDict> listBaseDict(){
        return baseDictFacade.findAll(BaseDict.class) ;
    }

    /**
     * 根据 字典类型获取字典的列表
     * @param baseType
     * @return
     */
    @GET
    @Path("list-by-type")
    public List<BaseDict> listBaseDict(@QueryParam("baseType")String baseType, @QueryParam("length")int length){

        return baseDictFacade.findByBaseType(baseType,length) ;
    }


    /**
     * 保存，修改的项目
     * @param baseDictBeanChangeVo
     * @return
     */
    @POST
    @Path("merge")
    public Response mergerBaseDict(BeanChangeVo<BaseDict> baseDictBeanChangeVo){
        try {
            baseDictFacade.mergeBaseDict(baseDictBeanChangeVo) ;
            return Response.status(Response.Status.OK).entity(baseDictBeanChangeVo).build() ;
        }catch(Exception e){
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，提交失败！");
            } else {
                errorException.setErrorMessage("提交失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }

    }


}
