package com.farubaba.api.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;

import com.farubaba.api.demo.UserListData;
import com.farubaba.data.model.User;
import com.farubaba.data.service.DataService;
import com.farubaba.data.service.DataServiceImpl;
import com.farubaba.http.model.ErrorDetectModel;
import com.farubaba.http.model.ObjectErrorModel;
import com.farubaba.root.utils.IOUtil;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.MultimapBuilder;
import com.opensymphony.xwork2.ActionSupport;

/**
 * /api/user
 */
public class OkHttpAction extends ActionSupport {
	
	private DataService dataService = new DataServiceImpl();
	private List<User> users; 
	private String userListJsonString;
	private ListMultimap<String,String> headerMap = MultimapBuilder.hashKeys().arrayListValues().build();
	private String jsonString;
	private UserListData userListData = new UserListData();
	
	@Action("v1")
	public String getUserList(){
		try {
			Method method = this.getClass().getMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
			String apiVersion = method.getAnnotation(Action.class).value();
			users = dataService.getUsers(apiVersion); 
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	@Action("v2")
	public String getUserListApi(){
		Method method;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			//Enumeration<String> names = request.getAttributeNames();
			
			//{name=[valueOfName1, valueOfName2, namevalue-from-map], pwd=123}
//			Map<String,String[]> params = request.getParameterMap();
//			String[] nameValues = params.get("name");
//			System.out.println(params);
//			for(String v : nameValues){
//				System.out.println("name "+ " = "+ v);
//			}
		
			method = this.getClass().getMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
			String apiVersion = method.getAnnotation(Action.class).value();
			users = dataService.getUsers(apiVersion); 
			
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	
	@Action("v3")
	public String getUserListStringApi(){
		Method method;
		try {
			method = this.getClass().getMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
			String apiVersion = method.getAnnotation(Action.class).value();
			userListJsonString = dataService.getUserListJson(apiVersion);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	

	@Action("v4")
	public String accessHeader(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Enumeration<String> headers = request.getHeaderNames();
		StringBuilder sb = new StringBuilder();
		while(headers.hasMoreElements()){
			String headerName = headers.nextElement();
			Enumeration<String> headerValues = request.getHeaders(headerName);
			while(headerValues.hasMoreElements()){
				String headerValue = headerValues.nextElement();
				sb.append(headerName + " = " + headerValue).append("\r\n");
				headerMap.put(headerName, headerValue);
			}
		}
		
		ServletActionContext.getResponse().addHeader("cache", "server cache control");
		
		System.out.println("服务器接收到的Header为：");
		System.out.println(sb.toString());
		Set<String> keys = headerMap.keySet();
		System.out.println("放入setMultiMap中的Header为：");
		for(String key : keys){
			System.out.println(key + " = " + headerMap.get(key));
		}
		return "json";
	}
	
	@Action("post_plain_text")
	public String postPlainText(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String readFromClient = IOUtil.readIntoMemery(request.getInputStream(), -1);
			ServletActionContext.getResponse().getOutputStream().write(readFromClient.getBytes());
			ServletActionContext.getResponse().setContentLength(readFromClient.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	@Action("post_stream")
	public String postPStream(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String readFromClient = IOUtil.readIntoMemery(request.getInputStream(), -1);
			ServletActionContext.getResponse().getOutputStream().write(readFromClient.getBytes());
			ServletActionContext.getResponse().setContentLength(readFromClient.length());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	@Action("post_file")
	public String postFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			String readFromClient = IOUtil.readIntoMemery(request.getInputStream(), -1);
			System.out.println("服务器接收到的post_file内容："+readFromClient);
			HttpServletResponse response = ServletActionContext.getResponse();
			//错误
			//response.getOutputStream().write(readFromClient.getBytes());
			//response.setContentLength(readFromClient.length());
			
			response.setContentType("text/x-markdown; charset=utf-8");
			response.getWriter().write(readFromClient);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	

	//@Action("post_form")
	public String postForm(){
		HttpServletRequest request = ServletActionContext.getRequest();
		jsonString = request.getParameter("name");
		System.out.println("服务器接收到的post_file内容："+jsonString);
		return "json";
	}
	
	public String postMultiPart(){
		StringBuffer sb = new StringBuffer();
		HttpServletRequest request = ServletActionContext.getRequest();
		sb.append(request.getParameter("key"))
		.append("----");
		jsonString = sb.toString();
		return "json";
	}
	
	@Action("getUsersApi")
	public String getUsersApi(){
		Method method;
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			method = this.getClass().getMethod(Thread.currentThread().getStackTrace()[1].getMethodName());
			String apiVersion = method.getAnnotation(Action.class).value();
			userListData.setData(dataService.getUsers(apiVersion)); 
			userListData.setSuccess(true);
			userListData.setSuc(true);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return "json";
	}
	
	
	public ObjectErrorModel result = new ObjectErrorModel();
	@Action("postString")
	public String postString(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		System.out.println("服务器接收到: name = "+ name + " pwd = "+ pwd);
		ErrorDetectModel mode = new ErrorDetectModel();
		mode.setMessage("错误信息");
		mode.setCode(40009);
		mode.setDisplay("服务器接收到: name = "+ name + " pwd = "+ pwd);
		result.setCode(200);
		result.setDisplay("服务器接收到: name = "+ name + " pwd = "+ pwd);
		result.setSuccess(true);
		result.setError(mode);
		return "json";
	}
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setUserListJsonString(String userListJsonString) {
		this.userListJsonString = userListJsonString;
	}
	
	public String getUserListJsonString(){
		return this.userListJsonString;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public void setUserListData(UserListData userListData) {
		this.userListData = userListData;
	}
	
	public UserListData getUserListData() {
		return userListData;
	}
}
