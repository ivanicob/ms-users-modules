package com.ivanicob.userservice.util.exceptions;

public class UserNotFoundException extends Exception {

	private static final long serialVersionUID = -6583513447605065868L;

	public UserNotFoundException(){
		super();
	}
	
	public UserNotFoundException(String msg){
		super(msg);
	}
	
	public UserNotFoundException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}
