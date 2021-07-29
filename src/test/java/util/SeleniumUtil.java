package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriver.SystemProperty;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Selenium工具类：
 * 	工具：util、tool、handler、manger...
 * @author tommy
 * @date 2018年5月21日
 * @desc 
 * @email
 * 
 * ctrl+o
 */
public class SeleniumUtil {
	
	//非常方便得打开一个浏览器：ie、Chrome、firefox
	/**
	 * 
	 * @param browserType
	 * @param seleniumVersion
	 * @param driverPath
	 */
	public static WebDriver openBrowser(String browserType,String seleniumVersion,String driverPath){
		
		WebDriver driver = null; //面向接口编程（多态）
		//如果是ie浏览器的话：忽略大小写
		if ("ie".equalsIgnoreCase(browserType)) {
			driver = openIEBrowser(driverPath);
		}else if ("chrome".equalsIgnoreCase(browserType)) {
			driver = openChromeBrowser(driverPath);
		}else if ("firefox".equalsIgnoreCase(browserType)) {
			driver = openFirefoxBrower(seleniumVersion, driverPath);
		}
		return driver;
	}

	/**
	 * 打开一个火狐浏览器
	 * @param seleniumVersion
	 * @param driverPath
	 * @return
	 */
	private static WebDriver openFirefoxBrower(String seleniumVersion, String driverPath) {
		System.setProperty(SystemProperty.BROWSER_BINARY, "D:\\Program Files\\Mozilla Firefox\\firefox.exe");
		//2.x:不需要可执行的驱动文件:推荐：selenium2.53.1  火狐46.0 geckdriver 0.16.0
		//3.x:需要可执行的驱动：jdk1.8
		//我需要一个依据：Selenium的版本是多少
		if ("3.x".equalsIgnoreCase(seleniumVersion)) {
			System.setProperty("webdriver.gecko.driver", driverPath);
		}
		return new FirefoxDriver();
	}

	private static WebDriver openChromeBrowser(String driverPath) {
		System.setProperty("webdriver.chrome.driver", driverPath);
		return new ChromeDriver();
	}

	private static WebDriver openIEBrowser(String driverPath) {
		System.setProperty(InternetExplorerDriverService.IE_DRIVER_EXE_PROPERTY, driverPath);
		//driver期望的能力
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//忽略IE安全设置
		capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		//忽略页面缩放
		capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
		//设置初始url，放置window对象丢失
		capabilities.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, "http://www.baidu.com");
		//通过能力创建driver
		return new InternetExplorerDriver(capabilities);
	}

}
