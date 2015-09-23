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
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-5 下午2:04
 * <p>Version: 1.0
 */

@TypeDef(
        name = "SetPermissionUserType",
        typeClass = IntegerValuedEnumType.class,
        parameters = {
                @Parameter(name = "enum", value = "com.easy555.common.entity.Permission"),
                @Parameter(name = "defaultValue", value = "1")
        }
)
@Entity
@Table(name = "sys_role_resource_permission")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleResourcePermission extends BaseEntity<Long> {
	
	/**
     * 角色id
     */
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    private Role role;

    /**
     * 资源id
     */
    @Column(name = "resource_id")
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

	public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    
    public EnumSet<Permission> getPermission_val() {
		return permission_val;
	}

	public void setPermission_val(EnumSet<Permission>  permission_val) {
		this.permission_val = permission_val;
	}

    @Override
    public String toString() {
        return "RoleResourcePermission{id=" + this.getId() +
                ",roleId=" + (role != null ? role.getId() : "null") +
                ", resourceId=" + resourceId +
                ", permission_val=" + permission_val +
                '}';
    }
}
