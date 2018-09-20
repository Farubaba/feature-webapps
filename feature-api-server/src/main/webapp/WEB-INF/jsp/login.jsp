<!DOCTYPE html>
<%@page import="com.google.gson.GsonBuilder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.farubaba.data.model.User" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
    	.button{
    		width:300px;
    		height:30px;
    		text-size:26pt;
    		text-align:center;
    		background-color:#2bbc8a;
    	}
    </style>
  </head>
  <body>  	
  	<h2>
  		<%= pageName.replace("_002d", "-").replace("_", ".") %> :		
  	</h2>
  	<h4>查看地址栏Url，输入正确的用户名：<font color="red">farubaba</font> 密码：<font color="red">123456</font>，则会登陆成功，返回user数据。</h4>
  	<h4>如果没有给userName和password参数或者参数错误，则并不会返回User对象，下面也不会有值显示：</h4>
  	<h4>JSON String User: [jsonUser] = <s:property value="jsonUser" /></h4>
  	<h4>Object User: [user] = <s:property value="user" /></h4>
  	<p>getServletName = <%= getServletName() %></p>
  	<p>getServletInfo = <%= getServletInfo() %></p>
  	<p>getServletContext.getAttribute() = <%= getServletContext().getAttribute("jsonUser") %></p> 
    <!--
    定义url action 和 param 
     -->
     <s:url var="loginWithNoParam" action="doLogin"></s:url>
   	
   	<s:url var="doLogin" action="doLogin">
   		<s:param name="userName">farubaba</s:param>
   		<s:param name="password">123456</s:param>
   	</s:url>
   	 
   	<s:url var="userlogin" action="userLogin" >
   		<s:param name="userName">farubaba</s:param>
   		<s:param name="password">123456</s:param>
   	</s:url>
   	
   	<s:url var="userlogin" action="userLogin" >
   		<s:param name="userName">farubaba</s:param>
   		<s:param name="password">123456</s:param>
   	</s:url>
   	
    <ul>
    	<li>没有登陆参数，调用 <a href="${loginWithNoParam}">login.action -> return null String</a></li>
    	<li>提供正确登录参数，调用 <a href="${doLogin}">login.action -> return JSON String User</a></li>
    	<li>提供正确登录参数，调用<a href="${userlogin}">userLogin.action -> return Object User </a></li>
    	<li>
    	使用form表单提交登录：(输入：<font color="red">farubaba</font> 和 <font color="red">123456</font> 可登录成功!登录成功后，JSON String User 和 Object User其中之一会有值)
	    <s:form action="doLogin"><p>
		 	name &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: <s:textfield name="userName" label="Your name" /><p>
		 	password : <s:textfield name="password" label="Your password"/><p>
		 	<s:submit value="登录" cssClass="button" />
		</s:form>
    	</li>
    </ul>
    <p><p><p><s:include value="../../road-map.jsp"></s:include>
   
  </body>
</html>