package com.homolo.homolo.entity.logs;

import java.util.Date;

/**
 * 系统操作日志.
 */
public class OperationLog {

	//数据id
	private String id;
	//描述
	private String description;
	//方法
	private String method;
	//请求ip
	private String requestIp;
	//创建时间
	private Date createDate;
	//操作用户
	private String userName;
	//用户id
	private String userid;
	//操作日志类型
	private String operationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public Date getCreateDate() {
		if (this.createDate != null) {
			return new Date(this.createDate.getTime());
		} else {
			return null;
		}
	}

	public void setCreateDate(Date createDate) {
		if (createDate != null) {
			this.createDate = (Date) createDate.clone();
		} else {
			this.createDate = null;
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
}
