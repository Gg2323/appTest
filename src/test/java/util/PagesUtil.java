package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.Locator;
import testcase.RegisterPage;

/**
 * 页面的工具类
 * @author tommy
 * @date 2018年6月13日
 * @desc 
 * @email
 */
public class PagesUtil {
	
	public static Map<String, Map<String,Locator>> pagesMap = null;

	static{
		loadPages();
	}

	/**
	 * 加载所有页面的ui元素
	 * @return
	 */
	private static Map<String, Map<String,Locator>> loadPages() {
		//通过reader读取xml文件
		SAXReader reader = new SAXReader();
		
		try {
			//得到文档对象
			Document document = reader.read(PagesUtil.class.getResourceAsStream("/pages.xml"));
			//获得根标签（元素）
			Element element = document.getRootElement();
			//获得这个根元素下所有我想要的子元素-->page标签
			List<Element> pagesElements = element.elements("page");
			//创建一个map，用来存放所有的页面map（value是每个页面的map信息，key为页面的名称）
			//告诉我一个页面的名称，就能把这个页面找到，告诉我某个元素的描述，就能把这个元素找到
			pagesMap = new HashMap<String, Map<String,Locator>>();
			//循环列表，遍历每个page
			for (Element pagesElement : pagesElements) {
				//获得每个page标签下的locator标签
				List<Element> locatorElements = pagesElement.elements("locator");
				//得到当前页面的名称
				String pageName = pagesElement.attributeValue("name");
				//创建一个列表，存放每个页面的locator(这个没用了哈！！！)
				List<Locator> locators = new ArrayList<Locator>();
				//用来保存当前页面的每个locator，key为locator的desc属性，这样子可以非常方便的通过描述信息就能找到这个locator
				//这个map具有这个页面的所有元素信息
				Map<String,Locator> locatorsMap = new HashMap<String,Locator>();
				//每个页面的元素定位信息都放到了一个map中间，但是这些map又放到哪去呢？？
				
				//遍历每个locator
				for (Element locatorElement : locatorElements) {
					//获取每个locator的属性信息
					//定位的方式
					String by = locatorElement.attributeValue("by");
					//定位的值
					String value = locatorElement.attributeValue("value");
					//元素的描述
					String desc = locatorElement.attributeValue("desc");
					//封装到locator对象--》10个locator
					Locator locator = new Locator(by, value, desc);
					//把每个locator用key-value的形式保存起来
					locatorsMap.put(desc, locator);
				}
				pagesMap.put(pageName, locatorsMap);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		return pagesMap;
	}

	//example
	public static void main(String[] args) {
		Map<String, Map<String, Locator>> pageMap = loadPages();
		
		//我要找到注册页面的注册按钮？？？
//		Locator locator = pageMap.get("登录的页面").get("密码输入框");
//		System.out.println(locator);
//		
		//找到注册页面的注册按钮--》注册页面、注册按钮（通过注册按钮的标识，就能找到注册按钮）
		/**
		 * 1:页面信息（元素信息）都存放到了xml中间（多个page，每个page有多个locator）
		 * 2：解析xml，把这些定位信息给提取出来--》java对象
		 * 3：我们怎么去定位到某一个元素？？
		 * 		首先我要知道在哪一个页面？
		 * 		我们只要知道是哪一个locator(key-value)
		 */
		//反射！！！
		RegisterPage page = new RegisterPage();
		//如果要得到一个类的细节，首先要拿到一个字节码对象，这个字节码对象就具有这个类的所有细节
		//1:通过对象来过得，对象.getClass()
		System.out.println(page.getClass().getName());
		System.out.println(page.getClass().getSimpleName());
		//2:类.class;
		Class<RegisterPage> clazz = RegisterPage.class;
		System.out.println(clazz.getName());
		
	}
	/**
	 * po的思想--》数据驱动
	 * 关键字驱动
	 */
	
}
