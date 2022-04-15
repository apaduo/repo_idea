package com.lagou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lagou.dao.UserMapper;
import com.lagou.domain.*;
import com.lagou.service.UserService;
import com.lagou.utils.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageInfo findAllUserByPage(UserVO userVO) {
        PageHelper.startPage(userVO.getCurrentPage(),userVO.getPageSize());
        List<User> users = userMapper.findAllUserByPage(userVO);
        return new PageInfo<>(users);
    }

    @Override
    public void updateUserStatus(Integer id, String status) {
        User user = new User();
        user.setId(id);
        user.setUpdateTime(new Date());
        user.setStatus(status);

        userMapper.updateUserStatus(user);
    }

    @Override
    public User login(User user) throws Exception {
        User login = userMapper.login(user);
        if (login != null && Md5.verify(user.getPassword(),"lagou",login.getPassword())){
            return login;
        }else{
            return null;
        }
    }

    @Override
    public List<Role> findUserRelationRoleById(Integer id) {
        return userMapper.findUserRelationRoleById(id);
    }

    @Override
    public void userContextRole(UserVO userVO) {
        //1.根据用户ID清空中间表关联关系
        userMapper.deleteUserContextRole(userVO.getUserId());
        //2.再重新建立关联关系
        Date date = new Date();
        for (Integer integer : userVO.getRoleIdList()) {
            //封装数据
            User_Role_relation user_role_relation = new User_Role_relation();
            user_role_relation.setUserId(userVO.getUserId());
            user_role_relation.setRoleId(integer);
            user_role_relation.setCreatedTime(date);
            user_role_relation.setUpdatedTime(date);
            user_role_relation.setCreatedBy("system");
            user_role_relation.setUpdatedby("system");

            userMapper.userContextRole(user_role_relation);
        }
    }
    /*
        获取用户权限信息
     */
    @Override
    public ResponseResult getUserPermissions(Integer userId) {
        ArrayList<Integer> roleIds = new ArrayList<>();
        //1.获取当前用户拥有的角色
        for (Role role : userMapper.findUserRelationRoleById(userId)) {
            //2.获取角色ID,保存到LIST集合中
            roleIds.add(role.getId());
        }
        //3.根据角色ID查询父菜单
        List<Menu> menuList = userMapper.findParentMenuByRoleId(roleIds);
        for (Menu menu : menuList) {
            //4.查询父菜单关联的子菜单
            menu.setSubMenuList(userMapper.findSubMenuByPid(menu.getId()));
        }
        //5.获取资源信息
        List<Resource> resourceList = userMapper.findResourceByRoleId(roleIds);

        //6.封装数据并返回
        HashMap<String, Object> map = new HashMap<>();

        map.put("menuList",menuList);
        map.put("resourceList",resourceList);

        return new ResponseResult(true,200,"获取用户权限信息成功",map);
    }
}
