package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.RequestMessage;
import com.jims.wx.entity.ResponseMessage;
import com.jims.wx.vo.MessageVo;
import sun.java2d.pipe.hw.AccelDeviceEventNotifier;
import weixin.popular.bean.message.EventMessage;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    /**
     * 根据发送时间查找内容
     * @param startTime
     * @param endTime
     * @return
     */
    public List<MessageVo> findByTime(String startTime, String endTime) {
        List<MessageVo> messageVos=new ArrayList<MessageVo>();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  sql="";
        Integer start=0;
        Integer end=0;
        try {
//            start=(Integer)((simpleDateFormat.parse(startTime).getTime())/1000);
            start=Integer.parseInt(simpleDateFormat.parse(startTime).getTime()/1000+"");
            end=Integer.parseInt(simpleDateFormat.parse(endTime).getTime()/1000+"");
            sql="from RequestMessage where createTime >= "+start+" and createTime <="+end+"";
            List<RequestMessage> list=entityManager.createQuery(sql).getResultList();
            if(list!=null&&!list.isEmpty()){
                for(RequestMessage requestMessage:list){
                    MessageVo messageVo=new MessageVo();
                    messageVo.setFromUserName(requestMessage.getFromUserName());
                    messageVo.setId(requestMessage.getId());
                    messageVo.setMsgType(requestMessage.getMsgType());
                    messageVo.setCreateTime(simpleDateFormat.format(new Date(requestMessage.getCreateTime())));
                    messageVo.setToUserName(requestMessage.getToUserName());
                    if(requestMessage.getMsgType().equals("text")){//文本
                        messageVo.setContent(requestMessage.getContent());
                    }else if(requestMessage.getMsgType().equals("image")){//图片
                        messageVo.setContent("<img src="+requestMessage.getPicUrl()+" style=width:100%;/>");
                    }else if(requestMessage.getMsgType().equals("link")){//链接
                        StringBuffer sb=new StringBuffer();
                        sb.append("<div>");
                            sb.append("<div>"+"标题"+requestMessage.getTitle()+"</div>");
                            sb.append("<div>"+"描述"+requestMessage.getDescription()+"</div>");
                            sb.append("<div>"+"链接"+requestMessage.getUrl()+"</div>");
                        sb.append("</div>");
                        messageVo.setContent(sb.toString());
                        messageVos.add(messageVo);
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return messageVos;
    }
}
