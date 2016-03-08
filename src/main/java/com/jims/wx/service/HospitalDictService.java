package com.jims.wx.service;

import com.jims.wx.entity.HospitalDict;
import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.facade.HospitalDictFacade;
import com.jims.wx.facade.HospitalInfoFacade;
import com.jims.wx.vo.HospInfoDictVo;
import oracle.sql.BLOB;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.Blob;
import java.util.List;

/**
 * Created by Dt on 2016/3/3.
 */
@Path("hospital-dict")
@Produces("application/json")
public class HospitalDictService {

    private HospitalDictFacade hospitalDictFacade;
    private HospitalInfoFacade hospitalInfoFacade;

    @Inject
    public HospitalDictService(HospitalDictFacade hospitalDictFacade,HospitalInfoFacade hospitalInfoFacade){

        this.hospitalDictFacade = hospitalDictFacade;
        this.hospitalInfoFacade = hospitalInfoFacade;
    }

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalDict> findAllHospital(){
        List<HospitalDict> hospitalDicts = hospitalDictFacade.findAll(HospitalDict.class) ;
        for (HospitalDict dict :hospitalDicts){
            System.out.println(dict.getHospitalName());
        }
        return hospitalDicts ;
    }




    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addHospitalDict(HospitalDict dict){

        HospitalDict merge = hospitalDictFacade.addHospitalDict(dict);
        System.out.println(merge.getHospitalName());

        return Response.status(Response.Status.OK).entity(dict).build() ;
    }

    @Path("add_vo")
    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addHospitalInfoDictVo(HospInfoDictVo dict){
        HospitalDict hospitalDict = new HospitalDict();
        HospitalInfo hospitalInfo = new HospitalInfo();

        hospitalDict.setHospitalName(dict.getHospitalName());
        hospitalDict.setUnitCode(dict.getUnitCode());
        hospitalDict.setLocation(dict.getLocation());
        hospitalDict.setZipCode(dict.getZipCode());
        hospitalDict.setOrganizationFullCode(dict.getOrganizationFullCode());
        hospitalDict.setParentHospital(dict.getParentHospital());
        hospitalDict.setDeptDicts(dict.getDeptDicts());

        HospitalDict dict1 = hospitalDictFacade.addHospitalDict(hospitalDict);

        hospitalInfo.setHospitalId(dict1.getId());
        hospitalInfo.setAppId(dict.getAppId());
        //blob  十六进制错误
//        hospitalInfo.setContent(dict.getContent());
        hospitalInfo.setInfoUrl(dict.getInfoUrl());

        HospitalInfo info = hospitalInfoFacade.addHospitalInfo(hospitalInfo);

        System.out.println(info.getAppId());

        return Response.status(Response.Status.OK).entity(info).build() ;
    }

    @Path("update")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateHospitalDict(HospitalDict dict){

        HospitalDict merge = hospitalDictFacade.updateHospitalDict(dict);
        System.out.println(merge.getHospitalName());

        return Response.status(Response.Status.OK).entity(dict).build() ;
    }

    @Path("delete/{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response deleteHospitalDict(@PathParam("id")String id){

        hospitalDictFacade.deleteHospitalDict(id);

        return Response.status(Response.Status.OK).build() ;
    }

    @Path("find-by-hospitalId")
    @GET
    public HospitalDict findHospitalDictById(@QueryParam("hospitalId") String hospitalId){
        return hospitalDictFacade.findHospitalDictById(hospitalId);
    }

}
