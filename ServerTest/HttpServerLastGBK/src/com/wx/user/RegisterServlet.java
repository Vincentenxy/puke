package com.wx.user;

import com.wx.server.Request;
import com.wx.server.Response;
import com.wx.server.Servlet;

public class RegisterServlet implements Servlet{

	@Override	
	public void service(Request request, Response response) {
		
		//��עҵ���߼�
		String uname = request.getParameter("uname");
		String[] fav = request.getParameterValues("fav");
		
		response.print("��ע�����ϢΪ" + uname);
		response.println("��ϲ��������Ϊ��" );
		
		for(String v: fav) {
			if(v.equals("0")) {
				response.print("��������");
			}else if(v.equals("1")) {
				response.print("�÷���");
			}else if(v.equals("2")) {
				response.print("yujie��");
			}
				
		}
	}
	
}
