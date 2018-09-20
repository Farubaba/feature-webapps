package com.farubaba.api.action;

import com.farubaba.data.model.User;
import com.farubaba.data.service.ConfigService;
import com.farubaba.data.service.SysConfigService;
import com.opensymphony.xwork2.ActionSupport;

public class ConfigActionXml extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 989849337948817719L;
	private User user;

	private ConfigService configService = new SysConfigService();
	public String config(){
		user = configService.sysConfig("v2");
		user.setUsername("I'm the v2 user : "+ user.getUsername());
		return "json";
	}

	public User getUser() {
		return user;
	}

}
