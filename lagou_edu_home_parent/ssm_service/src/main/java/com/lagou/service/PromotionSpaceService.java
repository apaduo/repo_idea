package com.lagou.service;

import com.lagou.domain.PromotionSpace;

import java.util.List;

public interface PromotionSpaceService {

    /*
        获取所有广告位
     */
    public List<PromotionSpace> findAllPromotionSpace();

    /*
        添加广告位
     */
    public void savePromotionSpace(PromotionSpace promotionSpace);
    /*
       根据ID查询广告位信息
    */
    public PromotionSpace findPromotionSpaceById(Integer id);
    /*
        修改广告位
     */
    public void updatePromotionSpace(PromotionSpace promotionSpace);
}
