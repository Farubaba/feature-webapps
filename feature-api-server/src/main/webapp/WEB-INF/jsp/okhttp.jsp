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
    <h4>这是jsp模板页面，新建jsp时，建议直接拷贝该页面，改名后修改即可：</h4>
    <p>userListJsonString:<s:property value="userListJsonString" /></p>
    <p>users:<s:property value="users" /></p>
    <p><p><p><s:include value="road-map.jsp"></s:include>
  </body>
</html>