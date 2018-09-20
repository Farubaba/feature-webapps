package com.farubaba.api.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.farubaba.api.singleton.AppConfig;
import com.farubaba.data.dao.ListUser;
import com.farubaba.data.dao.ListUser2;
import com.farubaba.http.body.StringRequestBody;
import com.farubaba.http.impl.HttpMethod;
import com.farubaba.http.impl.MimeType;
import com.farubaba.http.impl.RequestCallback;
import com.farubaba.http.impl.RequestContext;
import com.farubaba.http.impl.RequestHandler;
import com.farubaba.http.manager.HttpManager;
import com.farubaba.http.model.ObjectErrorModel;
import com.farubaba.okhttp.OkHttpAdapter;
import com.farubaba.root.interf.impl.ErrorResult;
import com.farubaba.root.utils.ConcurrentUtil;


/**
 * Struts2-json-plugin 继承属性无法转化到JSON
 * MultipartBody 需要验证每一个part对应的MediaType是否正确设置
 * 
 * @author violet
 *
 */
public class OkHttpManagerDataCenter {
	
	/**
	 * 
	 * @author gaoge
	 * android 平台支持bks格式证书文件
	 * 生成bks文件命令：
	 * 
	 * keytool -importcert -v -trustcacerts -alias certfengjr -file server.crt 
	 * -keystore fengjr.bks -storetype BKS 
	 * -providerclass org.bouncycastle.jce.provider.BouncyCastleProvider -providerpath ./bcprov-jdk15on-146.jar 
	 * -storepass fengjr601
	 * 
	 * 其中server.crt是原格式证书
	 * fengjr.bks是目标生成证书
	 * fengjr601是bks格式文件证书密码
	 *
	 */
	public static final String API_CONTEXT= AppConfig.getInstance().getUrlWithContextPath();
	String patternUrl = "api/user/v2?name=%1$s&name=%2$s";
	String fullUrl = API_CONTEXT + "api/user/getUsersApi?name=%1$s&name=%2$s";
	
	@Test
	public void okHttpGetTest(){
	
		final CountDownLatch counter = ConcurrentUtil.newSingleStepCountDownLatch();
		OkHttpAdapter manager = new OkHttpAdapter();
		Map<String,String> querys = new HashMap<String, String>();
		querys.put("name", "namevalue-from-map");
		querys.put("pwd", "123");
		RequestCallback<ListUser> callback = new RequestCallback<ListUser>() {
			@Override
			public void onSuccess(ListUser result) {
				super.onSuccess(result);
				ConcurrentUtil.countDown(counter);
			}
			
			@Override
			public void onFailure(ErrorResult error) {
				super.onFailure(error);
				ConcurrentUtil.countDown(counter);
			}
		};
		
		RequestContext<ListUser> requestContext = new RequestContext<ListUser>()
				.setDomain(API_CONTEXT)
				.setUrl(String.format(patternUrl, "valueOfName1","valueOfName2"))
				.setQuerys(querys)
				.setCallback(callback);
				
		manager.sendDefaultRequest(requestContext);
		ConcurrentUtil.await(counter);
	}
	
	
	@Test
	public void okHttpGetFullUrlTest(){
	
		OkHttpAdapter manager = new OkHttpAdapter();
		Map<String,String> querys = new HashMap<String, String>();
		querys.put("name", "namevalue-from-map2");
		querys.put("pwd", "123456");
		
		RequestContext<ListUser> requestContext = new RequestContext<ListUser>();
		requestContext.setDomain(API_CONTEXT)
				.setUrl(String.format(fullUrl, "valueOfName11","valueOfName22"))
				.setQuerys(querys);
				
		manager.sendDefaultRequest(requestContext);
	}
	
