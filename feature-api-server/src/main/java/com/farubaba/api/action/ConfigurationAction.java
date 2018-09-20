package com.farubaba.api.action;

import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.farubaba.api.utils.ServerUtil;
import com.farubaba.data.model.Data;
import com.farubaba.data.model.User;
import com.farubaba.data.service.ConfigService;
import com.farubaba.data.service.SysConfigService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opensymphony.xwork2.ActionSupport;

//@ParentPackage("dataserver")
//@Namespace("/api/sys/config") 
@ParentPackage("dataserver")  
@Namespace("/api/sys/config")  
@Results({  
    @Result(name = "json",type="json", params={"root","user"})  
}) 
public class ConfigurationAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 989849337948817719L;
	private ConfigService configService = new SysConfigService();
	private User user;

	private String name;
	
	@Action(value="v1")  
	public String configv1() throws IOException{
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String method = request.getMethod();
		System.out.println("requestMethod = "+ method);
		String contentType = request.getContentType();
		user = configService.sysConfig("v1");
		
		if(ServerUtil.isGet(request)){
			user.setAddress("成都-get-response");
		}
		if(ServerUtil.isPost(request)){
			user.setAddress("成都-post-response");
		}
		
		if(ServerUtil.isJsonBody(request)){
			Gson gson = new GsonBuilder().create();
			
			InputStreamReader reader = new InputStreamReader(request.getInputStream()); 
			//JsonReader jsonReader = gson.newJsonReader(reader);
			Data data = gson.fromJson(reader, Data.class);
			System.out.println(data.toString());
			user.setAddress("成都-post-json-response");
		}
			
		return "json";
	}

	public User getUser() {
		return user;
	}

	public String getName() {
		name = "testname";
		return name;
	}

	
	
}
