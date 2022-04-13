package com.lagou.service;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVO;
import java.util.List;

public interface PromotionAdService {

    /*
        分页查询广告信息
     */
    public PageInfo findAllPromotionAdByPage(PromotionAdVO promotionAdVO);

    /*
        广告动态上下线
     */
    public void updatePromotionAdStatus(int id,int status);
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
