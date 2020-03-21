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
		
		//获取响应页面源码
		File htmlFile = new File("./src/login.html");
		BufferedReader reader = null; 
		//返回响应的页面
		try {
			reader = new BufferedReader(new FileReader(htmlFile));	//将响应页面写入返回数据
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
		
		response.print("族中结果" + request.getParameter("uname"));
	}
}
