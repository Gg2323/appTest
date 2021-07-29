package base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.SeleniumException;
import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import testcase.LoginPage;
import util.PagesUtil;
import util.PropertiesUtil;
import util.SeleniumUtil;

/**
 * 基础测试类
 * 
 * @author tommy
 * @date 2018年5月23日
 * @desc
 * @email
 */
public class BaseTester {

	private static Logger logger = Logger.getLogger(BaseTester.class);

	/**
	 * 不加static：对象属性，各不相同的 加static：类属性、静态属性：在内存中只会有一份（大家是共用）
	 */
	protected static WebDriver driver = null;

	@BeforeSuite
	@Parameters(value = { "browserType", "seleniumVersion", "driverPath" })
	public void beforeSuite(String browserType, String seleniumVersion, String driverPath) {
		driver = SeleniumUtil.openBrowser(browserType, seleniumVersion, driverPath);
	}

	@AfterSuite
	public void afterSuite() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		driver.quit();
	}
	
	public static WebDriver getDriver() {
		return driver;
	}

	/**
	 * 智能查找元素（通用方法、封装好的方法） 访问当前页面
	 * 
	 * @param by
	 * @return
	 */
	protected WebElement getElement(String keyword) {
		return getElement(keyword, 5, this.getClass());
	}

	/**
	 * 定位指定页面的元素
	 * 
	 * @param keyword
	 * @param clazz
	 * @return
	 */
	protected WebElement getElement(String keyword, Class<?> clazz) {
		return getElement(keyword, 5, clazz);
	}

	/**
	 * 
	 * @param keyword
	 *            关键字
	 * @param timeOutInSeconds
	 *            超时时间
	 * @return
	 */
	// 这个方法有局限性：只能获得执行测试用例的类对应的页面上的元素
	// 如果我要获得登录页面的元素？？--》告诉我登录页面的名称--》LoginPage.class.getName()
	protected WebElement getElement(String keyword, long timeOutInSeconds, Class<?> pageClazz) {
		// Locator locator =
		// PagesUtil.pagesMap.get(this.getClass().getName()).get(keyword);
		Locator locator = PagesUtil.pagesMap.get(pageClazz.getName()).get(keyword);
		// 通过locator的信息返回web元素
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		// 等待，直到找到某个元素为止
		WebElement element = wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver driver) {
				// 通过locator信息，创建一个by
				String byStr = locator.getBy();
				String value = locator.getValue();
				String desc = locator.getDesc();
				/*
				 * if ("id".equals(byStr)) { By by = null; by = By.id(value); }
				 * else if ("name".equals(byStr)) { by = By.name(value); } else
				 * if ("tagName".equals(byStr)) { by = By.tagName(value); } else
				 * if ("linkText".equals(byStr)) { by = By.linkText(value); }
				 * else if ("partialLinkText".equals(byStr)) { by =
				 * By.partialLinkText(value); } else if
				 * ("className".equals(byStr)) { by = By.className(value); }
				 * else if ("cssSelector".equals(byStr)) { by =
				 * By.cssSelector(value); } else if ("xpath".equals(byStr)) { by
				 * = By.xpath(value); }
				 */

				By by = null;
				try {
					// 不管你是id、name、cssSelector。。。这些都是By类中的方法
					// 字节码得到By类的细节
					Class<By> clazz = By.class;
					// 方法的细节：id、name
					// 得到by的一个静态方法
					Method byMethod = clazz.getMethod(byStr, String.class);
					// 执行这个方法
					by = (By) byMethod.invoke(null, value);
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				return driver.findElement(by);
			}
		});
		return element;
	}

	/**
	 * 智能等待查找元素列表 定位当前页面的的元素列表
	 * 
	 * @param by
	 * @return
	 */
	/**
	 * 智能查找元素（通用方法、封装好的方法）
	 * 
	 * @param by
	 * @return
	 */
	protected List<WebElement> getElements(String keyword) {
		return getElements(keyword, 5, this.getClass());
	}

	/**
	 * 定位指定页面的元素列表
	 * 
	 * @param keyword
	 * @param pageClass
	 * @return
	 */
	protected List<WebElement> getElements(String keyword, Class<?> pageClass) {
		return getElements(keyword, 5, pageClass);
	}

	protected List<WebElement> getElements(String keyword, long timeOutInSeconds, Class<?> pageClass) {
		Locator locator = PagesUtil.pagesMap.get(pageClass.getName()).get(keyword);
		// 通过locator的信息返回web元素
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		// 等待，直到找到某个元素为止
		List<WebElement> elements = wait.until(new ExpectedCondition<List<WebElement>>() {
			@Override
			public List<WebElement> apply(WebDriver driver) {
				// 通过locator信息，创建一个by
				String byStr = locator.getBy();
				String value = locator.getValue();
				String desc = locator.getDesc();
				By by = null;
				if ("id".equals(byStr)) {
					by = By.id(value);
				} else if ("name".equals(byStr)) {
					by = By.name(value);
				} else if ("tagName".equals(byStr)) {
					by = By.tagName(value);
				} else if ("linkText".equals(byStr)) {
					by = By.linkText(value);
				} else if ("partialLinkText".equals(byStr)) {
					by = By.partialLinkText(value);
				} else if ("className".equals(byStr)) {
					by = By.className(value);
				} else if ("cssSelector".equals(byStr)) {
					by = By.cssSelector(value);
				} else if ("xpath".equals(byStr)) {
					by = By.xpath(value);
				}

				return driver.findElements(by);
			}
		});
		return elements;
	}

	/**
	 * 打开一个链接
	 * 
	 * @param url
	 *            要打开的网页地址
	 */
	protected void to(String urlKey) {
		// driver.get(url);
		// 1：用保存数据
		// 2：怎么操作数据
		// 通过PropertiesUtil获得urlkey的对应的值
		String url = PropertiesUtil.getURL(urlKey);
		driver.navigate().to(url);

	}

	/**
	 * 最大化
	 */
	protected void maximize() {
		driver.manage().window().maximize();
	}

	/**
	 * 返回
	 */
	protected void back() {
		driver.navigate().back();

	}

	/**
	 * 前进
	 */
	protected void forward() {
		driver.navigate().forward();

	}

	/**
	 * 刷新
	 */
	protected void refresh() {
		driver.navigate().refresh();
	}

	/**
	 * 输入内容
	 * 
	 * @param content
	 *            要输入的内容
	 * @param webElement
	 *            输入框
	 */
	protected void type(String keyword, String content) {
		type(keyword, content, this.getClass());
	}
	
	/**
	 * 
	 * @param keyword
	 * @param content
	 * @param pageClass
	 */
	protected void type(String keyword, String content,Class<?> pageClass) {
		logger.info("往[" + keyword + "]元素输入内容:[" + content + "]");
		WebElement webElement = getElement(keyword,pageClass);
		webElement.sendKeys(content);
	}

	/**
	 * 单击当前页面的元素
	 * 
	 * @param webElement
	 *            要点击的元素
	 */
	protected void click(String keyword) {
		click(keyword, this.getClass());
	}
	
	/**
	 * 单击某个页面的元素(更加的通用，更加的具体)
	 * @param keyword
	 * @param pageClass
	 */
	protected void click(String keyword,Class<?> pageClass) {
		logger.info("单击元素[" + keyword + "]");
		WebElement webElement = getElement(keyword,pageClass);
		webElement.click();
	}

	/**
	 * 获得元素的文本
	 * 
	 * @param webElement
	 *            元素
	 * @return 元素文本 统一日志处理！！！！--》通用性的方法，只需要写一遍
	 */
	protected String getText(String keyword) {
		return getText(keyword, this.getClass());
	}
	
	protected String getText(String keyword,Class<?> pageClass) {
		WebElement webElement = getElement(keyword,pageClass);
		String text = webElement.getText();
		logger.info("获取到元素[" + keyword + "]的文本内容:" + text);
		return text;
	}
	
	/**
	 * 断言页面元素文本值为某文本
	 * @param keyword
	 * @param expectedText
	 */
	protected void assertTextPresent(String keyword,String expectedText){
		String actualText = getText(keyword);
		Assert.assertEquals(actualText, expectedText);
	}
	
	/**
	 * 断言页面元素文本值包含某文本
	 * @param keyword
	 * @param expectedText
	 */
	protected void assertPartialTextPresent(String keyword,String expectedText){
		String actualText = getText(keyword);
		Assert.assertTrue(actualText.contains(expectedText));
	}
	
	/**
	 * 断言某元素可编辑(启用状态)
	 * @param keyword
	 */
	protected void assertElementEditable(String keyword){
		WebElement element = getElement(keyword);
		Assert.assertTrue(element.isEnabled());
	}
	
	
//	assertElementNotEditable：断言某元素不可编辑
//	assertURLContains：断言当前URL包含
//	assertTextNotPresent
//	assertTextNotPresentPrecesion
//	assertElementAttributeValueEquals
//	assertElementAttributeValueNotEquals
//	assertAlertTextContains
// 检查的地方-》检查点--》断言方法--》方便使用

	public static void main(String[] args) {

		WebElement element=null;
		By by=By.id("2");
		Select select=new Select(element);
		select.selectByValue("#");
		WebDriverWait wait =null;


		
	}
}
