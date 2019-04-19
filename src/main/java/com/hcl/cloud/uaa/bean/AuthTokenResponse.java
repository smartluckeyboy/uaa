package com.hcl.cloud.uaa.bean;

public class AuthTokenResponse {
	private String userId;

	public AuthTokenResponse() {
	}

	public AuthTokenResponse(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
