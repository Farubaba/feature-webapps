<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pageName = pageContext.getPage().getClass().getSimpleName();
%>

<html>
  <head>
  	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <title>Mobile Server Struts2 Application - Welcome</title>
  </head>
  <body>
  	<p>getServletName = <%= getServletName() %></p>
  	<p>getServletInfo = <%= getServletInfo() %></p>
  	<p>getServletContext.getAttribute() = <%= getServletContext().getAttribute("") %></p>
  	
    <h2><%=pageName.replace("_002d", "-").replace("_", ".") %></h2>
    <h3>web-server提供的所有api接口地址：</h3>
    
    
    <ol> 
    	<h4>演示如何使用get, post key-value pairs, post json, post Multipart 来请求服务器端接口：</h4>
     	<li>Get请求 : <a href="api/sys/config/v1" target="_blank"><%=basePath%>api/sys/config/v1</a></li>
     	<li>
     		<form name='form1' action='api/sys/config/v1' method='post'>   
				<input type="hidden" name='data' value='{username:"farubaba",password:"123456"}' alt="input your post json string to here"/>  
				Post key-value pairs 请求：<a href='javascript:document.form1.submit();'><%=basePath%>api/sys/config/v1 </a>  
			</form>
     	</li>
     	<li>Post json 请求：</li>
     	<li>Post multipart 请求：</li>
		
    </ol>
    <p><p><p><s:include value="../../road-map.jsp"></s:include>
  </body>
</html>