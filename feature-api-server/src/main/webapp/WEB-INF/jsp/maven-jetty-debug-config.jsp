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
		#content{
			max-width:56rem;/*56rem is ok for hexo "cactus" theme pages*/
			margin-left:auto;
			margin-right:auto;
			padding-left:2rem;
			padding-right:2rem;
			display: -webkit-flex; 
  			flex-direction: column;
			-webkit-justify-content: center;  
			-webkit-align-items: center;
		}
		
		.row{
			width:100%;
		    height: auto;
		    padding: 15px 10px; /*padding会把View撑大，可以修改0px 为其他大于零的数值看效果*/
		    margin-left: 10px;/*margin会让View整体移动*/
		    margin-right: 10px;/*margin会让View整体移动*/
		    /*结论：左右间距不建议使用padding，view会被撑大,尤其是有设置背景的时候。*/
		    text-align: left;
		    /*text-indent: 2em;/*首行缩进*/	
		    background-color: #f5f5f5;    
		}
		
		xmp,img{
			width: 100%;
			height: auto;
		}
	</style>
  </head>
  <body>  	
  	<!-- 
  	jettyDebugConfigImageList是一个List<String>，循环出的每一个item就是id=image，即item=id=image
  	-->
  	<div id="content">
  		<div class="row">
  			<h2><%=pageName.replace("_002d", "-").replace("_", ".") %></h2>
  		</div>
  		
  		<div class="row">
  			<font color="red">在myEclipse中配置maven webapp 项目使用mvn jetty:rum 来debug调试代码，并添加源码支持。</font><p>
  			当前页面使用的CSS：
  		</div>
		<xmp>
		<style type="text/css">
			#content{
				max-width:56rem;/*56rem is ok for hexo "cactus" theme pages*/
				margin-left:auto;
				margin-right:auto;
				padding-left:2rem;
				padding-right:2rem;
				display: -webkit-flex; 
	  			flex-direction: column;
				-webkit-justify-content: center;  
				-webkit-align-items: center;
			}
			
			.row{
				width:100%;
				height: auto;
				padding: 15px 10px; /*padding会把View撑大，可以修改0px 为其他大于零的数值看效果*/
				margin-left: 10px;/*margin会让View整体移动*/
				margin-right: 10px;/*margin会让View整体移动*/
				/*结论：左右间距不建议使用padding，view会被撑大,尤其是有设置背景的时候。*/
				text-align: left;
				/*text-indent: 2em;/*首行缩进*/	
				/*background-color: #f5f5f5;*/    
			}
			
			xmp,img{
				width: 100%;
				height: auto;
			}
		</style>
		</xmp>
	  	<s:iterator var="image" value="jettyDebugConfigImageList" status="status">
	  		<div class="row">
	  			<b><s:property value="#status.index+1"/>、</b><s:property value="#image"/>
	  		</div>
	    	<img src="<s:property value='#image'/>"></img>
	    </s:iterator>
	    <div class="row">
    		<s:include value="../../road-map.jsp"></s:include>
    	</div>
    </div>
    
    
  </body>
</html>