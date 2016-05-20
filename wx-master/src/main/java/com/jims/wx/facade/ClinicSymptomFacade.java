package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ClinicSymptom;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by chenxy on 2016/3/16.
 */
public class ClinicSymptomFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public ClinicSymptomFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 保存症状信息
     *
     * @param clinicSymptom
     */
    @Transactional
    public void save(ClinicSymptom clinicSymptom) {
        entityManager.merge(clinicSymptom);
    }

    /**
     * 批量删除
     *
     * @param clinicSymptomClass
     * @param list
     */
    @Transactional
    public void removeByIds(Class<ClinicSymptom> clinicSymptomClass, List<String> list) {
        removeByStringIds(clinicSymptomClass, list);
    }

    /**
     * 根据身体部位主键Id查询对饮症状的集合
     *
     * @param bodyId
     * @return
     */
    public List<ClinicSymptom> findSymptomByBodyId(String bodyId, String sexValue) {
        if ("1".equals(sexValue)) {
            sexValue = "女";
        } else if ("0".equals(sexValue)) {
            sexValue = "男";
        }
        String sql = "from ClinicSymptom where bodyPartId='" + bodyId + "' and (sex='" + sexValue + "' or sex='-1')";

        List<ClinicSymptom> list = entityManager.createQuery(sql).getResultList();

        if (!list.isEmpty())
            return list;
        return null;
    }

    /**
     * @param symptomId
     * @return
     */
    public String findNameById(String symptomId) {
        String name = (String) entityManager.createQuery("select name from ClinicSymptom where id='" + symptomId + "'").getSingleResult();
        if (name != null && !"".equals(name))
            return name;
        return null;
    }
}
