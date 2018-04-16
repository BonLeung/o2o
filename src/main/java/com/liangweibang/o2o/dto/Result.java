package com.liangweibang.o2o.dto;

public class Result<T> {

	private boolean success;
	private T data;
	private String errmsg;
	private int errno;
	
	public Result() {
		
	}
	
	// 成功时的构造器
	public Result(boolean success, T data) {
		this.success = success;
		this.data = data;
	}
	// 失败时的构造器
	public Result(boolean success, int errno, String errmsg) {
		this.success = success;
		this.errno = errno;
		this.errmsg = errmsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public int getErrno() {
		return errno;
	}

	public void setErrno(int errno) {
		this.errno = errno;
	}

}
