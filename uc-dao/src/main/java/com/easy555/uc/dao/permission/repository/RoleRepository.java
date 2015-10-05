package com.easy555.uc.dao.permission.repository;

import com.easy555.common.repository.BaseRepository;
import com.easy555.uc.dao.permission.entity.Role;
import com.easy555.uc.dao.permission.entity.RoleResourcePermission;

import java.util.Set;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 角色DAO
 * 
 * create date: 2015年10月5日 上午9:01:16
 * 
 * @author xiangdong
 */
public interface RoleRepository extends BaseRepository<Role, Long> {

	/**
	 * 获取角色的资源权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @return 角色资源权限列表
	 */
	@Query("from RoleResourcePermission o where o.roleId = ?1")
	public Set<RoleResourcePermission> findRoleResourcePermission(Long roleId);

	/**
	 * 删除角色权限
	 * 
	 * @param roleId
	 *            角色ID
	 * @param resourceId
	 *            资源ID
	 */
	@Modifying
	@Query("delete RoleResourcePermission o where o.roleId = ?1 and o.resourceId = ?2 ")
	public void deleteRoleResourcePermission(Long roleId, Long resourceId);
}
