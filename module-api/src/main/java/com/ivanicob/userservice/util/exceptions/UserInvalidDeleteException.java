package com.ivanicob.userservice.util.exceptions;

public class UserInvalidDeleteException extends Exception{

	private static final long serialVersionUID = -2647079167051444013L;

	public UserInvalidDeleteException(){
		super();
	}
	
	public UserInvalidDeleteException(String msg){
		super(msg);
	}
	
	public UserInvalidDeleteException(String msg, Throwable cause){
		super(msg, cause);
	}

}
