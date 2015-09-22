/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.uc.dao.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-14 下午4:25
 * <p>Version: 1.0
 */

@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration({"classpath:spring-common.xml", "classpath:spring-test.xml"})
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
public abstract class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    protected EntityManager entityManager;


    public void clear() {
        flush();
        entityManager.clear();
    }

    public void flush() {
        entityManager.flush();
    }


    protected String nextRandom() {
        return System.currentTimeMillis() + RandomStringUtils.randomNumeric(5);
    }

}
