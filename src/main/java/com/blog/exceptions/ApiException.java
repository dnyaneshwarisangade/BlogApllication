package com.blog.exceptions;

public class ApiException extends RuntimeException{

	public ApiException(String exception) {
		super(exception);
	}

	public ApiException() {
		super();
	}
}
