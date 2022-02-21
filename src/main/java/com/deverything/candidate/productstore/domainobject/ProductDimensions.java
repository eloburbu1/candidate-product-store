package com.deverything.candidate.productstore.domainobject;

public class ProductDimensions {

	private String statusCode;
	private Integer width;
	private Integer height;

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public Integer getArea() {
		return this.width * this.height;
	}

}
