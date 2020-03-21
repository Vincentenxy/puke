package com.wx.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.wx.server.Request;
import com.wx.server.Response;
import com.wx.server.Servlet;

public class LoginServlet implements Servlet {
	@Override
	public void service(Request request, Response response) {
		
		StringBuilder content = new StringBuilder(); 
		
		//��ȡ��Ӧҳ��Դ��
		File htmlFile = new File("./src/login.html");
		BufferedReader reader = null; 
		//������Ӧ��ҳ��
		try {
			reader = new BufferedReader(new FileReader(htmlFile));	//����Ӧҳ��д�뷵������
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		String tempStr; 
		try {
			while((tempStr = reader.readLine()) != null) {
				response.print(tempStr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		response.print("���н��" + request.getParameter("uname"));
	}
}
