package com.log.web;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.stereotype.Component;

@ServerEndpoint(value = "/websocket/{uuid}/{service}")
@Component
public class LogsWebSocket {
	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;
	
	private static final String DEFAULT_SERVICE = "ALL";

	/* 客戶端集合 */
	private static Map<String, LogsWebSocket> clients = new ConcurrentHashMap<String, LogsWebSocket>();

	private String uuid;

	private String serviceName;
	/* 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(@PathParam("uuid") String uuid, @PathParam("service") String service, Session session) {
		this.session = session;
		this.uuid = uuid;
		this.serviceName = service;
		System.out.println();
		clients.put(uuid, this);
		addOnlineCount();
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
		/*try {
			sendMessage(String.valueOf(getOnlineCount()));
		} catch (IOException e) {
			System.out.println("IO异常");
		}*/
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		clients.remove(uuid); // 从map中删除
		subOnlineCount(); // 在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);

		// 群发消息
		for (LogsWebSocket item : clients.values()) {
			try {
				item.sendMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发生错误时调用
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("发生错误");
		error.printStackTrace();
	}

	public void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 按日志类型发送消息
	 */
	public static void sendInfo(String message, String serviceName) throws IOException {
		for (LogsWebSocket item : clients.values()) {
			try {
				// item.getService() == null || item.getService().trim().isEmpty() ||
				if (item.getService().equals(DEFAULT_SERVICE) || item.getService().equals(serviceName)) {
					item.sendMessage(message);
				}
			} catch (IOException e) {
				continue;
			}
		}
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public synchronized String getService() {
		return serviceName;
	}

	public static synchronized void addOnlineCount() {
		LogsWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		LogsWebSocket.onlineCount--;
	}
}
