package com.easy555.uc.dao.entity;

import java.util.EnumSet;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.easy555.common.entity.Permission;
import com.easy555.uc.dao.integration.entity.Resource;
import com.easy555.uc.dao.integration.repository.ResourceRepository;
import com.easy555.uc.dao.test.BaseTest;

public class ResourceTest extends BaseTest {

	@Autowired
	private ResourceRepository repos;

	@Test
	public void testPermissionToDB() {

		Resource resource = new Resource();

		resource.setIdentity("wecome");
		resource.setName("xd");
		resource.setUrl("/wecome");
		resource.setPermission_val(EnumSet.of(Permission.Create, Permission.Delete));
		resource.setIcon("11");

		resource = repos.save(resource);
		clear();

		Assert.assertEquals(repos.count(), 1);
		Assert.assertNotNull(repos.findOne(resource.getId()));

		resource = repos.findOne(resource.getId());
		EnumSet<Permission> permissionSet = resource.getPermission_val();
		
		Assert.assertTrue(permissionSet.contains(Permission.Create));
		Assert.assertTrue(permissionSet.contains(Permission.Delete));
		Assert.assertFalse(permissionSet.contains(Permission.Read));

	}
}
