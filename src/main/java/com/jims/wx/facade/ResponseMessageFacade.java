package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ResponseMessage;
import com.jims.wx.vo.ReceiveMessage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by wangjing on 2016/3/8.
 */
public class ResponseMessageFacade extends BaseFacade{
    private EntityManager entityManager;

    @Inject
    public ResponseMessageFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ResponseMessage> findAll(){

        return null;
    }

    @Transactional
    public ResponseMessage save(ResponseMessage msg){
        msg = super.merge(msg);
        return msg;
    }
}
