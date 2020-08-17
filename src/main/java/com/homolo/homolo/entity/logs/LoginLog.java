package com.homolo.homolo.entity.logs;

import java.util.Date;

/**
 * 系统用户登录日志.
 */

public class LoginLog {

	private String id;
	//ip
	private String ip;
	//用户名
	private String userName;
	//日期
	private Date loginDate;
	//操作类型/登录登出
	private String loginLogType;
	//结果1成功， 9失败
	private int result;
	//消息描述
	private String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginDate() {
		if (this.loginDate != null) {
			return new Date(this.loginDate.getTime());
		} else {
			return null;
		}
	}

	public void setLoginDate(Date loginDate) {
		if (loginDate != null) {
			this.loginDate = (Date) loginDate.clone();
		} else {
			this.loginDate = null;
		}
	}

	public String getLoginLogType() {
		return loginLogType;
	}

	public void setLoginLogType(String loginLogType) {
		this.loginLogType = loginLogType;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
