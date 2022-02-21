package com.deverything.candidate.productstore.domainobject;

import java.util.List;

public class Checkout {

	private Integer boxId;
	private List<Integer> productIds;

	public Integer getBoxId() {
		return boxId;
	}

	public void setBoxId(Integer boxId) {
		this.boxId = boxId;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

}
