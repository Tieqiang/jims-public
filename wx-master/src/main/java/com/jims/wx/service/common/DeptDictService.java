package com.jims.wx.service.common;


import com.jims.wx.entity.DeptDict;
import com.jims.wx.entity.DoctInfo;
import com.jims.wx.facade.DeptDictFacade;
import com.jims.wx.facade.DoctInfoFacade;
import com.jims.wx.util.PinYin2Abbreviation;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by heren on 2015/9/15.
 */
@Path("dept-dict")
@Produces("application/json")
public class DeptDictService {
    //find-by-id
    private DeptDictFacade deptDictFacade;
    private HttpServletRequest request;
    private DoctInfoFacade doctInfoFacade;

    @Inject
    public DeptDictService(DeptDictFacade deptDictFacade, HttpServletRequest request, DoctInfoFacade doctInfoFacade) {
        this.request = request;
        this.deptDictFacade = deptDictFacade;
        this.doctInfoFacade = doctInfoFacade;
    }

    @GET
    @Path("list")
    public List<DeptDict> list() {
        List<DeptDict> list = deptDictFacade.findAll(DeptDict.class);
        return getList(list);
    }

    /**
     * @param list
     * @return
     */
    private List<DeptDict> getList(List<DeptDict> list) {
        String addr = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        List<DeptDict> deptDicts = new ArrayList<DeptDict>();
        for (DeptDict deptDict : list) {
            deptDict.setImgUrl2(deptDict.getImgUrl());
            deptDict.setImgUrl(addr + deptDict.getImgUrl());
            deptDict.setImg(deptDict.getImgUrl());
            deptDicts.add(deptDict);
        }
        return deptDicts;
    }

    /**
     * 根据id查询对象
     *
     * @param deptId
     * @return
     */
    @GET
    @Path("find-by-id")
    public DeptDict findById(@QueryParam("deptId") String deptId) {

        return deptDictFacade.findById(deptId);
    }

    @GET
    @Path("list-all")
    public List<DeptDict> listAll() {
         return getList(deptDictFacade.findAllByCondition());
    }


    /**
     * 查询未与核算单元对照的科室
     *
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list-with-recking")
    public List<DeptDict> listWidthNotRecking(@QueryParam("hospitalId") String hospitalId) {
        return deptDictFacade.findByHospitalIdWithRecking(hospitalId);
    }
//    /**
//     *根据id查询对象
//     * @param deptId
//     * @return
//     */
//    @GET
//    @Path("find-by-id")
//    public DeptDict findById(@QueryParam("deptId")String deptId){
//        return deptDictFacade.findById(deptId) ;
//    }

    /**
     * 查询已经与核算单元对照的科室
     *
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list-with-recked")
    public List<DeptDict> listWithRecking(@QueryParam("hospitalId") String hospitalId) {
        return deptDictFacade.findByHospitalIdWithRecked(hospitalId);
    }

    @GET
    @Path("list-width-recked-by-acct")
    public List<DeptDict> listWidthReckedByAcctId(@QueryParam("hospitalId") String hospitalId, @QueryParam("acctDeptId") String acctDeptId) {
        return deptDictFacade.findByHospitalIdAndAcctDeptId(hospitalId, acctDeptId);
    }


    @GET
    @Path("list-by-input")
    public List<DeptDict> listByQuery(@QueryParam("hospitalId") String hospitalId, @QueryParam("q") String q) {
        List<DeptDict> byHospitalId = deptDictFacade.findByHospitalIdAndQuer(hospitalId, q);
        return byHospitalId;
    }

    @POST
    @Path("add")
    public Response addDeptDict(DeptDict deptDict) {
        deptDict.setInputCode(PinYin2Abbreviation.cn2py(deptDict.getDeptName()));
        DeptDict dict = deptDictFacade.saveOrUpdate(deptDict);
        return Response.status(Response.Status.OK).entity(dict).build();
    }

//    DeptDict
    @DELETE
    @Path("del/{deptId}")
    public Response delDeptDict(@PathParam("deptId") String deptId) {
        DeptDict deptDict = deptDictFacade.deleteById(deptId);
        return Response.status(Response.Status.OK).entity(deptDict).build();
    }

    /**
     * app科室列表模糊查询
     *
     * @param likeSearch 科室
     * @return
     */
    @GET
    @Path("query-like")
    public List<DeptDict> queryLike(@QueryParam("likeSearch") String likeSearch) {
        List<DeptDict> deptDicts = null;
        deptDicts = deptDictFacade.queryLike(likeSearch);
        return getList(deptDicts);
    }

}
