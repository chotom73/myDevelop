package com.chat.exception;

/**
 * 
 * @author Sergi Almar
 */
public class TooMuchProfanityException extends RuntimeException {

	public TooMuchProfanityException(String message) {
		super(message);
	}
}
