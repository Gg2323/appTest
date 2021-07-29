package testcase;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTester;
import base.Locator;
import bsh.commands.dir;
import util.ExcelUtil;
import util.PagesUtil;

public class RegisterPage extends BaseTester {
	
	private static Logger logger = Logger.getLogger(RegisterPage.class);

	@Test(dataProvider = "provideFailureData")
	public void test_case_001(String mobilephone, String password, String pwdconfirm, String expectedTips) {
		to("registerURL");
		type("手机号输入框", mobilephone);
		type("密码输入框", password);
		type("重复密码输入框", pwdconfirm);
		click("注册按钮");
		assertTextPresent("提示信息", expectedTips);
		
		//断言这个元素的文本内容是否符合预期
		//断言元素文本内容是否符合预期
//		String actualTips = getText("提示信息");
//		Assert.assertEquals(actualTips, expectedTips);
		
	}
	
	@Test(dataProvider = "provideSuccessData")
	public void test_case_002(String mobilephone, String password, String pwdconfirm, String expectedTips) {
		to("registerURL");
		type("手机号输入框", mobilephone);
		type("密码输入框", password);
		type("重复密码输入框", pwdconfirm);
		click("注册按钮");
		
		to("registerURL");
		type("手机号输入框", mobilephone);
		type("密码输入框", password);
		type("重复密码输入框", pwdconfirm);
		click("注册按钮");
		
		WebElement loginButton = getElement("登录按钮",LoginPage.class);
		Assert.assertNotNull(loginButton);
	}

	/**
	 * 数据提供者
	 * 	 硬编码：不好维护
	 * 	数据解耦：jdbc、log4j
	 * 		key-value--》属性文件
	 * 		对象、实体信息--》xml、json、属性文件...
	 * 		多个对象、实体的信息--》xml，excel
	 * 		几行几列：二维数组-->excel--》读取、解析
	 * 
	 * @return
	 */
	//反向测试用例：数据保存在resource底下的register.xlsx文件sheet1的第2行到第7行
	@DataProvider
	public Object[][] provideFailureData() {
		Object[][] data = ExcelUtil.readExcel("/register.xlsx", 0, 2, 7, 1, 4);
		return data;
	}
	
	@DataProvider
	public Object[][] provideSuccessData() {
		Object[][] data = ExcelUtil.readExcel("/register.xlsx", 0, 8, 8, 1, 4);
		return data;
	}
	
	/**
	 * 整个测试用例的编写门槛降低（普通测试人员）
	 * 测试人员不断的、高度的封装，对测试开发的要求比较高
	 * 傻瓜化的测试用例的执行
	 */
	
	public static void main(String[] args) {
		RegisterPage page = new RegisterPage();
		Class<?> clazz1 = page.getClass();
		Class<RegisterPage> clazz2 = RegisterPage.class;
		System.out.println(clazz1 == clazz2);
		System.out.println(1);

	}
	
}
