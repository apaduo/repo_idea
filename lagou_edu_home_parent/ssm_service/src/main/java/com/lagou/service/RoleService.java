package com.lagou.service;

import com.lagou.domain.*;

import java.util.List;

public interface RoleService {
    /*
        查询所有角色&条件进行查询
     */
    public List<Role> findAllRole(Role role);

    /*
        根据角色ID查询该角色关联的菜单信息ID
     */
    public List<Integer> findMenuByRoleId(Integer roleId);
    /*
        为角色分配菜单
     */
    public void roleContextMenu(RoleMenuVO roleMenuVO);
    /*
        删除角色
     */
    public void deleteRole(Integer roleId);
    /*
        获取当前角色拥有的资源信息
     */
    public List<ResourceCategory> findResourceListByRoleId(Integer roleId);
    /*
        为角色分配菜单
     */
    public void roleContextResource(RoleResourceVO roleResourceVO);
}
