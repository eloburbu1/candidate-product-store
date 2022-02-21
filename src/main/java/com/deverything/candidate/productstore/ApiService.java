package com.deverything.candidate.productstore;

import java.util.List;

import com.deverything.candidate.productstore.exception.ProductStoreServiceException;

public interface ApiService<ProductObject, ProductDimensionsObject, BoxObject, CheckoutObject, CheckoutSummaryObject> {

	/**
	 * Should get a json list of products from API
	 * 
	 * @return A product list
	 * @throws ProductStoreServiceException
	 */
	public List<ProductObject> getProducts() throws ProductStoreServiceException;

	/**
	 * Should get a json object back with width and heigh for a given productId
	 * 
	 * @param productId The product id
	 * @return The product dimensions of the product sendt as parameter
	 * @throws ProductStoreServiceException
	 */
	public ProductDimensionsObject getProductDimensions(int productId) throws ProductStoreServiceException;

	/**
	 * Should get a json object with a list of boxes from the API
	 * 
	 * @return A box list
	 * @throws ProductStoreServiceException
	 */
	public List<BoxObject> getBoxes() throws ProductStoreServiceException;

	/**
	 * Performs the checkout
	 * 
	 * @param checkout json object that contains the boxId and the list of products
	 *                 to checkout
	 * @return The checkout summary object
	 * @throws ProductStoreServiceException
	 */
	public CheckoutSummaryObject checkout(CheckoutObject checkout) throws ProductStoreServiceException;

}
