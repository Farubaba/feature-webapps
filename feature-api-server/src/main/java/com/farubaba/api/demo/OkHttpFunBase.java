
package com.farubaba.api.demo;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import javax.net.ssl.SSLHandshakeException;

import com.farubaba.api.action.OkHttpAction;
import com.farubaba.api.singleton.AppConfig;
import com.farubaba.data.model.User;
import com.farubaba.http.ssl.SslUtil;
import com.farubaba.root.utils.ConcurrentUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * 
 * @author violet
 *
 */
public class OkHttpFunBase {
	public static final String API_CONTEXT= AppConfig.getInstance().getUrlWithContextPath();
	public static final Gson gson = new GsonBuilder().create();
	
	OkHttpClient okHttpClient = null;
	OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
			
	public OkHttpFunBase() {
		try {
			okHttpClient = builder.sslSocketFactory(SslUtil.getSSLSocketFactory(),SslUtil.getX509TrustManager())
					.hostnameVerifier(SslUtil.getHostNameVerifier())
					.build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException | CertificateException
				| IOException e) {
			e.printStackTrace();
		}
			
	}
	/**
	 * 参考 {@link OkHttpAction#getUserListApi()}}
	 * @return
	 */
	public List<User> synchronousGetListUser(){
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/v2")
				.build();
		System.out.println("URL = " + request.url().toString());
		Call call = okHttpClient.newCall(request);
		Response response;
		try {
			response = call.execute();
			if(response.isSuccessful()){
				String result = response.body().string();
				System.out.println(result);
				List<User> users = gson.fromJson(result, new TypeToken<List<User>>(){}.getType());
				System.out.println(users.get(0).getUsername());
				return users;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String synchronousGetListUserOtherSite(){
		Request request = new Request.Builder()
				//.url("https://farubaba.github.io/")
				//.url("https://www.baidu.com/")
				.url("https://github.com/")
				.build();
		System.out.println("URL = " + request.url().toString());
		Call call = okHttpClient.newCall(request);
		Response response;
		try {
			response = call.execute();
			if(response.isSuccessful()){
				String result = response.body().string();
				return result;
			}
		} catch (IOException e) {
			if(e instanceof SSLHandshakeException){
				System.out.println("SSL 证书错误，okhttp没有预装信任 "+request.url()+ "对应的CA，目前只信任farubaba.cer证书。只能访问www.farubaba.com");
			}
			e.printStackTrace();
		}
		
		return null;
	}
	
	public List<User> asynchronousGet(){
		//模拟同步
		final CountDownLatch countDownLatch = ConcurrentUtil.newSingleStepCountDownLatch();
		
		final List<User> users = new ArrayList<User>();
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/v2")
				.build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			
			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					if(response.isSuccessful()){
						String result = response.body().string();
						System.out.println("asynchronousGet = " + result);
						//users.addAll(list);
						List<User> list = gson.fromJson(result, new TypeToken<List<User>>(){}.getType());
						users.addAll(list);
						System.out.println("list.size() = "+ list.size() + users.get(0).getUsername() );
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally{
					ConcurrentUtil.countDown(countDownLatch);
				}
			}
			
			@Override
			public void onFailure(Call call, IOException e) {
				ConcurrentUtil.countDown(countDownLatch);
			}
		});
		ConcurrentUtil.await(countDownLatch);
		return users;
	}
	
	public String accessingHeaders(){
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/v4")
				.header("User-Agent", "webview")
				.header("os", "android")
				.header("os", "android")
				.header("mac", "xxxx")
				.addHeader("os", "ios")
				.addHeader("os", "android")
				.addHeader("os", "android")
				
				.build();
		Call call = okHttpClient.newCall(request);
		Response response;
		try {
			response = call.execute();
			if(response.isSuccessful()){
				String headerCache = response.header("cache");
				return headerCache;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public String postPlainText(MediaType contentType,String content) {
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/post_plain_text")
				.post(RequestBody.create(contentType, content))
				.build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String postStreamAndReturn(final MediaType mediaType,final String post) {
		RequestBody body = new RequestBody() {
			
			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.write(post.getBytes());
				
			}
			
			@Override
			public MediaType contentType() {
				return mediaType;
			}
		};
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/post_stream")
				.post(body)
				.build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			return response.body().string();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String postFile(MediaType mediaType, File file) {
		Request request = new Request.Builder()
				.url(API_CONTEXT + "api/user/post_file")
				//.header("Connection", "Keep-Alive")
				.post(RequestBody.create(mediaType, file))
				.build();
		
		try {
			Response response = okHttpClient.newCall(request).execute();
			String result = response.body().string();
			System.out.println("OkHttpBase : response.body().string() = "+ result);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String postingFormParameters(String key, String value){
		String result = null;
		RequestBody requestBody = new FormBody.Builder()
				.add(key, value)
				.build();
		Request request = new Request.Builder().url(API_CONTEXT + "api/user/postform")
				.post(requestBody)
				.build();
		try {
			Response response = okHttpClient.newCall(request).execute();
			result = response.body().string();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String postMultiPart(String key, String value,final String bodyString) {
		String result = null;
		
		RequestBody body = new RequestBody() {
			
			@Override
			public void writeTo(BufferedSink sink) throws IOException {
				sink.write(bodyString.getBytes());
			}
			
			@Override
			public MediaType contentType() {
				return MediaType.parse("text/plain; charset=utf-8");
			}
		};
		
		
		MultipartBody maMultipartBody = new MultipartBody.Builder()
				.addFormDataPart(key, value)
				.addPart(body)
				.build();
		
		Request reqeust = new Request.Builder()
				.url(API_CONTEXT + "api/user/post_multi_part")
				.post(maMultipartBody)
				.build();
		try {
			Response response = okHttpClient.newCall(reqeust).execute();
			result = response.body().string();
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return result;
	}
}
