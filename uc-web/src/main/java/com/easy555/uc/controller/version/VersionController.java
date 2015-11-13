package com.easy555.uc.controller.version;

import org.springframework.beans.factory.annotation.Value;

public class VersionController {

	// POM.xml 配置自动替换成当前版本号;
	@Value(value = "${system.current.version}")
	final String version = "0.0.1";
	
}
