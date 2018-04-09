package com.liangweibang.o2o.entity;

import java.util.Date;

public class LocalAuth {
	// id
	private Long LocalAuthId;
	// 用户名
	private String username;
	// 密码
	private String password;
	// 创建时间
	private Date createTime;
	// 更新事件
	private Date updateTime;
	// 关联的用户
	private PersonInfo personInfo;

	public Long getLocalAuthId() {
		return LocalAuthId;
	}

	public void setLocalAuthId(Long localAuthId) {
		LocalAuthId = localAuthId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

}
