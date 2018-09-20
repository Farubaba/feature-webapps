package com.farubaba.api.utils;

import javax.servlet.http.HttpServletRequest;

public class ServerUtil {
	
	public static boolean isMethod(String method, HttpServletRequest request){
		if(method != null && method.equalsIgnoreCase(request.getMethod())){
			return true;
		}
		return false;
	}
	public static boolean isGet(HttpServletRequest request){
		return isMethod("get",request);
	}
	
	public static boolean isPost(HttpServletRequest request){
		return isMethod("post",request);
	}
	
	public static boolean isJsonBody(HttpServletRequest request){
		if(hasPostBody(request)){
			String contentType = request.getContentType();
			if(contentType != null && contentType.contains("application/json")){
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasPostBody(HttpServletRequest request){
		if(isPost(request)){
			if(request.getContentLength() > 0){
				return true;
			}
		}
		return false;
	}
	
	
}
