package com.ivanicob.userservice.util.exceptions;

public class UserInvalidUpdateException extends Exception{

	private static final long serialVersionUID = 4553415750970292199L;

	public UserInvalidUpdateException(){
		super();
	}
	
	public UserInvalidUpdateException(String msg){
		super(msg);
	}
	
	public UserInvalidUpdateException(String msg, Throwable cause){
		super(msg, cause);
	}

}
