package com.wx.server;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WebApp {
	private static WebContext webContext;
	static {
		try {
			// SAX����
			// 1����ȡ��������
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// 2���ӽ���������ȡ������
			SAXParser parse = factory.newSAXParser();
			// 3�������ĵ�Documentע�������

			// 4����д�ĵ�������
			WebHandler handler = new WebHandler();
			// 5������
			parse.parse(Thread.currentThread().getContextClassLoader().getResourceAsStream("web.xml"),handler);
			//��ȡ����
			webContext = new WebContext(handler.getEntitys(),handler.getMappings());
		}catch(Exception e) {
			System.out.println("���������ļ�����");
		}
	}
	
	/**
	 * ͨ��URL��ȡ�����ļ���Ӧ��url
	 * @param url
	 * @return
	 */
	public static Servlet getServletFromUrl(String url) {		
		//��������login
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
