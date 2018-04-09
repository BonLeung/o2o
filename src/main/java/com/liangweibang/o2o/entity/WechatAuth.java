package com.liangweibang.o2o.entity;

import java.util.Date;

public class WechatAuth {
	// id
	private Long wechatAuthId;
	// wechat openid
	private String openId;
	// 创建时间
	private Date createTime;
	// 关联的用户
	private PersonInfo personInfo;

	public long getWechatAuthId() {
		return wechatAuthId;
	}

	public void setWechatAuthId(long wechatAuthId) {
		this.wechatAuthId = wechatAuthId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
}
