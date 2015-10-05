/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.permission.entity;

import java.util.EnumSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.easy555.common.entity.BaseEntity;
import com.easy555.common.entity.Permission;
import com.easy555.common.repository.hibernate.type.IntegerValuedEnumType;
import com.easy555.common.repository.support.annotation.EnableQueryCache;

/**
 * 角色资源权限
 * 
 * create date: 2015年10月5日 下午2:35:51
 * 
 * @author xiangdong
 */

@TypeDef(name = "SetPermissionUserType", typeClass = IntegerValuedEnumType.class, parameters = {
		@Parameter(name = "enum", value = "com.easy555.common.entity.Permission"),
		@Parameter(name = "defaultValue", value = "1") })
@Entity
@Table(name = "sys_role_resource_permission")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleResourcePermission extends BaseEntity<Long> {

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 资源id
	 */
	private Long resourceId;

	/**
	 * 权限值
	 */
	@Type(type = "SetPermissionUserType")
	private EnumSet<Permission> permission_val;

	public RoleResourcePermission() {
	}

	public RoleResourcePermission(Long id) {
		setId(id);
	}

	public RoleResourcePermission(Long roleId, Long resourceId, EnumSet<Permission> permissions) {
		this.roleId = roleId;
		this.resourceId = resourceId;
		this.permission_val = permissions;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public EnumSet<Permission> getPermission_val() {
		return permission_val;
	}

	public void setPermission_val(EnumSet<Permission> permission_val) {
		this.permission_val = permission_val;
	}

	@Override
	public String toString() {
		return "RoleResourcePermission{id=" + this.getId() + ",roleId=" + roleId + ", resourceId=" + resourceId
				+ ", permission_val=" + permission_val + '}';
	}
}
