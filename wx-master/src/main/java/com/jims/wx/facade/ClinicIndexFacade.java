package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicIndex;
import com.jims.wx.entity.ClinicTypeSetting;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.ComboboxVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class ClinicIndexFacade extends BaseFacade {
    private EntityManager entityManager;
    private ClinicTypeSettingFacade clinicTypeSettingFacade;

    @Inject
    public ClinicIndexFacade(EntityManager entityManager,ClinicTypeSettingFacade clinicTypeSettingFacade){
        this.entityManager=entityManager;
        this.clinicTypeSettingFacade=clinicTypeSettingFacade;
    }

    /**
     * 根据id 查询对象
     * @param clinicIndexId
     * @return
     */
    public ClinicIndex findById(String clinicIndexId) {
        List<ClinicIndex> list=entityManager.createQuery("from ClinicIndex where id='"+clinicIndexId+"'").getResultList();
        return list.get(0);
    }
    /**
     * 查询号别信息
     * @return
     */
    public List<ComboboxVo> findClinicIndexType(String typeId) {
         List<ComboboxVo> comboboxVoList=new ArrayList<ComboboxVo>();
         String sql="from ClinicIndex as c";
        if(typeId!=null&&!"".equals(typeId)){
             sql+=" where c.clinicTypeId='"+typeId+"'";
        }
        List<ClinicIndex> list=entityManager.createQuery(sql).getResultList();
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                ClinicIndex c=list.get(i);
                ComboboxVo v=new ComboboxVo();
                v.setId(c.getId());
                v.setText(c.getClinicLabel());
                comboboxVoList.add(v);
            }
        }
        return comboboxVoList;
    }
    //find by typeId
    public List<ClinicIndex> findByTypeId(String typeId){
        String sqls = "from ClinicIndex where 1=1";
        if(null != typeId && !typeId.trim().equals("")){
            sqls +=" and clinicTypeId='" +typeId+ "'";
        }
        return entityManager.createQuery(sqls).getResultList();
    }


//    /**
//     * 根据id 查询对象
//     * @param clinicIndexId
//     * @return
//     */
//    public ClinicIndex findById(String clinicIndexId) {
//        List<ClinicIndex> list=entityManager.createQuery("from ClinicIndex where id='"+clinicIndexId+"'").getResultList();
//        return list.get(0);
//    }
//    //find by typeId
//    public List<ClinicIndex> findByTypeId(String typeId){
//        String sqls = "from ClinicIndex where 1=1";
//        if(null != typeId && !typeId.trim().equals("")){
//            sqls +=" and clinicTypeId='" +typeId+ "'";
//        }
//        return entityManager.createQuery(sqls).getResultList();
//    }
//    /**
//     * 查询号别信息
//     * @return
//     */
//    public List<ComboboxVo> findClinicIndexType(String typeId) {
//        List<ComboboxVo> comboboxVoList=new ArrayList<ComboboxVo>();
//        String sql="from ClinicIndex as c";
//        if(typeId!=null&&!"".equals(typeId)){
//            sql+=" where c.clinicTypeId='"+typeId+"'";
//        }
//        List<ClinicIndex> list=entityManager.createQuery(sql).getResultList();
//        if(list!=null&&list.size()>0){
//            for(int i=0;i<list.size();i++){
//                ClinicIndex c=list.get(i);
//                ComboboxVo v=new ComboboxVo();
//                v.setId(c.getId());
//                v.setText(c.getClinicLabel());
//                comboboxVoList.add(v);
//            }
//        }
//        return comboboxVoList;
//    }
    /**
     * 保存增删改
     *
     * @param beanChangeVo
     */
    @Transactional
    public List<ClinicIndex> save (BeanChangeVo<ClinicIndex> beanChangeVo){
        List<ClinicIndex> newUpdateSheet = new ArrayList<>();
        List<ClinicIndex> inserted =beanChangeVo.getInserted();
        List<ClinicIndex> updated =beanChangeVo.getUpdated();
        List<ClinicIndex> deleted =beanChangeVo.getDeleted();
//        inserted.
        for (ClinicIndex sheet:inserted){
            ClinicIndex merge =merge(sheet);
            newUpdateSheet.add(merge);
        }
        for (ClinicIndex sheet : updated) {
            ClinicIndex merge = merge(sheet);
            newUpdateSheet.add(merge);
        }
        List<String> ids = new ArrayList<>();
        for (ClinicIndex sheet : deleted) {
            ids.add(sheet.getId());
            newUpdateSheet.add(sheet);
        }
        this.removeByStringIds(ClinicIndex.class,ids);
        return newUpdateSheet;
    }

    /**
     *
     * @param clinicLabel
     * @return
     */
    public String findDeptInfo(String clinicLabel) {
        String sql="from ClinicIndex where clinicLabel='"+clinicLabel+"'";
        List<ClinicIndex> list=entityManager.createQuery(sql).getResultList();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getClinicDept();
        }
        return "";
    }


    /**
     *
     * @param clinicLabel
     * @return
     */
    public String findDoctInfo(String clinicLabel) {
        String sql="from ClinicIndex where clinicLabel='"+clinicLabel+"'";
        List<ClinicIndex> list=entityManager.createQuery(sql).getResultList();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getDoctorId();
        }
        return "";
    }

    /**
     * 根据医生Id查询科室代码
     * @param id
     * @return
     */
    public String findDeptCodeByDoctId(String id) {
//        ClinicIndex
        String sql="from ClinicIndex where doctorId='"+id+"'";
        List<ClinicIndex> list=entityManager.createQuery(sql).getResultList();
        if(list!=null&&!list.isEmpty()){
            return list.get(0).getClinicDept();
        }
        return "";
    }
}