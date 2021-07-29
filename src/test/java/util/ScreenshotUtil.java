package util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import base.BaseTester;

/**
 * 截图的工具类
 * @author tommy
 * @date 2018年6月20日
 * @desc   
 * @email 1754739303@qq.com
 */
public class ScreenshotUtil {
	
	private static Logger logger = Logger.getLogger(ScreenshotUtil.class);

	/**
	 * 截屏的方法
	 * @param screenshotDir 截屏文件保存的目录
	 */
	public static void takeScreenshot(String screenshotDir) {
		//拿到dirver
		WebDriver driver = BaseTester.getDriver();
		//当前时间
		Date date = new Date();
		//获得毫秒值
		long time = date.getTime();
		//文件名称
		String fileName = time + ".jpg";
		//如果这个dirver是ChromeDriver的一个实例
		logger.info("开始截屏");
		//强转为接口类型
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		//源文件
		File screenshotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		//创建一个目标文件
		File destFile = new File(screenshotDir +File.separator+ fileName);
		//把截屏保存到screenshotDir这个目录中去
		try {
			logger.info("拷贝截屏到目标路径中去");
			FileUtils.copyFile(screenshotFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//失效、过时
	@Deprecated
	public static void takeScreenshot2(String screenshotDir) {
		//拿到dirver
		WebDriver driver = BaseTester.getDriver();
		//当前时间
		Date date = new Date();
		//获得毫秒值
		long time = date.getTime();
		//文件名称
		String fileName = time + ".jpg";
		//如果这个dirver是ChromeDriver的一个实例
		File screenshotFile  = null;
		logger.info("开始截屏");
		if (driver instanceof ChromeDriver) {
			//强制转换
			ChromeDriver chromeDriver = (ChromeDriver) driver;
			//截图：这是源文件
			screenshotFile = chromeDriver.getScreenshotAs(OutputType.FILE);
		}else if (driver instanceof FirefoxDriver) {
			//强制转换
			FirefoxDriver firefoxDriver = (FirefoxDriver) driver;
			//截图：这是源文件
			screenshotFile = firefoxDriver.getScreenshotAs(OutputType.FILE);
		}else if (driver instanceof InternetExplorerDriver) {
			//强制转换
			InternetExplorerDriver internetExplorerDriver = (InternetExplorerDriver) driver;
			//截图：这是源文件
			screenshotFile = internetExplorerDriver.getScreenshotAs(OutputType.FILE);
		}
		
		//创建一个目标文件
		File destFile = new File(screenshotDir +File.separator+ fileName);
		//把截屏保存到screenshotDir这个目录中去
		try {
			logger.info("拷贝截屏到目标路径中去");
			FileUtils.copyFile(screenshotFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Date date = new Date();
		long time = date.getTime();
		System.out.println(time);
		Thread.sleep(1000);
		Date date2 = new Date();
		System.out.println(date2.getTime());
	}
}
