/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.junit.Assert;
import org.junit.Test;

import com.easy555.uc.dao.user.entity.User;
import com.easy555.uc.dao.user.entity.UserStatus;
import com.easy555.uc.web.test.BaseUserTest;



/**
 * UserRealm 测试类
 * @author xiangdong
 * create date: 2015年9月29日 上午9:43:41
 */
public class UserRealmTest extends BaseUserTest {

    @Test
    public void testLoginSuccess() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
        Assert.assertEquals(username, subject.getPrincipal());
    }

    @Test(expected = UnknownAccountException.class)
    public void testLoginFailWithUserNotExists() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username + "1", password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }

    @Test(expected = AuthenticationException.class)
    public void testLoginFailWithUserPasswordNotMatch() {
        createUser(username, password);

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password + "1");
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }


    @Test(expected = LockedAccountException.class)
    public void testLoginFailWithSysBlocked() {
        User user = createUser(username, password);
        userService.changeStatus(user, user, UserStatus.blocked, "sql");

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }

    @Test(expected = ExcessiveAttemptsException.class)
    public void testLoginFailWithRetryLimitExceed() {
        createUser(username, password);
        for (int i = 0; i < maxtRetryCount; i++) {
            try {
                UsernamePasswordToken upToken = new UsernamePasswordToken(username, password + "1");
                Subject subject = SecurityUtils.getSubject();
                subject.login(upToken);
            } catch (AuthenticationException e) {
            }
        }

        UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(upToken);
    }


    private User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setMobilePhoneNumber(mobilePhoneNumber);
        user.setPassword(password);
        userService.saveAndFlush(user);
        return user;
    }

}
