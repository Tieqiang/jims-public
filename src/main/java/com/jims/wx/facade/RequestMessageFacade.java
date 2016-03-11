package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.RequestMessage;
import com.jims.wx.entity.ResponseMessage;

import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created by wangjing on 2016/3/8.
 */
public class RequestMessageFacade extends BaseFacade{
    private EntityManager entityManager;

    @Inject
    public RequestMessageFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    /**
     * 添加，修改msg
     * @param msg
     * @return
     */
    @Transactional
    public RequestMessage save(RequestMessage msg){
        msg = super.merge(msg);
        return msg;
    }

    /**
     * 删除msg
     * @param id
     * @return
     */
    @Transactional
    public RequestMessage deleteById(String id){
        RequestMessage msg = get(RequestMessage.class, id);
        this.remove(id);
        return msg;
    }
}
