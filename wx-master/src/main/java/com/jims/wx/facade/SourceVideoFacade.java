package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.SourceImage;
import com.jims.wx.entity.SourceVideo;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by zhu on 2016/3/16.
 */
public class SourceVideoFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public SourceVideoFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public SourceVideo save(SourceVideo sourceVideo) {
        return super.merge(sourceVideo);
    }
}
