package com.wx.user;

import com.wx.server.Request;
import com.wx.server.Response;
import com.wx.server.Servlet;

public class OtherServlet implements Servlet {
	
	@Override
	public void service(Request request, Response response) {
		response.print("ÆäËû²âÊÔÒ³Ãæ");
	} 
}
