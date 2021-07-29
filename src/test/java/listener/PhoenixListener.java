package listener;

import java.io.File;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import util.ScreenshotUtil;

public class PhoenixListener extends TestListenerAdapter {

	@Override
	public void onTestFailure(ITestResult tr) {
		super.onTestFailure(tr);
		System.out.println("-----------发现测试用例执行失败---------------");
		//思路：当测试用例执行失败，我要使用TakesScreenshot（子类实现了这个接口）进行截图
		//截图的各个操作在这里写好吗--》一个具体的业务
		//获得surefire-reports套件目录
		String outputDirectory = tr.getTestContext().getOutputDirectory();
		//当前套件的报告路径
		//E:\java2_workspace\phoenix_web_auto_4\target\surefire-reports\Suite
		//截串--》E:\java2_workspace\phoenix_web_auto_4\target\surefire-reports
		//重0开始截取，截取到最后一个\的索引位置
		String surefireDir = outputDirectory.substring(0, outputDirectory.lastIndexOf("\\"));
		//拿到test的名称
		//不同的test的截屏文件保存到不同的目录
		String testName = tr.getTestContext().getCurrentXmlTest().getName();
		//截屏的目录
		//E:\java2_workspace\phoenix_web_auto_4\target\surefire-reports\screenshot\register
		String screenshotDir = surefireDir + File.separator + "screenshot" + File.separator + testName;
		;
		//使用工具类进行截图
		ScreenshotUtil.takeScreenshot(screenshotDir);
		//

	}
}
