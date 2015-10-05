package com.easy555.uc.service.permission;

import java.util.EnumSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easy555.common.entity.Permission;
import com.easy555.common.service.BaseService;
import com.easy555.uc.dao.permission.entity.Role;
import com.easy555.uc.dao.permission.entity.RoleResourcePermission;
import com.easy555.uc.dao.permission.repository.RoleRepository;
import com.easy555.uc.dao.permission.repository.RoleResourcePermissionRepository;
import com.google.common.collect.Sets;

/**
 * 角色服务<br>
 * 1. 增删改查角色Role <br>
 * 2. 增加、删除角色资源权限<br>
 * <p>
 * create date: 2015年10月5日 下午2:09:07
 * 
 * @author xiangdong
 */
@Service
public class RoleService extends BaseService<Role, Long> {

	@Autowired
	RoleResourcePermissionRepository roleResourcePermissionRepository;

	public RoleRepository getRoleRepository() {
		return (RoleRepository) baseRepository;
	}

	/**
	 * 获取角色资源权限
	 * 
	 * @param role
	 * @return 角色资源权限列表
	 */
	public Set<RoleResourcePermission> findRoleResourcePermission(Role role) {

		return findRoleResourcePermission(role.getId());
	}

	public Set<RoleResourcePermission> findRoleResourcePermission(Long roleId) {

		return getRoleRepository().findRoleResourcePermission(roleId);
	}

	/**
	 * 增加角色的资源权限
	 * 
	 * @param roleId
	 * @param resourceId
	 * @param permissions
	 */
	public void addRoleResourcePermission(Long roleId, Long resourceId, EnumSet<Permission> permissions) {
		addRoleResourcePermission(new RoleResourcePermission(roleId, resourceId, permissions));
	}

	/**
	 * 增加角色的资源权限
	 * 
	 * @param roleResourcePermission
	 */
	public void addRoleResourcePermission(RoleResourcePermission roleResourcePermission) {
		roleResourcePermissionRepository.save(roleResourcePermission);
	}

	/**
	 * 删除角色资源权限
	 * 
	 * @param roleId
	 * @param resourceId
	 */
	public void deleteRoleResourcePermission(Long roleId, Long resourceId) {
		getRoleRepository().deleteRoleResourcePermission(roleId, resourceId);
	}

	/**
	 * 获取可用的角色列表
	 *
	 * @param roleIds
	 * @return
	 */
	public Set<Role> findAvailableRoles(Set<Long> roleIds) {

		Set<Role> roles = Sets.newHashSet();

		for (Role role : findAll()) {
			if (Boolean.TRUE.equals(role.getStatus()) && roleIds.contains(role.getId())) {
				roles.add(role);
			}
		}
		return roles;
	}

}
