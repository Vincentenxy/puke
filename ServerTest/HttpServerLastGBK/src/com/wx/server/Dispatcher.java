package com.wx.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * �ַ���:����״̬���ݴ���
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
			this.request = new Request(client);	// ��ȡ����Э��
			this.response = new Response(client);	// ��ȡ��ӦЭ��
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
				servlet.service(request, response);	//������Ӧ��ҵ�����߼�***
				// ��ע״̬��
				response.pushInfoToBrowser(200);
			} else {
				// ����
				
				InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");	//ͨ���������������Դ
				response.print((new String(is.readAllBytes())));
				response.pushInfoToBrowser(404);
				is.close();
			}
		} catch (Exception e) {
			try {
				response.println("����Ҳ��ã��Һܿ�ͺ�");
				response.pushInfoToBrowser(500);
			} catch (Exception e1) {
				e.printStackTrace();
			}
		}
		release();
	}

	// �ͷ���Դ
	private void release() {
		try {
			client.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
