package com.lawencon.glexy.constant;

public enum MessageEnum {
	
	SUCCESS("Success"), FAILED("Failed"), CREATED("Created"), UPDATED("Updated");

	private String msg;

	MessageEnum(String msg) {
		this.msg = msg;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
