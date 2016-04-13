package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.DeptDict;


import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by heren on 2015/9/15.
 */
public class DeptDictFacade extends BaseFacade {


    private EntityManager entityManager;

    @Inject
    public DeptDictFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * 根据 医院获取该医院的所有科室
     *
     * @param hospitalId
     * @return
     */
    public List<DeptDict> findByHospitalId(String hospitalId) {
        String hql = "from DeptDict as dept where dept.hospitalDict.id='" + hospitalId + "'";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }

    @Transactional
    public DeptDict saveOrUpdate(DeptDict deptDict) {
        DeptDict deptDict1 = super.merge(deptDict);
        return deptDict1;
    }

    @Transactional
    public DeptDict deleteById(String deptId) {
        DeptDict deptDict = get(DeptDict.class, deptId);
        remove(deptDict);
        return deptDict;
    }

    public List<DeptDict> findByHospitalIdAndQuer(String hospitalId, String q) {
        String hql = "from DeptDict as dept where dept.hospitalDict.id='" + hospitalId + "' and " +
                "dept.inputCode like '" + q + "%'";
        Query query = entityManager.createQuery(hql);
        List resultList = query.getResultList();
        return resultList;
    }

    /**
     * 返回没有对照核算单元的科室
     *
     * @param hospitalId
     * @return
     */
    public List<DeptDict> findByHospitalIdWithRecking(String hospitalId) {
        String hql = "from DeptDict as dept where dept.hospitalDict.id = '" + hospitalId + "' and dept.id not in (select distinct d.deptDictId from AcctDeptVsDeptDict as d )";
        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    /**
     * 查询出已经和核算科室对照的科室
     *
     * @param hospitalId
     * @return
     */
    public List<DeptDict> findByHospitalIdWithRecked(String hospitalId) {
        String hql = "from DeptDict as dept where dept.hospitalDict.id = '" + hospitalId + "' and dept.id not in (select distinct d.deptDictId from AcctDeptVsDeptDict as d )";
        Query query = entityManager.createQuery(hql) ;
        return query.getResultList() ;
    }


    /**
     * 查询出某一个核算单元对照的科室
     * @param hospitalId
     * @param acctDeptId
     * @return
     */
    public List<DeptDict> findByHospitalIdAndAcctDeptId(String hospitalId, String acctDeptId) {

        String hql = "select dept from DeptDict as dept ,AcctDeptVsDeptDict vs where dept.id=vs.deptDictId and " +
                "vs.acctDeptId='"+acctDeptId+"' and dept.hospitalDict.id='"+hospitalId+"'"  ;

        Query query = entityManager.createQuery(hql);
        return query.getResultList();
    }

    /**
     *
     * @param deptId
     * @return
     */
    public DeptDict findById(String deptId) {
        if(deptId=="" || deptId==null){
            return null;
        }
        DeptDict d=(DeptDict)entityManager.createQuery("select d from DeptDict as d where d.id="+deptId+"").getSingleResult();
        if(d==null){
            return null;
        }
        return d;
     }
}
