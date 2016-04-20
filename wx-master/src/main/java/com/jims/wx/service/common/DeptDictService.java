package com.jims.wx.service.common;



import com.jims.wx.entity.DeptDict;
import com.jims.wx.facade.DeptDictFacade;
import com.jims.wx.util.PinYin2Abbreviation;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by heren on 2015/9/15.
 */
@Path("dept-dict")
@Produces("application/json")
public class DeptDictService {
//find-by-id
    private DeptDictFacade deptDictFacade ;

    @Inject
    public DeptDictService(DeptDictFacade deptDictFacade) {
        this.deptDictFacade = deptDictFacade;
    }

    @GET
    @Path("list")
    public List<DeptDict> list(@QueryParam("hospitalId")String hospitalId){
        return deptDictFacade.findByHospitalId(hospitalId) ;
    }
    @GET
    @Path("list-all")
    public List<DeptDict> listAll(){
        return deptDictFacade.findAll(DeptDict.class);
    }
    /**
     *根据id查询对象
     * @param deptId
     * @return
     */
    @GET
    @Path("find-by-id")
    public DeptDict findById(@QueryParam("deptId")String deptId){
        return deptDictFacade.findById(deptId) ;
    }

    @GET
    @Path("list-all")
    public List<DeptDict> listAll(){
        return deptDictFacade.findAll(DeptDict.class);
    }


    /**
     * 查询未与核算单元对照的科室
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list-with-recking")
    public List<DeptDict> listWidthNotRecking(@QueryParam("hospitalId")String hospitalId){
        return deptDictFacade.findByHospitalIdWithRecking(hospitalId) ;
    }
    /**
     *根据id查询对象
     * @param deptId
     * @return
     */
    @GET
    @Path("find-by-id")
    public DeptDict findById(@QueryParam("deptId")String deptId){
        return deptDictFacade.findById(deptId) ;
    }
    /**
     * 查询已经与核算单元对照的科室
     * @param hospitalId
     * @return
     */
    @GET
    @Path("list-with-recked")
    public List<DeptDict> listWithRecking(@QueryParam("hospitalId")String hospitalId){
        return deptDictFacade.findByHospitalIdWithRecked(hospitalId) ;
    }

    @GET
    @Path("list-width-recked-by-acct")
    public List<DeptDict> listWidthReckedByAcctId(@QueryParam("hospitalId")String hospitalId,@QueryParam("acctDeptId")String acctDeptId){
        return deptDictFacade.findByHospitalIdAndAcctDeptId(hospitalId,acctDeptId) ;
    }


    @GET
    @Path("list-by-input")
    public List<DeptDict>  listByQuery(@QueryParam("hospitalId")String hospitalId,@QueryParam("q")String q ){
        List<DeptDict> byHospitalId = deptDictFacade.findByHospitalIdAndQuer(hospitalId,q);
        return byHospitalId ;
    }

    @POST
    @Path("add")
    public Response addDeptDict(DeptDict deptDict){
        deptDict.setInputCode(PinYin2Abbreviation.cn2py(deptDict.getDeptName()));
        DeptDict dict = deptDictFacade.saveOrUpdate(deptDict) ;
        return Response.status(Response.Status.OK).entity(dict).build() ;
    }


    @DELETE
    @Path("del/{deptId}")
    public Response delDeptDict(@PathParam("deptId")String deptId){
        DeptDict deptDict = deptDictFacade.deleteById(deptId);
        return Response.status(Response.Status.OK).entity(deptDict).build() ;
    }

}
