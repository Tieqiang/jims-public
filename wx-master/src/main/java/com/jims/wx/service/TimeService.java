package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.facade.InpBillDetailFacade;
import com.jims.wx.vo.InpBillDetailVo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * Created by heren on 2016/2/24.
 */
@Path("time")
@Produces("application/json")
public class TimeService {

    private InpBillDetailFacade inpBillDetailFacade;

    @Inject
    public TimeService(InpBillDetailFacade inpBillDetailFacade) {
        this.inpBillDetailFacade = inpBillDetailFacade;
    }

    @GET
    @Path("now")
    public String getTime(){
        Date date = new Date() ;
        return date.toString() ;
    }

    @GET
    @Path("list")
    public List<InpBillDetailVo> list(@QueryParam("patientId") String patientId,@QueryParam("visitId")double visitId){
        return  inpBillDetailFacade.listInpBillVo(patientId,visitId);
    }
}
