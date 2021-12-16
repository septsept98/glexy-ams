package com.lawencon.glexy.constant;

public enum StatusAssetEnum {

	DEPLOY("SA1", "Deployable"), UNDEPLOY("SA2", "Undeployable"), ARCHIVED("SA3", "Archived"),
	PENDING("SA4", "Pending"), ASSIGN("SA5", "Assign");

	private String code;
	private String name;

	StatusAssetEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