	@Test
	public void asyncOkHttpGetTest(){
	
		System.out.println("发送请求的线程ID ："+Thread.currentThread().getId());
		
		HttpManager manager = HttpManager.getInstance(new OkHttpAdapter());
		Map<String,String> querys = new HashMap<String, String>();
		querys.put("name", "namevalue-from-map2-asyc");
		querys.put("pwd", "123456");
		
		RequestCallback<ListUser2> callback = new RequestCallback<ListUser2>() {
			@Override
			public void onSuccess(ListUser2 result) {
					String name = result.getData().get(0).getUsername();
					System.out.println("接收response的线程ID："+Thread.currentThread().getId()+"  成功获得userName = "+name);
							
			}

			@Override
			public void onFailure(ErrorResult result) {
				// TODO Auto-generated method stub
				System.out.println("接收到的错误：  " + result.getMessage());
				
			}
		};
		
		RequestContext<ListUser2> requestContext = new RequestContext<ListUser2>()
				.setDomain(API_CONTEXT)
				.setUrl(String.format(fullUrl, "valueOfName11","valueOfName22"))
				.setQuerys(querys)
				.setCallback(callback)
				.setResultModelClass(ListUser2.class);
	
				
		manager.sendDefaultRequest(requestContext);
	}
	
	@Test
	public void asyncOkHttpGetTest2(){
	
		System.out.println("发送请求的线程ID ："+Thread.currentThread().getId());
		
		HttpManager manager = HttpManager.getInstance(new OkHttpAdapter());
		
		Map<String,String> querys = new HashMap<String, String>();
		querys.put("name", "namevalue-from-map2-asyc-test2");
		querys.put("pwd", "1234567890");
		
		final CountDownLatch countDownLatch = ConcurrentUtil.newSingleStepCountDownLatch();
		
		RequestCallback<ListUser2> callback = new RequestCallback<ListUser2>() {
			@Override
			public void onSuccess(ListUser2 result) {
					String name = result.getData().get(0).getUsername();
					System.out.println("接收response的线程ID："+Thread.currentThread().getId()+"  成功获得userName = "+name);
					countDownLatch.countDown();	
			}

			@Override
			public void onFailure(ErrorResult result) {
				// TODO Auto-generated method stub
				System.out.println("接收到的错误：  " + result.getMessage());
				countDownLatch.countDown();	
				
			}
		};
		
		
		RequestContext<ListUser2> requestContext = new RequestContext<ListUser2>()
				//.setDomain(domain)
				.setUrl(String.format(fullUrl, "valueOfName11","valueOfName22"))
				.setQuerys(querys)
				.setCallback(callback)
				.setResultModelClass(ListUser2.class);
		
		RequestHandler handler = manager.sendDefaultRequest(requestContext);
		//you can cancel request like below:
		//handler.cancelRequest();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void httpManagerPostString(){
		final CountDownLatch countDownLatch = ConcurrentUtil.newSingleStepCountDownLatch();
		String postStringUrlWithUrlParamter = API_CONTEXT + "api/bussiness/postString?name=%1$s&pwd=%2$s";
		postStringUrlWithUrlParamter = API_CONTEXT + "api/bussiness/postString?name=%1$s&pwd=%2$s";
		String bodyContent = "hello body, this is the message post by a beautiful girl, who lives on the other size of the earth, don't you exciting?";
		HttpManager manager = HttpManager.getInstance(new OkHttpAdapter());
		RequestContext<ObjectErrorModel> requestContext = new RequestContext<ObjectErrorModel>()
				.setUrl(String.format(postStringUrlWithUrlParamter, "lzg", "123"))
				.setMethod(HttpMethod.POST)
				.setRequestBody(new StringRequestBody()
						.setMimeType(MimeType.TEXT_X_MARKDOWN)
						.setBodyContent(bodyContent))
				.setResultModelClass(ObjectErrorModel.class)
				.setCallback(new RequestCallback<ObjectErrorModel>() {
					@Override
					public void onSuccess(ObjectErrorModel result) {
						System.out.println("正确返回 : result.display = " + result.getDisplay() );
						System.out.println("正确返回 : result.getError().display = " + result.getError().getDisplay() );
						countDownLatch.countDown();
					}

					@Override
					public void onFailure(ErrorResult result) {
						System.out.println("错误返回");
						countDownLatch.countDown();
					}
				});
		manager.sendDefaultRequest(requestContext);
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
