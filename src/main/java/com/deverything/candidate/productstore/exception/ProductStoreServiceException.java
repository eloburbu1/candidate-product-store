package com.deverything.candidate.productstore.exception;

public class ProductStoreServiceException extends Exception {

	public ProductStoreServiceException(String errorMessage, Throwable error) {
		super(errorMessage, error);
	}

}
