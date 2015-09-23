package com.easy555.uc.dao.permission.entity;

import com.easy555.common.repository.hibernate.type.IntegerValuedEnum;

/**
 * 权限
 * 
 * xiangdong 2015.09.23
 */
public enum Permission implements IntegerValuedEnum {

	Read(1), Create(2), Delete(4);

	private final Integer code;

	Permission(final Integer code) {
		this.code = code;
	}

	@Override
	public int getCode() {
		return this.code;
	}
}
