package com.wx.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static WebContext webContext;
	static {
		try {
			// SAX解析
			// 1、获取解析工厂
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2、从解析工厂获取解析器
			SAXParser parse = factory.newSAXParser();
			// 3、加载文档Document注册解析器

			// 4、编写文档处理器
			WebHandler handler = new WebHandler();
			// 5、解析
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),handler);
			//获取数据
			webContext = new WebContext(handler.getEntitys(),handler.getMappings());
		}catch(Exception e) {
			System.out.println("解析配置文件错误");
		}
	}
	
	/**
	 * 通过URL获取配置文件对应的url
	 * @param url
	 * @return
	 */
	public static Servlet getServletFromUrl(String url) {		
		//假设输入login
		String className = webContext.getClz("/" + url);
		System.out.println("url ==>" +url );
		System.out.println("className ==>" +className );
		Class clz;
		try {
			clz = Class.forName(className);
			Servlet servlet = (Servlet)clz.getConstructor().newInstance();
			return servlet;
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null; 
	}
}
