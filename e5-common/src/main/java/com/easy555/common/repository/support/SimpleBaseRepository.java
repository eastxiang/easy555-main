/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.easy555.common.repository.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.easy555.common.entity.search.Searchable;
import com.easy555.common.plugin.entity.LogicDeleteable;
import com.easy555.common.repository.BaseRepository;
import com.easy555.common.repository.RepositoryHelper;
import com.easy555.common.repository.callback.SearchCallback;
import com.easy555.common.repository.support.annotation.QueryJoin;
import com.google.common.collect.Sets;

/**
 * <p>
 * 抽象基础Custom Repository 实现
 * </p>
 * <p>
 * User: Zhang Kaitao
 * <p>
 * Date: 13-1-15 下午7:33
 * <p>
 * Version: 1.0
 */
public class SimpleBaseRepository<M, ID extends Serializable> extends SimpleJpaRepository<M, ID>
		implements BaseRepository<M, ID> {

	public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
	public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
	public static final String FIND_QUERY_STRING = "from %s x where 1=1 ";
	public static final String COUNT_QUERY_STRING = "select count(x) from %s x where 1=1 ";

	private final EntityManager em;
	private final JpaEntityInformation<M, ID> entityInformation;

	private final RepositoryHelper repositoryHelper;

	private Class<M> entityClass;
	private String entityName;
	private String idName;

	/**
	 * 查询所有的QL
	 */
	private String findAllQL;
	/**
	 * 统计QL
	 */
	private String countAllQL;

	private QueryJoin[] joins;

	private SearchCallback searchCallback = SearchCallback.DEFAULT;

	public SimpleBaseRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);

		this.entityInformation = entityInformation;
		this.entityClass = this.entityInformation.getJavaType();
		this.entityName = this.entityInformation.getEntityName();
		this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
		this.em = entityManager;

		repositoryHelper = new RepositoryHelper(entityClass);

		findAllQL = String.format(FIND_QUERY_STRING, entityName);
		countAllQL = String.format(COUNT_QUERY_STRING, entityName);
	}

	/**
	 * 设置searchCallback
	 *
	 * @param searchCallback
	 */
	public void setSearchCallback(SearchCallback searchCallback) {
		this.searchCallback = searchCallback;
	}

	/**
	 * 设置查询所有的ql
	 *
	 * @param findAllQL
	 */
	public void setFindAllQL(String findAllQL) {
		this.findAllQL = findAllQL;
	}

	/**
	 * 设置统计的ql
	 *
	 * @param countAllQL
	 */
	public void setCountAllQL(String countAllQL) {
		this.countAllQL = countAllQL;
	}

	public void setJoins(QueryJoin[] joins) {
		this.joins = joins;
	}

	/////////////////////////////////////////////////
	//////// 覆盖默认spring data jpa的实现////////////
	/////////////////////////////////////////////////

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	@Transactional
	@Override
	public void delete(final ID id) {
		M m = findOne(id);
		delete(m);
	}

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	@Transactional
	@Override
	public void delete(final M m) {
		if (m == null) {
			return;
		}
		if (m instanceof LogicDeleteable) {
			((LogicDeleteable) m).markDeleted();
			save(m);
		} else {
			super.delete(m);
		}
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param ids
	 *            实体
	 */
	@Transactional
	@Override
	public void delete(final ID[] ids) {
		if (ArrayUtils.isEmpty(ids)) {
			return;
		}
		List<M> models = new ArrayList<M>();
		for (ID id : ids) {
			M model = null;
			try {
				model = entityClass.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("batch delete " + entityClass + " error", e);
			}
			try {
				BeanUtils.setProperty(model, idName, id);
			} catch (Exception e) {
				throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
			}
			models.add(model);
		}
		deleteInBatch(models);
	}

	@SuppressWarnings("rawtypes")
	@Transactional
	@Override
	public void deleteInBatch(final Iterable<M> entities) {
		Iterator<M> iter = entities.iterator();
		if (entities == null || !iter.hasNext()) {
			return;
		}

		Set models = Sets.newHashSet(iter);

		boolean logicDeleteableEntity = LogicDeleteable.class.isAssignableFrom(this.entityClass);

		if (logicDeleteableEntity) {
			String ql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, entityName);
			repositoryHelper.batchUpdate(ql, models);
		} else {
			String ql = String.format(DELETE_ALL_QUERY_STRING, entityName);
			repositoryHelper.batchUpdate(ql, models);
		}
	}

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	@Transactional
	@Override
	public M findOne(ID id) {
		if (id == null) {
			return null;
		}
		if (id instanceof Integer && ((Integer) id).intValue() == 0) {
			return null;
		}
		if (id instanceof Long && ((Long) id).longValue() == 0L) {
			return null;
		}
		return super.findOne(id);
	}

	//////// 根据Specification查询
	//////// 直接从SimpleJpaRepository复制过来的///////////////////////////////////
	@Override
	public M findOne(Specification<M> spec) {
		try {
			return getQuery(spec, (Sort) null).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.data.repository.CrudRepository#findAll(ID[])
	 */
	public List<M> findAll(Iterable<ID> ids) {

		return getQuery(new Specification<M>() {
			public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<?> path = root.get(entityInformation.getIdAttribute());
				return path.in(cb.parameter(Iterable.class, "ids"));
			}
		}, (Sort) null).setParameter("ids", ids).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(
	 * org.springframework.data.jpa.domain.Specification)
	 */
	public List<M> findAll(Specification<M> spec) {
		return getQuery(spec, (Sort) null).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(
	 * org.springframework.data.jpa.domain.Specification,
	 * org.springframework.data.domain.Pageable)
	 */
	public Page<M> findAll(Specification<M> spec, Pageable pageable) {

		TypedQuery<M> query = getQuery(spec, pageable);
		return pageable == null ? new PageImpl<M>(query.getResultList()) : readPage(query, pageable, spec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(
	 * org.springframework.data.jpa.domain.Specification,
	 * org.springframework.data.domain.Sort)
	 */
	public List<M> findAll(Specification<M> spec, Sort sort) {

		return getQuery(spec, sort).getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.data.jpa.repository.JpaSpecificationExecutor#count(
	 * org.springframework.data.jpa.domain.Specification)
	 */
	public long count(Specification<M> spec) {

		return getCountQuery(spec).getSingleResult();
	}

	@Override
	public List<M> findAll() {
		return repositoryHelper.findAll(findAllQL);
	}

	@Override
	public List<M> findAll(final Sort sort) {
		return repositoryHelper.findAll(findAllQL, sort);
	}

	@Override
	public Page<M> findAll(final Pageable pageable) {
		return new PageImpl<M>(repositoryHelper.<M> findAll(findAllQL, pageable), pageable,
				repositoryHelper.count(countAllQL));
	}

	@Override
	public long count() {
		return repositoryHelper.count(countAllQL);
	}

	/////////////////////////////////////////////////
	/////////////////// 自定义实现////////////////////
	/////////////////////////////////////////////////

	@Override
	public Page<M> findAll(final Searchable searchable) {
		List<M> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
		long total = searchable.hasPageable() ? count(searchable) : list.size();
		return new PageImpl<M>(list, searchable.getPage(), total);
	}

	@Override
	public long count(final Searchable searchable) {
		return repositoryHelper.count(countAllQL, searchable, searchCallback);
	}

	/**
	 * 重写默认的 这样可以走一级/二级缓存
	 *
	 * @param id
	 * @return
	 */
	@Override
	public boolean exists(ID id) {
		return findOne(id) != null;
	}

}
