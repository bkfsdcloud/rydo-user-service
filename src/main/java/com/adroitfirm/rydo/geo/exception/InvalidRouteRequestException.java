package com.adroitfirm.rydo.geo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class InvalidRouteRequestException extends RuntimeException {

	private String message;
	private Throwable throwable;
	/**
	 * 
	 */
	private static final long serialVersionUID = 2317579070717246675L;

	public InvalidRouteRequestException(String message) {
		super(message);
	}
	public InvalidRouteRequestException(Throwable throwable) {
		super(throwable);
	}
}
