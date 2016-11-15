package com.zhiyin.manager.web.entity;

public class LoginFailException extends RuntimeException {

	public LoginFailException() {
	}

	public LoginFailException(String message) {
		super(message);
	}
}
