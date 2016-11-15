package com.zhiyin.manager.web.entity;

/**
 * RepeatRegisterException
 * 
 */
public class RepeatRegisterException extends RuntimeException {

	private static final long serialVersionUID = 8096927982277821689L;

	public RepeatRegisterException() {
	}

	public RepeatRegisterException(String message) {
		super(message);
	}
}
