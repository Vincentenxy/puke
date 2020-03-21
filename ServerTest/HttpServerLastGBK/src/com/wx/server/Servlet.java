package com.wx.server;

/**
 * 服务器小脚本接口
 * @author Vincent
 *
 */
public interface Servlet {
    void service(Request request, Response response); 
}
