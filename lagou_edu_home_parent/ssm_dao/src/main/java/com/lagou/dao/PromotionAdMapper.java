package com.lagou.dao;

import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionAdMapper {

    /*
        分页查询广告信息
     */
    public List<PromotionAd> findAllPromotionAdByPage();

    /*
        广告动态上下线
     */
    public void updatePromotionAdStatus(PromotionAd promotionAd);
    /*
        添加广告
     */
    public void savePromotionAd(PromotionAd promotionAd);

    /*
        根据ID查询广告
     */
    public PromotionAd findPromotionAdById(Integer id);
    /*
        修改广告
     */
    public void updatePromotionAd(PromotionAd promotionAd);
}
