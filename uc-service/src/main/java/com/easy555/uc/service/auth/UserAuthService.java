/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.service.auth;

import java.util.Set;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easy555.uc.dao.permission.entity.Role;
import com.easy555.uc.dao.user.entity.User;
import com.easy555.uc.service.permission.RoleService;
import com.easy555.uc.service.resource.ResourceService;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

/**
 * 分组、组织机构、用户、新增、修改、删除时evict缓存
 * <p/>
 * 获取用户授权的角色及组装好的权限
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-5-1 下午2:38
 * <p>
 * Version: 1.0
 */
@Service
public class UserAuthService {

	@Autowired
	private AuthService authService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ResourceService resourceService;


	public Set<Role> findRoles(User user) {

		if (user == null) {
			return Sets.newHashSet();
		}

		Long userId = user.getId();

		Set<Long[]> organizationJobIds = Sets.newHashSet();
		Set<Long> organizationIds = Sets.newHashSet();
		Set<Long> jobIds = Sets.newHashSet();

		

		//Set<Role> roles = roleService.findAvailableRoles(roleIds);

		//return roles;
		return null;

	}

	public Set<String> findStringRoles(User user) {
		Set<Role> roles = ((UserAuthService) AopContext.currentProxy()).findRoles(user);
		return Sets.newHashSet(Collections2.transform(roles, new Function<Role, String>() {
			@Override
			public String apply(Role input) {
				return input.getRole();
			}
		}));
	}

	/**
	 * 根据角色获取 权限字符串 如sys:admin
	 *
	 * @param user
	 * @return
	 */
	public Set<String> findStringPermissions(User user) {
		Set<String> permissions = Sets.newHashSet();

		Set<Role> roles = ((UserAuthService) AopContext.currentProxy()).findRoles(user);
//		for (Role role : roles) {
//			for (RoleResourcePermission rrp : role.getResourcePermissions()) {
//				Resource resource = resourceService.findOne(rrp.getResourceId());
//
//				String actualResourceIdentity = resourceService.findActualResourceIdentity(resource);
//
//				// 不可用 即没查到 或者标识字符串不存在
//				if (resource == null || StringUtils.isEmpty(actualResourceIdentity)
//						|| Boolean.FALSE.equals(resource.getVisible())) {
//					continue;
//				}
//
//				for (Long permissionId : rrp.getPermissionIds()) {
//					Permission permission = permissionService.findOne(permissionId);
//
//					// 不可用
//					if (permission == null || Boolean.FALSE.equals(permission.getVisible())) {
//						continue;
//					}
//					permissions.add(actualResourceIdentity + ":" + permission.getPermission());
//
//				}
//			}
//
//		}

		return permissions;
	}

}
