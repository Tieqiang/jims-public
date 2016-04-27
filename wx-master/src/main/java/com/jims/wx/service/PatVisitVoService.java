package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.facade.PatVisitFacade;
import com.jims.wx.vo.PatVisitVo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
/**
 * Created by wei on 2016/4/22.
 */
@Path("pat-visit")
@Produces("application/json")
public class PatVisitVoService {

    private PatVisitFacade patVisitFacade;
    @Inject
    public PatVisitVoService(PatVisitFacade patVisitFacade) {
        this.patVisitFacade = patVisitFacade;
    }

    @GET
    @Path("list")
    public List<PatVisitVo> list(@QueryParam("patientId")String patientId){
        return  patVisitFacade.listPatVisitVo(patientId);
    }
}
