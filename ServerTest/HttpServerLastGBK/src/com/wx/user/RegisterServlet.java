package com.wx.user;

import com.wx.server.Request;
import com.wx.server.Response;
import com.wx.server.Servlet;

public class RegisterServlet implements Servlet{

	@Override	
	public void service(Request request, Response response) {
		
		//关注业务逻辑
		String uname = request.getParameter("uname");
		String[] fav = request.getParameterValues("fav");
		
		response.print("你注册的信息为" + uname);
		response.println("你喜欢的类型为：" );
		
		for(String v: fav) {
			if(v.equals("0")) {
				response.print("了螺鲤形");
			}else if(v.equals("1")) {
				response.print("好放行");
			}else if(v.equals("2")) {
				response.print("yujie行");
			}
				
		}
	}
	
}
