package com.jims.wx.service;

import com.google.inject.Inject;
import com.jims.wx.entity.*;
import com.jims.wx.expection.ErrorException;
import com.jims.wx.facade.*;
import com.jims.wx.vo.PatInfoVo;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

/**
 * Created by chenxy on 2016/4/17.
 * 智能导诊Service
 */
@Path("intelligent-guide")
@Produces("application/json")
public class IntelligentGuideService {
    private HttpServletRequest request;
    private HttpServletResponse response;


    private BodyPartFacade bodyPartFacade;
    private ClinicSymptomFacade clinicSymptomFacade;
    private ClinicSickessFacade clinicSickessFacade;
    private SymptomSicknessFacade symptomSicknessFacade;
    private DeptDictFacade deptDictFacade;

    @Inject
    public IntelligentGuideService(BodyPartFacade bodyPartFacade, ClinicSymptomFacade clinicSymptomFacade, ClinicSickessFacade clinicSickessFacade, SymptomSicknessFacade symptomSicknessFacade, HttpServletRequest request, HttpServletResponse response,DeptDictFacade deptDictFacade) {
        this.bodyPartFacade = bodyPartFacade;
        this.clinicSickessFacade = clinicSickessFacade;
        this.clinicSymptomFacade = clinicSymptomFacade;
        this.symptomSicknessFacade = symptomSicknessFacade;
        this.request = request;
        this.response = response;
        this.deptDictFacade=deptDictFacade;
    }

