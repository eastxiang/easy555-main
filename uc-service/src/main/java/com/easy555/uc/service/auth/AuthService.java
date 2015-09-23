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
import com.easy555.uc.dao.auth.entity.Auth;
import com.easy555.uc.dao.auth.repository.AuthRepository;
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
public class AuthService extends BaseService<Auth, Long> {

	@Autowired
	private UserService userService;

	private AuthRepository getAuthRepository() {
		return (AuthRepository) baseRepository;
	}

	public void addUserAuth(Long[] userIds, Auth m) {

		if (ArrayUtils.isEmpty(userIds)) {
			return;
		}

		for (Long userId : userIds) {

			User user = userService.findOne(userId);
			if (user == null) {
				continue;
			}

			// Auth auth = getAuthRepository().findByUserId(userId);
			// if (auth != null) {
			// auth.addRoleIds(m.getRoleIds());
			// continue;
			// }
			// auth = new Auth();
			// auth.setUserId(userId);
			// auth.setType(m.getType());
			// auth.setRoleIds(m.getRoleIds());
			// save(auth);
		}
	}

}
