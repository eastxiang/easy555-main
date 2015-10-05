/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.service.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.easy555.uc.dao.organization.entity.User;
import com.easy555.uc.service.user.PasswordService;
import com.easy555.uc.service.user.UserService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-4-5 下午3:24
 * <p>
 * Version: 1.0
 */
public abstract class BaseUserTest extends BaseTest {

	@Autowired
	protected UserService userService;

	@Autowired
	protected PasswordService passwordService;

	protected int maxtRetryCount = 10;

	protected String username = "__xiang__dong";
	protected String email = "xiang.d@vip.163.com";
	protected String mobilePhoneNumber = "18612345678";
	protected String password = "123456";

	@Before
	public void setUp() {
		userService.setPasswordService(passwordService);
		passwordService.setMaxRetryCount(maxtRetryCount);

		User user = userService.findByUsername(username);
		if (user != null) {
			userService.delete(user);// 因为用户是逻辑删除 此处的目的主要是清 缓存
			delete(user); // 所以还需要物理删除
		}
		
		user = userService.findByEmail(email);
		if (user != null) {
			userService.delete(user);
			delete(user);
		}
		
		user = userService.findByMobilePhoneNumber(mobilePhoneNumber);
		if (user != null) {
			userService.delete(user);
			delete(user);
		}
		
		clear();
	}

	@After
	public void tearDown() {
		passwordService.clearLoginRecordCache(username);
		passwordService.clearLoginRecordCache(email);
		passwordService.clearLoginRecordCache(mobilePhoneNumber);
	}

	protected User createUser(String username, String email, String mobilePhoneNumber, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setMobilePhoneNumber(mobilePhoneNumber);
		user.setPassword(password);
		userService.saveAndFlush(user);
		return user;
	}

	protected User createDefaultUser() {
		return createUser(username, email, mobilePhoneNumber, password);
	}

}
