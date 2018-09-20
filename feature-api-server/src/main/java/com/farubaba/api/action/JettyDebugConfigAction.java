package com.farubaba.api.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class JettyDebugConfigAction extends ActionSupport {
	
	private List<String> jettyDebugConfigImageList;
	
	public List<String> getJettyDebugConfigImageList() {
		return jettyDebugConfigImageList;
	}

	public void setJettyDebugConfigImageList(List<String> jettyDebugConfigImageList) {
		this.jettyDebugConfigImageList = jettyDebugConfigImageList;
	}

	/**
	 * 为了快速测试，这里并没有编写Service，Dao等，所有都写在Action中
	 * @return
	 */
	public String jettyDebugConfig(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ request.getContextPath() +"/";
		String image1 = basePath+"resources/images/maven-jetty-debug-config.png";
		String image2 = basePath+"resources/images/maven-jetty-debug-config-sourcecode.png";
		jettyDebugConfigImageList = new ArrayList<String>();
		jettyDebugConfigImageList.add(image1);
		jettyDebugConfigImageList.add(image2);
		return SUCCESS;
	}
}
