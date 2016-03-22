package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;

import com.jims.wx.entity.Subject;
import com.jims.wx.entity.SubjectOptions;
import com.jims.wx.vo.SubjectOptionsVo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.*;

/**
 * Created by wei on 2016/3/16.
 */
public class SubjectFacade extends BaseFacade {

    private EntityManager entityManager;

    @Inject
    public SubjectFacade( EntityManager entityManager){
        this.entityManager=entityManager;
    }

    /**
     * 保存调查问卷题目及其选项（增加、修改）
     * @param sub
     */
    @Transactional
    public void save(Subject sub){
        //取得想要保存的选项
        List<SubjectOptionsVo> options = sub.getOptions();
        //首先删除此题目多有的选项
        sub = get(Subject.class, sub.getId());
        Set<SubjectOptions> deleteData = sub.getSubjectOptionses();
        if(deleteData != null && deleteData.size() > 0){
            List<String> ids = new ArrayList<>();
            for (SubjectOptions obj : deleteData) {
                ids.add(obj.getId());
            }
            super.removeByStringIds(SubjectOptions.class, ids);
        }

        //在保存页面里面所有的选项
        if(options != null && options.size() > 0){
            Set<SubjectOptions> subjectOptionses = new HashSet<SubjectOptions>();
            SubjectOptions opt;
            Subject s;
            Iterator ite = options.iterator();
            while(ite.hasNext()){
                SubjectOptionsVo temp = (SubjectOptionsVo)ite.next();
                opt = new SubjectOptions();
                opt.setId(temp.getId());
                opt.setOptStatus(temp.getOptStatus());
                opt.setOptContent(temp.getOptContent());
                s = new Subject();
                s.setId(sub.getId());
                opt.setSubject(s);
                subjectOptionses.add(opt);
            }
            sub.setSubjectOptionses(subjectOptionses);
        }
        merge(sub);
    }

    /**
     * 通过问题ID获取问题及其选项
     * @param id
     * @return
     */
    @Transactional
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
                vo.setSubjectId(temp.getSubject().getId());
                opt.add(vo);
            }
            obj.setOptions(opt);
        }

        return obj;
    }

    /**
     * 根据主键删除问题及其选项
     * @param ids
     */
    @Transactional
    public void delSubjects(String ids){
        if(ids != null){
            String[] idArray = ids.split(";");
            for(String id:idArray){
                Subject sub = get(Subject.class, id);
                //首先删除此题目多有的选项
                Set<SubjectOptions> deleteData = sub.getSubjectOptionses();
                List<String> optionIds = new ArrayList<>();
                for (SubjectOptions opt : deleteData) {
                    optionIds.add(opt.getId());
                }
                super.removeByStringIds(SubjectOptions.class, optionIds);
                //删除题干
                super.remove(sub);
            }
        }
    }
}


