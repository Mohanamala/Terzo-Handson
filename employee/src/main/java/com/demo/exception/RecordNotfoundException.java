package com.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotfoundException extends RuntimeException {
	  
	private static final long serialVersionUID = 1L;
	private String paramName;
	private String paramValue;
	private String message;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public RecordNotfoundException(String paramName,String paramValue,String message) {
		this.message= message;
		this.paramName = paramName;
		this.paramValue = paramValue;
		
	}
}
