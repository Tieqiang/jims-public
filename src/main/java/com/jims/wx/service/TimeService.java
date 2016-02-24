package com.jims.wx.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by heren on 2016/2/24.
 */
@Path("time")
public class TimeService {

    @GET
    @Path("now")
    public String getTime(){
        Date date = new Date() ;
        return date.toString() ;
    }
}
