/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.resource.entity;

import java.util.EnumSet;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.easy555.common.entity.BaseEntity;
import com.easy555.common.entity.Permission;
import com.easy555.common.repository.hibernate.type.IntegerValuedEnumType;
import com.easy555.common.repository.support.annotation.EnableQueryCache;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-2-4 上午9:38
 * <p>
 * Version: 1.0
 */

@TypeDef(name = "SetPermissionUserType", typeClass = IntegerValuedEnumType.class, parameters = {
		@Parameter(name = "enum", value = "com.easy555.common.entity.Permission"),
		@Parameter(name = "defaultValue", value = "1") })
@Entity
@Table(name = "sys_resource")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends BaseEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8966817382538485958L;

	/**
	 * 标题
	 */
	private String name;

	/**
	 * 资源标识符 用于权限匹配的 如sys:resource
	 */
	private String identity;

	/**
	 * 点击后前往的地址 菜单才有
	 */
	private String url;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 是否显示
	 */
	private Boolean status = Boolean.FALSE;

	/**
	 * 权限值
	 */
	@Type(type = "SetPermissionUserType")
	private EnumSet<Permission> permission_val;

	public String getName() {

		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public EnumSet<Permission> getPermission_val() {
		return permission_val;
	}

	public void setPermission_val(EnumSet<Permission> permission_val) {
		this.permission_val = permission_val;
	}
}
