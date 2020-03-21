package com.wx.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标:多线程处理，加入分发器
 * 
 * @author Vincent
 *
 */

public class Server {

	private ServerSocket serverSocket;
	private boolean isRunning;

	public static void main(String[] args) {
		Server server = new Server();
		server.start();

	}

	// 启动服务
	public void start() {

		try {
			serverSocket = new ServerSocket(8888);
			isRunning = true;
			receive();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("服务器启动失败!");
			stop();
		}

	}

	// 接收连接处理
	public void receive() {
		while (isRunning) {
			try {
				Socket client = serverSocket.accept();
				System.out.println("一个客户端链接过来了");
				// 多线程处理
				new Thread(new Dispatcher(client)).start();

			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("客户端错误");
			}
		}
	}

	// 停止服务
	public void stop() {
		isRunning = false;
		try {
			this.serverSocket.close();
			System.out.println("服务器已停止");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
