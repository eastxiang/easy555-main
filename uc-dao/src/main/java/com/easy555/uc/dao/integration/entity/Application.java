/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.integration.entity;

import com.easy555.common.entity.BaseEntity;
import com.easy555.common.repository.support.annotation.EnableQueryCache;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统应用
 * 
 * create date: 2015年10月4日 下午3:33:21
 * 
 * @author xiangdong
 */
@Entity
@Table(name = "sys_application")
@EnableQueryCache
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application extends BaseEntity<Long> {
	/**
	 * 应用编号
	 */
	private String code;
	/**
	 * 应用名称
	 */
	private String name;

	/**
	 * 详细描述
	 */
	private String description;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
