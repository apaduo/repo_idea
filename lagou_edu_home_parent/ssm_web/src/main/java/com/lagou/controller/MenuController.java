package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.Menu;
import com.lagou.domain.ResponseResult;
import com.lagou.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;
    /*
        查询所有菜单
     */
    @RequestMapping("/findAllMenu")
    public ResponseResult findAllMenu(Integer currentPage,Integer pageSize){
        PageInfo pageInfo = menuService.findAllMenu(currentPage,pageSize);
        return new ResponseResult(true,200,"查询所有菜单成功",pageInfo);
    }

    /*
        回显菜单信息
     */
    @RequestMapping("/findMenuInfoById")
    public ResponseResult findMenuInfoById(Integer id){
        //根据id的值判断当前是更新还是添加操作 判断id的值是否为-1
        HashMap<String, Object> map = new HashMap<>();
        //添加 回显信息中不需要查询menu信息
        List<Menu> subMenuListByPid = menuService.findSubMenuListByPid(-1);
        if (id == -1){
            //封装数据
            map.put("menuInfo",null);
            map.put("parentMenuList",subMenuListByPid);

            return new ResponseResult(true,200,"添加回显成功",map);
        }else{
            //修改操作 回显所有menu信息
            Menu menu = menuService.findMenuById(id);
            //封装数据
            map.put("menuInfo",menu);
            map.put("parentMenuList",subMenuListByPid);
            return new ResponseResult(true,200,"更新回显成功",map);
        }

    }
    /*
        新增&更新菜单信息
     */
    @RequestMapping("/saveOrUpdateMenu")
    public ResponseResult saveOrUpdateMenu(@RequestBody Menu menu){
        if (menu.getId() != null){
            menuService.updateMenu(menu);
            return new ResponseResult(true,200,"更新菜单信息成功",null);
        }else{
            menuService.saveMenu(menu);
            return new ResponseResult(true,200,"新建菜单信息成功",null);
        }
    }
}
