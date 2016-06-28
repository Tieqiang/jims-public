package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.AnswerResult;
import com.jims.wx.entity.SourceImage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by zhu on 2016/3/16.
 */
public class SourceImageFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public SourceImageFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public SourceImage save(SourceImage sourceImage) {
        return merge(sourceImage);
    }

    /**
     * 根据mediaId 查找图片的lcoalUrl
     * @param thumbMediaId
     * @return
     */
    public String findImageLocalUrlByMediaId(String thumbMediaId) {
        String sql="select imageLocalUrl from SourceImage where mediaId='"+thumbMediaId+"'";
        List<String> list=entityManager.createQuery(sql).getResultList();
        if(list!=null&&!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     *
     * @param ids
     * @return
     */
    public List<SourceImage> findById(String ids) {
        return entityManager.createQuery("from SourceImage where id='"+ids+"'").getResultList();
    }

    /**
     * 删除图片
     * @param sourceImage
     */
    @Transactional
    public void delete(SourceImage sourceImage) {
        super.remove(sourceImage);
    }
}
