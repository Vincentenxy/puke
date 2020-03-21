package com.wx.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 分发器:加入状态内容处理
 * @author Vincent
 *
 */
public class Dispatcher implements Runnable {

	private Socket client;
	Request request = null;
	Response response = null;

	public Dispatcher(Socket client) {
		this.client = client;
		try {
			System.out.println("1dispatch");
			this.request = new Request(client);	// 获取请求协议
			this.response = new Response(client);	// 获取响应协议
		} catch (IOException e) {
			e.printStackTrace();
			this.release();
		}

	}

	@Override
	public void run() {
		try {
			if(null == request.getUrl() || request.getUrl().equals("")) {
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
				response.print((new String(is.readAllBytes())));
				response.pushInfoToBrowser(200);
				is.close();	
				return ;
			}
			
			System.out.println(request.getUrl());
			Servlet servlet = WebApp.getServletFromUrl(request.getUrl());
			if (null != servlet) {
				servlet.service(request, response);	//进入相应的业务处理逻辑***
				// 关注状态码
				response.pushInfoToBrowser(200);
			} else {
				// 错误
				
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");	//通过类加载器加载资源
				response.print((new String(is.readAllBytes())));
				response.pushInfoToBrowser(404);
				is.close();
			}
		} catch (Exception e) {
			try {
				response.println("你好我不好，我很快就好");
				response.pushInfoToBrowser(500);
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		release();
	}

	// 释放资源
	private void release() {
		try {
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
