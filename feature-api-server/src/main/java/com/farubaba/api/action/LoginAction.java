
package com.farubaba.api.action;

import org.apache.struts2.ServletActionContext;

import com.farubaba.data.model.User;
import com.farubaba.data.service.LoginService;
import com.farubaba.data.service.UserLoginService;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	
	private String jsonUser;
	private User user;
	
	public String getJsonUser() {
		return jsonUser;
	}

	public void setJsonUser(String jsonUser) {
		this.jsonUser = jsonUser;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	LoginService loginService = new UserLoginService();
	
	/**
	 * 返回给客户端JSON字符串
	 * @return
	 */
	public String login() {
		String userName = ServletActionContext.getRequest().getParameter("userName");
		String password = ServletActionContext.getRequest().getParameter("password");
		if(userName != null && password != null){
			jsonUser =loginService.login(userName, password);
		}
        return SUCCESS;
    }
	
	/**
	 * 返回给客户端User对象
	 * @return
	 */
	public String userLogin(){
		String userName = ServletActionContext.getRequest().getParameter("userName");
		String password = ServletActionContext.getRequest().getParameter("password");
		user =loginService.userLogin(userName, password);
        return SUCCESS;
	}
}
