package com.farubaba.api.singleton;

import java.util.Properties;

import com.farubaba.root.utils.PropertiesUtil;
import com.farubaba.root.utils.UrlUtil;

public class AppConfig {
	
	private Properties properties;
	/**
	 * 存放在项目根目录下，以后fang'ruWEB-INF目录下的资源文件
	 */
	public static final String CONFIG_FILE_NAME = "configuration.properties";
	public static final String KEY_SCHEME = "scheme";
	public static final String KEY_HOST = "host";
	public static final String KEY_PORT = "port";
	public static final String KEY_CONTEXT_PATH = "context_path";
	public static final String KEY_PROXY_ENABLED = "proxy_enabled";
	public static final String KEY_PROXY_HOST = "proxy_host";
	public static final String KEY_PROXY_PORT = "proxy_port";
	
	private static AppConfig instance = new AppConfig();
	private String configFileName = CONFIG_FILE_NAME;
	
	private AppConfig() {
		reloadDefaultProperties();
	}
	
	public static AppConfig getInstance() {
		return instance;
	}
	
	public AppConfig setConfigFile(String configFileName) {
		this.configFileName = configFileName;
		return this;
	}
	
	public String getConfigFileName() {
		return this.configFileName == null ? CONFIG_FILE_NAME : this.configFileName;
	}
	
	public Properties reloadDefaultProperties() {
		return reloadProperties(CONFIG_FILE_NAME);
	}
	
	public Properties reloadProperties(String propertiesFileName) {
		this.properties = PropertiesUtil.loadProperties(propertiesFileName);
		return this.properties;
	}
	
	public Properties getProperties() {
		return properties;
	}
	
	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	public String getProperty(String key) {
		return getProperties().getProperty(key);
	}
	
	public String getScheme() {
		return getProperty(KEY_SCHEME);
	}
	
	public String getHost() {
		return getProperty(KEY_HOST);
	}
	
	public int getPort() {
		String port = getProperty(KEY_PORT);
		try {
			return Integer.valueOf(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public String getContextPath() {
		return getProperty(KEY_CONTEXT_PATH);
	}
	
	public boolean isProxyEnabled() {
		String proxyEnable = getProperty(KEY_PROXY_ENABLED);
		try {
			return Boolean.valueOf(proxyEnable);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getProxyHost() {
		return getProperty(KEY_PROXY_HOST);
	}
	
	public int getProxyPort() {
		String port = getProperty(KEY_PORT);
		try {
			return Integer.valueOf(port);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 构建如下形式的URL：
	 * 		scheme://host:port/contextPath/ <br>
	 * 		scheme://host/contextPath/ <br>
	 * @param contextPath
	 * @return
	 */
	public String getUrlWithContextPath(String contextPath) {
		return UrlUtil.buildUrl(getScheme(), getHost(), getPort(), contextPath);
	}
	
	public String getUrlWithContextPath() {
		return getUrlWithContextPath(getContextPath());
	}
}
