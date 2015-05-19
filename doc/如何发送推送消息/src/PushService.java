package com.module.openfirepush.service;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.jivesoftware.smack.AccountManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.common.service.BaseService;
import com.common.utils.LogUtils;
import com.common.utils.PropertyLoader;

@Service("PushService")
public class PushService extends BaseService {
	private Logger logger = LogUtils.getLogger(getClass());
	private XMPPConnection conn = null;

	@PostConstruct
	public void init() throws Exception {
		connectToServer();
	}

	@PreDestroy
	public void destroy() throws Exception {
		if (conn != null)
			conn.disconnect();
	}

	/**
	 * 获得当前连接
	 * 
	 * @return
	 */
	public XMPPConnection getConnnection() {
		return conn;
	}

	/**
	 * 发送消息，并不保证消息一定到达用户
	 * 
	 * @param msg
	 * @return
	 */
	public boolean sendMessage(Message msg) {
		try {
			if (!ifConnectToServer())
				connectToServer();
			conn.sendPacket(msg);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 注册账户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean registerAccount(String pushuser, String pushpwd) {
		try {
			if (!ifConnectToServer())
				connectToServer();
			AccountManager am = AccountManager.getInstance(conn);
			am.createAccount(pushuser, pushpwd);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 连接到推送服务器，如果已连接，则直接返回
	 */
	public void connectToServer() {
		if (ifConnectToServer()) {
			logger.info("已连接到服务器");
			return;
		} else {
			// 断开连接
			disconnectToServer();
			try {
				Properties properties = PropertyLoader
						.getProperties("pushserver.properties");
				String host = properties.getProperty("pushserver.host");
				String port = properties.getProperty("pushserver.port");
				String srvname = properties.getProperty("pushserver.srvname");
				String username = properties.getProperty("pushserver.user");
				String password = properties.getProperty("pushserver.pwd");

				logger.info("推送服务器:" + host + ":" + port + "/" + srvname);
				logger.info("推送系统账户：" + username + "/" + password);

				ConnectionConfiguration config = new ConnectionConfiguration(
						host, Integer.parseInt(port), srvname);
				config.setReconnectionAllowed(true);
				config.setSecurityMode(SecurityMode.disabled);
				conn = new XMPPTCPConnection(config);
				conn.connect();

				try {
					AccountManager am = AccountManager.getInstance(conn);
					am.createAccount(username, password);
				} catch (Exception e) {
					logger.debug("推送服务器注册系统用户(" + username + "," + password
							+ ")失败");
				}

				conn.login(username, password);
				System.out.println("成功连接并登录推送到服务器");
			} catch (Exception e) {
				System.out.println("推送服务初始化失败");
				disconnectToServer();
			}
		}
	}

	/**
	 * 检查后台是否连接到推送服务器
	 * 
	 * @return
	 */
	public boolean ifConnectToServer() {
		if (conn != null && conn.isConnected() && conn.isAuthenticated()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 断开与推送服务器的连接
	 */
	public void disconnectToServer() {
		if (conn != null) {
			try {
				conn.disconnect();
			} catch (NotConnectedException e) {
			}
			conn = null;
		}
	}

}
