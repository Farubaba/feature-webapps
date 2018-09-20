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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Hello World! Struts2</title>
    <style type="text/css">
    	.message{
    		color:red;
    	}
    </style>
  </head>
  <body>	
  	<h2><%=pageName.replace("_002d", "-").replace("_", ".") %></h2>
  	<h2>Hello World! Struts2</h2>
  	<h4><span class="message">messageStore.message在action中被赋值，交替点击下面的超链接</span>，查看地址栏Url变化以及messageStore.message的显示值变化：</h4>
  	<h4>访问<a href="<%=basePath%>HelloWorld.jsp"> [HelloWorld.jsp] </a>和<a href="<s:url action='hello'/>"> [hello.action] </a> messageStore.message 值变化:</h4>
    <h4>messageStore.message = <span class="message"><s:property value="messageStore.message" /></span></h4>
    
    <p><p><p><s:include value="road-map.jsp"></s:include>
  </body>
</html>