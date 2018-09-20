package com.farubaba.api.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.farubaba.api.utils.ServerUtil;
import com.farubaba.root.utils.CloseUtil;
import com.farubaba.root.utils.IOUtil;
import com.opensymphony.xwork2.ActionSupport;

public class ReceiveAction extends ActionSupport {
	
	String receivedString = "";
	public String returnReceive(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if(ServerUtil.isPost(request)){
			System.out.println(request.getServletPath());
			File file = new File(request.getContextPath()+"/resources/files/test.md");
			try {
				receivedString = IOUtil.readIntoMemery(request.getInputStream(), -1);
				System.out.println("接收到POST中数据为："+ receivedString);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "json";
	}
	
	public String getReceivedString() {
		return receivedString;
	}
	
	
	public String doSveToFile(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		//File file = new File(request.getContextPath()+"/resources/files/test.md");
//		File file = new File(request.getContextPath()+"/test.md");
		
		/**
		 * 第一种获得文件绝对路径的方式
		 */
		String path = ServletActionContext.getServletContext().getRealPath("/resources/files/test.md");
		/**
		 * 无法获得绝对路径，无法用于创建文件
		 */
		//path = ServletActionContext.getServletContext().getContextPath()+"/abs/test.md";
		
		System.out.println("test.md path = "+ path);
		receivedString = "新创建的文件存储在: "+ path;
		
		File file = IOUtil.createFile(path);
		InputStream in = null;
		FileOutputStream fos = null;
		if(ServerUtil.isPost(request)){
			try {
				in = request.getInputStream();
//				fos = new FileOutputStream(file);
//				
//				byte[] b = new byte[1024];
//				int len = 0;
//				while((len = in.read(b))!=-1){
//					fos.write(b, 0 , len);
//				}
				
				IOUtil.writeToJsonSizeFile(in,file);
			} catch (IOException e) {
				e.printStackTrace();
				
			}finally{
				CloseUtil.closeIO(in);
				CloseUtil.closeIO(fos);
			}
		}
		return "json";
	}
	
}
