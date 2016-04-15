package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.ResponseMessage;
import com.jims.wx.vo.ReceiveMessage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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


    /**
     * 添加，修改msg
     * @param msg
     * @return
     */
    @Transactional
    public ResponseMessage save(ResponseMessage msg){
        msg = super.merge(msg);
        return msg;
    }

    /**
     * 删除msg
     * @param id
     * @return
     */
    @Transactional
    public ResponseMessage deleteById(String id){
        ResponseMessage msg = get(ResponseMessage.class, id);
        this.remove(id);
        return msg;
    }
}
