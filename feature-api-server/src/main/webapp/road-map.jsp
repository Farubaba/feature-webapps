<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String pageName = pageContext.getPage().getClass().getSimpleName();
String contextPath = "test-webapp";
%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Road Map</title>
  </head>
  <body>  	
  	<h4>显示当前server中所有的jsp页面链接：</h4>
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>HelloWorld.jsp"> HelloWorld.jsp </a>
		&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>index.jsp"> index.jsp </a>
  		&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>model.jsp"> model.jsp </a>
  		&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=basePath%>road-map.jsp"> road-map.jsp </a>
  		&nbsp;&nbsp;&nbsp;&nbsp;<a href="<s:url action='jettyDebugConfig'/>"> maven-jetty-debug-config.jsp </a>
    	&nbsp;&nbsp;&nbsp;&nbsp;<a href="<s:url action='doLogin'/>"> login.jsp </a>
    	&nbsp;&nbsp;&nbsp;&nbsp;<a href="<s:url action='api/user/v1'/>"> okhttp.jsp </a>
    	</body>
</html>