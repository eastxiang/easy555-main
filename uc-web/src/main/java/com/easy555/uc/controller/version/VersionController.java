package com.easy555.uc.controller.version;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 显示工程的版本号<br>
 * 版本号由maven自动替换形成.
 * 
 * create date: 2015年12月5日 下午9:59:04
 * 
 * @author xiangdong
 */

@Controller
public class VersionController {

	/**
	 * POM.xml 配置自动替换成当前版本号;
	 */
	@Value("${system.current.version}")
	private String currentVersion = "0.1";

	/**
	 * 获取当期版本号
	 * 
	 * @return 版本号
	 */
	@ResponseBody
	@RequestMapping("/version")
	public String version(HttpServletRequest request) {
		return request.getContextPath() + ": " + currentVersion;
	}
}