    /**
     * 查询所有症状
     *
     * @return
     */
    @Path("find-symptom")
    @GET
    public List<ClinicSymptom> findSymptom() {
        List<ClinicSymptom> clinicSymptoms = new ArrayList<ClinicSymptom>();
        List<ClinicSymptom> list = clinicSymptomFacade.findAll(ClinicSymptom.class);
        if (list!=null&&!list.isEmpty()) {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                ClinicSymptom clinicSymptom = (ClinicSymptom) iterator.next();
                String bodyPartId = clinicSymptom.getBodyPartId();
                String bodyPartName = bodyPartFacade.findNameById(bodyPartId);
                clinicSymptom.setBodyPartName(bodyPartName);
                clinicSymptoms.add(clinicSymptom);
            }
        }
        return clinicSymptoms;
    }

    /**
     * 查询所有的bodyPart
     *
     * @return
     */
    @POST
    @Path("find-body-part")
    public List<BodyPart> findBodyPart() {
        List<BodyPart> list = bodyPartFacade.findAll(BodyPart.class);
        if (list!=null&&!list.isEmpty())
            return list;
        return null;
    }

    /**
     * 保存症状
     *
     * @param clinicSymptom
     * @return
     */
    @POST
    @Path("save-symptom")
    public Response save(ClinicSymptom clinicSymptom) {
        try {
            clinicSymptomFacade.save(clinicSymptom);
            return Response.status(Response.Status.OK).entity(clinicSymptom).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

    /**
     * 根据ids批量或单条删除数据
     *
     * @param ids
     * @return
     */
    @POST
    @Path("delete-symptom")
    public Response delete(@QueryParam("ids") String ids) {
        try {
            String[] idStr = ids.split(",");
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < idStr.length; i++) {
                list.add(idStr[i]);
            }
            clinicSymptomFacade.removeByIds(ClinicSymptom.class, list);
            return Response.status(Response.Status.OK).entity(list).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

    /**
     * 根据身体部位主键Id查询对饮症状的集合
     *
     * @param bodyId
     * @return
     */
    @GET
    @Path("find-symptom-by-body")
    public Map<String,Object>findSymptomByBodyId(@QueryParam("bodyId") String bodyId,@QueryParam("sexValue") String sexValue) {
        Map<String,Object> map=new HashMap<String,Object>();
        String bodyName=bodyPartFacade.findNameById(bodyId);
        List<ClinicSymptom> list= clinicSymptomFacade.findSymptomByBodyId(bodyId,sexValue);
        map.put("bodyName",bodyName);
        map.put("list",list);
        return map;
    }

    /**
     * 根据所选择的症状来查询可能的疾病
     *
     * @param ids
     * @return
     */
    @GET
    @Path("find-sickness-by-symptom")
    public List<ClinicSickness> findSicknessBySymptom(@QueryParam("ids") String ids,@QueryParam("sexValue") String sexValue) {
        List<ClinicSickness> cs=new ArrayList<ClinicSickness>();
        List<String> list = symptomSicknessFacade.findSicknessBySymptom(ids,sexValue);//疾病 的ids
        List<ClinicSickness> clinicSicknesses =null;
        if(list!=null&&!list.isEmpty()){
             clinicSicknesses = clinicSickessFacade.findByIds(list);
        }
         if (clinicSicknesses!=null&&!clinicSicknesses.isEmpty()){
              for(ClinicSickness clinicSickness:clinicSicknesses) {
                  String deptId = clinicSickness.getDeptId();
                  String deptName = this.deptDictFacade.findDeptDictByDeptId(deptId);
                  clinicSickness.setDeptName(deptName);
                  cs.add(clinicSickness);
              }
        }
        return cs;
    }

    /**
     *根据bodyId 查询bodyName
     * @return
     */
    @GET
    @Path("find-body-name")
    public String findBodyName(@QueryParam("bodyId") String bodyId){
        String name=bodyPartFacade.findNameById(bodyId);
        if(name!=null && !"".equals(name))
            return name;
            return "";
    }

    /**
     * 查询所有疾病
     * @return
     */
    @GET
    @Path("find-sickness")
    public List<ClinicSickness> findSickness(){
        List<ClinicSickness> clinicSicknesses=new ArrayList<ClinicSickness>();
        List<ClinicSickness> list=clinicSickessFacade.findAll(ClinicSickness.class);
        if(list!=null&&!list.isEmpty()) {
            for (ClinicSickness clinicSickness : list) {
                String deptId = clinicSickness.getDeptId();
                String deptName = deptDictFacade.findDeptDictByDeptId(deptId);
                clinicSickness.setDeptName(deptName);
                clinicSicknesses.add(clinicSickness);
            }
            return clinicSicknesses;
        }else{
            return null;
        }
      }

    /**
     * 查找所有科室
     * @return
     */
    @POST
    @Path("find-dept-dict")
    public List<DeptDict> findDeptDict(){
        return deptDictFacade.findAll(DeptDict.class);
    }


    /**
     * 保存疾病
     * @param clinicSickness
     * @return
     */
    @POST
    @Path("save-sickness")
    public Response saveSickness(ClinicSickness clinicSickness) {
        try {
            clinicSickessFacade.save(clinicSickness);
            return Response.status(Response.Status.OK).entity(clinicSickness).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

//    delete-sickness?ids=' + id

    /**
     * 根据ids批量或单条删除数据
     *
     * @param ids
     * @return
     */
    @POST
    @Path("delete-sickness")
    public Response deleteSickness(@QueryParam("ids") String ids) {
        try {
            String[] idStr = ids.split(",");
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < idStr.length; i++) {
                list.add(idStr[i]);
            }
            clinicSickessFacade.removeByIds(ClinicSickness.class, list);
            return Response.status(Response.Status.OK).entity(list).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

    /**
     * 查询关系
     * @return
     */
    @GET
    @Path("find-symptom-vs-sickness")
    public List<SymptomVsSickness> findSVS(){
        List<SymptomVsSickness> symptomVsSicknesses=new ArrayList<SymptomVsSickness>();
        List<SymptomVsSickness> list=symptomSicknessFacade.findAll(SymptomVsSickness.class);
        if(list!=null&&!list.isEmpty()){
            for(SymptomVsSickness symptomVsSickness:list){
                String symptomName=clinicSymptomFacade.findNameById(symptomVsSickness.getSymptomId());
                String sicknessName=clinicSickessFacade.findNameById(symptomVsSickness.getSicknessId());
                symptomVsSickness.setSymptomName(symptomName);
                symptomVsSickness.setSicknessName(sicknessName);
                symptomVsSicknesses.add(symptomVsSickness);
             }
        }
        return symptomVsSicknesses;
    }

    /**
     * load data for combox
     * @return
     */
    @POST
    @Path("load-symptom")
    public List<ClinicSymptom> loadSympton(){
        return clinicSymptomFacade.findAll(ClinicSymptom.class);
    }

    /**
     * load data for combox
     * @return
     */
    @POST
    @Path("load-sickness")
    public List<ClinicSickness> loadSickness(){
        return clinicSickessFacade.findAll(ClinicSickness.class);
    }
     /**
      * @param symptomVsSickness
     *  @return
     */
    @POST
    @Path("save-symptom-vs-sickness")
    public Response saveVVS(SymptomVsSickness symptomVsSickness) {
        try {
            symptomSicknessFacade.save(symptomVsSickness);
            return Response.status(Response.Status.OK).entity(symptomVsSickness).build();
        } catch (Exception e) {
            ErrorException errorException = new ErrorException();
            errorException.setMessage(e);
            if (errorException.getErrorMessage().toString().indexOf("最大值") != -1) {
                errorException.setErrorMessage("输入数据超过长度！");
            } else if (errorException.getErrorMessage().toString().indexOf("唯一") != -1) {
                errorException.setErrorMessage("数据已存在，保存失败！");
            } else {
                errorException.setErrorMessage("保存失败！");
            }
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorException).build();
        }
    }

    @GET
    @Path("body-list")
    public List<BodyPart> bodyPartList(){
        return bodyPartFacade.findAll(BodyPart.class);
    }




}




