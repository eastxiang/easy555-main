/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.permission.repository;

import com.easy555.common.repository.BaseRepository;
import com.easy555.uc.dao.permission.entity.UserPermission;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-2-4 下午3:00
 * <p>Version: 1.0
 */
public interface UserPermissionRepository extends BaseRepository<UserPermission, Long> {

    UserPermission findByUserId(Long userId);
}
