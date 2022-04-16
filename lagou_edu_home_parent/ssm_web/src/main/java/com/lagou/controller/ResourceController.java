package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Resource;
import com.lagou.domain.ResourceVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/findAllResource")
    public ResponseResult findAllResource(@RequestBody ResourceVO resourceVO){

        PageInfo<Resource> pageInfo = resourceService.findAllResourceByPage(resourceVO);

        return new ResponseResult(true,200,"资源信息分页多条件查询成功",pageInfo);
    }

    @RequestMapping("/saveOrUpdateResource")
    public ResponseResult saveOrUpdateResource(@RequestBody Resource resource){
        if (resource.getId() != null){
            resourceService.updateResource(resource);
            return new ResponseResult(true,200,"资源信息更新成功",null);
        }else{
            resourceService.saveResource(resource);
            return new ResponseResult(true,200,"资源信息添加成功",null);
        }
    }
}
