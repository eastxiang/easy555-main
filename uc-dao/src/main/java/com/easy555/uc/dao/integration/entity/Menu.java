/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.integration.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

import com.easy555.common.entity.BaseEntity;
import com.easy555.common.plugin.entity.Treeable;
import com.easy555.common.repository.support.annotation.EnableQueryCache;

/**
 * 菜单
 * 
 * create date: 2015年10月4日 下午3:33:21
 * 
 * @author xiangdong
 */
@Entity
@Table(name = "sys_menu")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends BaseEntity<Long>implements Treeable<Long> {

	/**
	 * 标题
	 */
	private String name;

	/**
	 * 菜单类型
	 */
	private Boolean menuType;
	/**
	 * 父路径
	 */
	@Column(name = "parent_id")
	private Long parentId;

	@Column(name = "parent_ids")
	private String parentIds;

	/**
	 * 权重
	 */
	private Integer weight;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 是否显示
	 */
	private Boolean status = Boolean.FALSE;

	/**
	 * 是否有叶子节点
	 */
	@Formula(value = "(select count(1) from sys_menu m where m.parent_id = id)")
	private boolean hasChildren;

	public Menu() {
	}

	public Menu(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getMenuType() {
		return menuType;
	}

	public void setMenuType(Boolean menuType) {
		this.menuType = menuType;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Override
	public String makeSelfAsNewParentIds() {
		return getParentIds() + getId() + getSeparator();
	}

	@Override
	public String getSeparator() {
		return "/";
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getIcon() {
		if (!StringUtils.isEmpty(icon)) {
			return icon;
		}
		if (isRoot()) {
			return getRootDefaultIcon();
		}
		if (isLeaf()) {
			return getLeafDefaultIcon();
		}
		return getBranchDefaultIcon();
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public boolean isRoot() {
		if (getParentId() != null && getParentId() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isLeaf() {
		if (isRoot()) {
			return false;
		}
		if (isHasChildren()) {
			return false;
		}

		return true;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

	/**
	 * 根节点默认图标 如果没有默认 空即可
	 *
	 * @return
	 */
	@Override
	public String getRootDefaultIcon() {
		return "ztree_root_open";
	}

	/**
	 * 树枝节点默认图标 如果没有默认 空即可
	 *
	 * @return
	 */
	@Override
	public String getBranchDefaultIcon() {
		return "ztree_branch";
	}

	/**
	 * 树叶节点默认图标 如果没有默认 空即可
	 *
	 * @return
	 */
	@Override
	public String getLeafDefaultIcon() {
		return "ztree_leaf";
	}

}
