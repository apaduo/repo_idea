package com.lagou.service.impl;

import com.lagou.dao.RoleMapper;
import com.lagou.domain.*;
import com.lagou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAllRole(Role role) {
        return roleMapper.findAllRole(role);
    }

    @Override
    public List<Integer> findMenuByRoleId(Integer roleId) {
        return roleMapper.findMenuByRoleId(roleId);
    }

    @Override
    public void roleContextMenu(RoleMenuVO roleMenuVO) {
        //1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleMenuVO.getRoleId());
        //2.为角色分配菜单
        for (Integer mid : roleMenuVO.getMenuIdList()) {
            Role_menu_relation role_menu_relation = new Role_menu_relation();
            role_menu_relation.setRoleId(roleMenuVO.getRoleId());
            role_menu_relation.setMenuId(mid);
            Date date = new Date();
            role_menu_relation.setCreatedTime(date);
            role_menu_relation.setUpdatedTime(date);
            role_menu_relation.setCreatedBy("system");
            role_menu_relation.setUpdatedBy("system");
            roleMapper.roleContextMenu(role_menu_relation);
        }
    }

    @Override
    public void deleteRole(Integer roleId) {
        //1.清空中间表的关联关系
        roleMapper.deleteRoleContextMenu(roleId);

        //2.删除角色
        roleMapper.deleteRole(roleId);
    }

    @Override
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId) {
        List<ResourceCategory> resourceCategories = new ArrayList<ResourceCategory>();
        List<ResourceCategory> resourceCategoryList = new ArrayList<ResourceCategory>();
        //查询角色对应的资源信息
        List<Resource> resourceListByRoleId = roleMapper.findResourceListByRoleId(roleId);
        for (Resource resource : resourceListByRoleId) {
            ResourceCategory resourceCategory = roleMapper.findResourceCategoryByResourceId(resource.getCategoryId());
            resourceCategories.add(resourceCategory);
        }
        //去重
        resourceCategoryList = resourceCategories.stream().distinct().collect(Collectors.toList());
        for (ResourceCategory resourceCategory : resourceCategoryList) {
            ArrayList<Resource> resourceList = new ArrayList<Resource>();
            for (Resource resource : resourceListByRoleId) {
                if (resource.getCategoryId().equals(resourceCategory.getId())){
                    resourceList.add(resource);
                }
            }
            resourceCategory.setResourceList(resourceList);
        }
        return resourceCategoryList;
    }

    @Override
    public void roleContextResource(RoleResourceVO roleResourceVO) {
        roleMapper.deleteRoleResourceRelationByRoleId(roleResourceVO.getRoleId());
        Date date = new Date();
        for (Integer integer : roleResourceVO.getResourceIdList()) {
            RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
            roleResourceRelation.setRoleId(roleResourceVO.getRoleId());
            roleResourceRelation.setResourceId(integer);
            roleResourceRelation.setUpdatedTime(date);
            roleResourceRelation.setCreatedTime(date);
            roleResourceRelation.setCreatedBy("system");
            roleResourceRelation.setUpdatedBy("system");
            roleMapper.saveRoleResourceRelation(roleResourceRelation);
        }
    }
}
