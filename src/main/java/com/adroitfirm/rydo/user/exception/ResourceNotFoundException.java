package com.adroitfirm.rydo.user.exception;

public class ResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2190798139737105094L;

	public ResourceNotFoundException(String message) { super(message); }
}