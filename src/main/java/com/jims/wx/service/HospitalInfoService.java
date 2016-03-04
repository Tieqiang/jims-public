package com.jims.wx.service;

import com.jims.wx.entity.HospitalInfo;
import com.jims.wx.facade.HospitalInfoFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Dt on 2016/3/4.
 */
@Path("hospital-info")
public class HospitalInfoService {

    private HospitalInfoFacade hospitalInfoFacade;

    @Inject
    public HospitalInfoService(HospitalInfoFacade hospitalInfoFacade){
        this.hospitalInfoFacade = hospitalInfoFacade;
    }

    @Path("list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<HospitalInfo> findAllHospital(){
        List<HospitalInfo> hospitalDicts = hospitalInfoFacade.findAll(HospitalInfo.class) ;
        for (HospitalInfo dict :hospitalDicts){
            System.out.println(dict.getAppId());
        }
        return hospitalDicts ;
    }




    @Path("add")
    @POST
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response addHospitalInfo(HospitalInfo dict){

        HospitalInfo merge = hospitalInfoFacade.addHospitalInfo(dict);
        System.out.println(merge.getAppId());

        return Response.status(Response.Status.OK).entity(dict).build() ;
    }

    @Path("update")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateHospitalInfo(HospitalInfo dict){

        HospitalInfo merge = hospitalInfoFacade.updateHospitalInfo(dict);
        System.out.println(merge.getAppId());

        return Response.status(Response.Status.OK).entity("success").build() ;
    }

    @Path("delete/{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response deleteHospitalInfo(@PathParam("id")String id){

        hospitalInfoFacade.deleteHospitalInfo(id);

        return Response.status(Response.Status.OK).build() ;
    }
}
