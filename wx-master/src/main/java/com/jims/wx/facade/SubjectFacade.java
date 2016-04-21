package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.Subject;
import com.jims.wx.entity.SubjectOptions;
import com.jims.wx.vo.BeanChangeVo;
import com.jims.wx.vo.SubjectOptionsVo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by wei on 2016/3/16.
 */
public class SubjectFacade extends BaseFacade {

    /**
     * 名称模糊查询
     * @param name
     * @return
     */
    public List<Subject> findAll(String name) {
        String hql = "from  Subject sub where 1=1";
        if (null != name && !name.trim().equals("")) {
            hql += " and sub.questionContent like '%" + name.trim() + "%'";
        }
        return entityManager.createQuery(hql).getResultList();
    }

    /**
     * 保存增和修改
     *
     * @param sub
     */
    @Transactional
    public Subject save(Subject sub) {
        //对象有ID修改,否则新增
        if(sub.getId() != null && !sub.getId().trim().equals("")){
            //首先删除此题目多有的选项
            Subject old = get(Subject.class, sub.getId());
            Set<SubjectOptions> deleteData = old.getSubjectOptionses();
            if(deleteData != null && deleteData.size() > 0){
                List<String> ids = new ArrayList<>();
                for (SubjectOptions obj : deleteData) {
                    ids.add(obj.getId());
                }
                super.removeByStringIds(SubjectOptions.class, ids);
            }

        }

        Subject merge = merge(sub);

        return merge;
    }
    /**
     * 通过问题ID获取问题及其选项
     * @param id
     * @return
     */
    public Subject getById(String id){
        //通过主键获取问题
        Subject obj = get(Subject.class, id);

        //把问题选项转换成自定义VO的集合
        List<SubjectOptionsVo> opt = new ArrayList<SubjectOptionsVo>();
        Set<SubjectOptions> options = obj.getSubjectOptionses();
        if(options != null && options.size() > 0){
            SubjectOptionsVo vo;
            Iterator ite = options.iterator();
            while(ite.hasNext()){
                SubjectOptions temp = (SubjectOptions)ite.next();
                vo = new SubjectOptionsVo();
                vo.setId(temp.getId());
                vo.setOptContent(temp.getOptContent());
                vo.setOptStatus(temp.getOptStatus());
                vo.setImage(temp.getImage());
                vo.setSubjectId(temp.getSubject().getId());
                opt.add(vo);
            }
            obj.setOptions(opt);
        }
        return obj;
    }

    /**
     * 删除
     * @param beanChangeVo
     * @return
     */
    @Transactional
    public List<Subject> delete(BeanChangeVo<Subject> beanChangeVo) {

        List<Subject> newUpdateDict = new ArrayList<>();

        List<Subject> deleted = beanChangeVo.getDeleted();


        List<String> ids = new ArrayList<>();

        for (Subject  dict : deleted) {
            //根据ID取得问题的选项，并删除所有选项
            Subject old = get(Subject.class,dict.getId());
            Set<SubjectOptions> options = old.getSubjectOptionses();
            List<String> optIds = new ArrayList<>();

            for (SubjectOptions  opt : options) {
                optIds.add(opt.getId());
            }
            this.removeByStringIds(SubjectOptions .class, optIds);
            //删除题目
            ids.add(dict.getId());
            newUpdateDict.add(dict);

        }
        this.removeByStringIds(Subject .class, ids);
        return newUpdateDict;
    }

    /**
     *
     * @param subjectId
     * @return
     */
    public Subject findById(String subjectId) {
        return  (Subject)entityManager.createQuery("from Subject where id='"+subjectId+"'").getSingleResult();
    }
}


