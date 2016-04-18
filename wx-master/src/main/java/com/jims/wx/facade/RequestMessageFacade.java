package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.RequestMessage;
import com.jims.wx.entity.ResponseMessage;
import sun.java2d.pipe.hw.AccelDeviceEventNotifier;
import weixin.popular.bean.message.EventMessage;

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

    @Transactional
    public void saveMsg(EventMessage eventMessage) {

        RequestMessage requestMessage = new RequestMessage() ;
        requestMessage.setContent(eventMessage.getContent());
        requestMessage.setCreateTime(eventMessage.getCreateTime());
        requestMessage.setDescription(eventMessage.getDescription());
        requestMessage.setMsgType(eventMessage.getMsgType());
        requestMessage.setMsgId(eventMessage.getMsgId());
        requestMessage.setEvent(eventMessage.getEvent());
        requestMessage.setEventKey(eventMessage.getEventKey());
        requestMessage.setFormat(eventMessage.getFormat());
        requestMessage.setFromUserName(eventMessage.getFromUserName());
        requestMessage.setToUserName(eventMessage.getToUserName());
        requestMessage.setLabel(eventMessage.getLabel());
        requestMessage.setLatitude(eventMessage.getLatitude());
        requestMessage.setLocationX(eventMessage.getLocation_X());
        requestMessage.setLocationY(eventMessage.getLocation_Y());
        requestMessage.setLongitude(eventMessage.getLongitude());
        requestMessage.setMediaId(eventMessage.getMediaId());
        requestMessage.setPicUrl(eventMessage.getPicUrl());
        requestMessage.setPrecision(eventMessage.getPrecision());
        requestMessage.setResponseStatus(eventMessage.getStatus());
        requestMessage.setScale(eventMessage.getScale());
        requestMessage.setTicket(eventMessage.getTicket());
        requestMessage.setTitle(eventMessage.getTitle());
        requestMessage.setUrl(eventMessage.getUrl());

        merge(requestMessage) ;

    }
}
