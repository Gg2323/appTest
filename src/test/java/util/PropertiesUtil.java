package util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	//用来加载url属性文件的对象
	private static Properties urlProperties;

	static {
		loadProperties();
	}

	private static void loadProperties() {
		try {
			if (urlProperties == null) {
				urlProperties = new Properties();
			}
			urlProperties.load(PropertiesUtil.class.getResourceAsStream("/url.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param urlKey
	 * @return
	 */
	public static String getURL(String urlKey) {
		return urlProperties.getProperty(urlKey);
	}
	
	/**
	 * example
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(urlProperties.get("registerURL"));
	}
}
