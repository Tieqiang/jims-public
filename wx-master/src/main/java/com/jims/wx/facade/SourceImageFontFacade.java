package com.jims.wx.facade;

import com.google.inject.persist.Transactional;
import com.jims.wx.BaseFacade;
import com.jims.wx.entity.SourceImageFont;
import com.jims.wx.entity.SourceVideo;
import weixin.popular.bean.message.Article;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhu on 2016/3/16.
 */
public class SourceImageFontFacade extends BaseFacade {
    private EntityManager entityManager;

    @Inject
    public SourceImageFontFacade(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Transactional
    public SourceImageFont save(SourceImageFont sourceImageFont) {
        return super.merge(sourceImageFont);
    }

    /**
     * 根据sourceImageFont 的id 查找
     * 将其封装成为Article
     * @param ids
     * @return
     */
    public List<Article> findByIds(String ids) {
         List<Article> articles=new ArrayList<Article>();
         // todo 根据sourceImageFont 的id 查找将其封装成为Article
         List<SourceImageFont> list=findSourceImageFontByIds(ids);
         if(list!=null&&!list.isEmpty()){
             for(SourceImageFont sourceImageFont:list){
                 Article article=new Article();
                 article.setShow_cover_pic("1");
                 article.setDigest(sourceImageFont.getDigest());
                 article.setContent(new String(sourceImageFont.getContent()));
                 article.setAuthor(sourceImageFont.getAuthor());
                 article.setThumb_media_id(sourceImageFont.getMediaId());
                 article.setTitle(sourceImageFont.getTitle());
                 article.setContent_source_url(sourceImageFont.getContentSourceUrl());
                 articles.add(article);
             }
         }
         return articles;
    }

    /**
     *
     * @param ids
     * @return
     */
    public List<SourceImageFont> findSourceImageFontByIds(String ids) {
        List<SourceImageFont> list=new ArrayList<SourceImageFont>();
        if(ids!=null&&!"".equals(ids)){
            String[] idsArr=ids.split(",");
            for(String id:idsArr){
                String sql="from SourceImageFont where id in ('"+id+"')";
                SourceImageFont sourceImageFont= (SourceImageFont) entityManager.createQuery(sql).getSingleResult();
                list.add(sourceImageFont);
            }

        }
         return list;
    }
}
