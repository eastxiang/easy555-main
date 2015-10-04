/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.service.auth;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easy555.common.service.BaseService;
import com.easy555.uc.dao.permission.entity.UserPermission;
import com.easy555.uc.dao.permission.repository.UserPermissionRepository;
import com.easy555.uc.dao.user.entity.User;
import com.easy555.uc.service.user.UserService;

/**
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-2-4 下午3:01
 * <p>
 * Version: 1.0
 */
@Service
public class AuthService extends BaseService<UserPermission, Long> {

	@Autowired
	private UserService userService;

	

}
