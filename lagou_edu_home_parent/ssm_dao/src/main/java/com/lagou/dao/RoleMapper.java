package com.lagou.dao;

import com.lagou.domain.*;

import java.util.List;

public interface RoleMapper {
    /*
        查询所有角色&条件进行查询
     */
    public List<Role> findAllRole(Role role);

    /*
        根据角色ID查询该角色关联的菜单信息ID
     */
    public List<Integer> findMenuByRoleId(Integer roleId);
    /*
        根据roleId清空中间表的关联关系
     */
    public void deleteRoleContextMenu(Integer rid);
    /*
        为角色分配菜单信息
     */
    public void roleContextMenu(Role_menu_relation role_menu_relation);
    /*
        删除角色
     */
    public void deleteRole(Integer roleId);
    /*
        根据角色ID获取资源信息
     */
    public List<Resource> findResourceListByRoleId(Integer roleId);
    /*
        根据资源分类Id查询对应的资源分类信息
     */
    public ResourceCategory findResourceCategoryByResourceId(Integer resourceCategoryId);
    /*
        根据角色ID删除角色与资源的关联关系
     */
    public void deleteRoleResourceRelationByRoleId(Integer roleId);
    /*
        插入最新的关联关系
     */
    public void saveRoleResourceRelation(RoleResourceRelation roleResourceRelation);
    /*
        添加角色
     */
    public void saveRole(Role role);
    /*
        修改角色
     */
    public void updateRole(Role role);
}
