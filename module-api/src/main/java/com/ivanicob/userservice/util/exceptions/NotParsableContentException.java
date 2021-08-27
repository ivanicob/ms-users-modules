package com.ivanicob.userservice.util.exceptions;

public class NotParsableContentException extends Exception{

	private static final long serialVersionUID = -775115640580026702L;

	public NotParsableContentException(String msg){
		super(msg);
	}
	
	public NotParsableContentException(String msg, Throwable cause){
		super(msg, cause);
	}

}
