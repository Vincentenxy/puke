package com.wx.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��װ����Э�飺��װ�������ΪMap
 * @author Vincent
 *
 */
public class Request {

	private String requestInfo;	//Э����Ϣ
	private String method; 	//����ʽ
	private String url; 	//����url
	private String queryStr; 	//�������
	private Map<String, List<String>> parameterMap;
	private final String CRLF = "\r\n";

	
	public Request(Socket client) throws IOException {
		this(client.getInputStream());
	}
	public Request(InputStream is) {
		parameterMap = new HashMap<String,List<String>>();
		
		System.out.println(is);
		
		
		
		byte[] datas = new byte[1024*1024*1024];
		int len; 
		try {
			len = is.read(datas);
			this.requestInfo=  new String(datas, 0, len);
		} catch (IOException e) {
			e.printStackTrace();
			return ;
		} 
		
		//�ֽ��ַ���
		parseRequestInfo();
	}
	
	private void parseRequestInfo() {
		System.out.println("----�ֽ�----");
		System.out.println("---- 1����ȡ����ʽ����ͷ����һ��/ -----.");
		System.out.println("requestInfo-->"+requestInfo);
		this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).toLowerCase().trim();
		System.out.println("method-->" + this.method);
		System.out.println("---- 2����ȡ�����URL:��һ��/ ��HTTP/----");
		System.out.println("----���ܰ��������������ǰ��ΪURL----");
		
		//1����ȡ/��λ��
		int startIdx = this.requestInfo.indexOf("/") + 1;
		//2����ȡHTTP/��λ��
		int endIdx = this.requestInfo.indexOf("HTTP/"); 
		//3���ָ��ַ���
		this.url = this.requestInfo.substring(startIdx, endIdx).trim();
		//4���ͻ�ȡ����λ��
		int quertIdx = this.url.indexOf("?");
		if(quertIdx >= 0) {
			String[] urlArray = this.url.split("\\?");
			this.url = urlArray[0];
			queryStr = urlArray[1];
		}
		
		System.out.println(this.url);
		System.out.println("---- 3����ȡ������������Get�Ѿ���ȡ�����ʱpost��������������----");

		if(method.equals("post"	)) {
			String qStr = this.requestInfo.substring(this.requestInfo.lastIndexOf(CRLF)).trim();
			if(null == queryStr) {
				queryStr = qStr;
			}else {
				queryStr += "&" +qStr;
			}
		}
		queryStr = null==queryStr ? "" :queryStr;
		System.out.println(method + "--> " + url + "--> " + queryStr);
		
		//ת��Map fav=1&fav=2&uname=sha&age=18&others=
		convertMap();
	}
	
	// �����������ΪMap
	private void convertMap() {
		// 1���ָ��ַ��� &
		String[] keyValues = this.queryStr.split("&");
		for(String queryStr : keyValues) {
			//2���ٴηָ��ַ��� =
			String [] kv = queryStr.split("=");
			kv = Arrays.copyOf(kv, 2);	//���ֻ��key,û��value���������֤��Զ����������
			//��ȡkey��vluae
			String key = kv[0];
			String value = kv[1]==null ? null : decode(kv[1], "UTF-8");	//��������
			//��ŵ�map��
			if(!parameterMap.containsKey(key)) {	//��ʾ��һ��
				parameterMap.put(key, new ArrayList<String>());
			}
			parameterMap.get(key).add(value);
			
		}
		System.out.println("---------->"+parameterMap);
	}
	
	/**
	 * ��������
	 * @param value
	 * @return
	 */
	private String decode(String value, String enc) {
		try {
			return java.net.URLDecoder.decode(value, enc);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
	/**
	 * ͨ��name��ȡ��Ӧ�Ķ��ֵ
	 * @param key
	 * @return
	 */
	public String[] getParameterValues(String key) {
		List<String> values = this.parameterMap.get(key);
		if(null == values || values.size() <1) {
			return null; 
		}
		return values.toArray(new String[0]);
	}
	/**
	 * ͨ��name��ȡ��Ӧ��һ��ֵ
	 * @param key
	 * @return
	 */
	public String getParameter(String key) {
		String [] values = getParameterValues(key);
		return values == null ? null :values[0];
	}
	
	
	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public String getQueryStr() {
		return queryStr;
	}
	
	
}
