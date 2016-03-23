package com.jims.wx.service.common;



import com.jims.wx.entity.HospitalDict;
import com.jims.wx.facade.HospitalDictFacade;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by heren on 2015/9/10.
 */
@Path("hospital-dict")
public class HospitalDictService {

    private HospitalDictFacade hospitalDictFacade;

    @Inject
    public HospitalDictService(HospitalDictFacade hospitalDictFacade){
        this.hospitalDictFacade = hospitalDictFacade;
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

    @Path("update")
    @PUT
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response updateHospitalDict(HospitalDict dict){

        HospitalDict merge = hospitalDictFacade.updateHospitalDict(dict);
        System.out.println(merge.getHospitalName());

        return Response.status(Response.Status.OK).entity("success").build() ;
    }

    @Path("delete/{id}")
    @DELETE
    @Consumes({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public Response deleteHospitalDict(@PathParam("id")String id){

        hospitalDictFacade.deleteHospitalDict(id);

        return Response.status(Response.Status.OK).build() ;
    }
}
