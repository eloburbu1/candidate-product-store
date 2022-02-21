package com.deverything.candidate.productstore;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.deverything.candidate.productstore.dao.ProductStoreDao;
import com.deverything.candidate.productstore.domainobject.Box;
import com.deverything.candidate.productstore.domainobject.BoxesResponse;
import com.deverything.candidate.productstore.domainobject.Checkout;
import com.deverything.candidate.productstore.domainobject.CheckoutSummary;
import com.deverything.candidate.productstore.domainobject.Product;
import com.deverything.candidate.productstore.domainobject.ProductDimensions;
import com.deverything.candidate.productstore.domainobject.ProductsResponse;
import com.deverything.candidate.productstore.exception.ProductStoreServiceException;

@Service
public class ProductStoreServiceImpl implements ApiService<Product, ProductDimensions, Box, Checkout, CheckoutSummary> {

	private static final Logger logger = Logger.getLogger(ProductStoreServiceImpl.class);

	@Autowired
	private ProductStoreDao productStoreDao;

	@Override
	public List<Product> getProducts() throws ProductStoreServiceException {
		List<Product> products = null;
		try {
			ProductsResponse productResponse = productStoreDao.callGetAPI("/products", ProductsResponse.class);
			if (productResponse != null) {
				Integer responseStatusCode = Integer.valueOf(productResponse.getStatusCode());
				if (responseStatusCode == HttpStatus.OK.value()) {
					products = productResponse.getProducts();
				}
			}
		} catch (Exception e) {
			logger.error("Error getting products", e);
			throw new ProductStoreServiceException("Error getting products", e);
		}
		return products;
	}

	@Override
	public ProductDimensions getProductDimensions(int productId) throws ProductStoreServiceException {
		ProductDimensions productDimensions = null;
		try {
			productDimensions = productStoreDao.callGetAPI("/products/" + productId, ProductDimensions.class);
		} catch (Exception e) {
			logger.error("Error getting productDimensions", e);
			throw new ProductStoreServiceException("Error getting productDimensions", e);
		}
		return productDimensions;
	}

	@Override
	public List<Box> getBoxes() throws ProductStoreServiceException {
		List<Box> boxes = null;
		try {
			BoxesResponse boxesResponse = productStoreDao.callGetAPI("/boxes", BoxesResponse.class);
			if (boxesResponse != null) {
				Integer responseStatusCode = Integer.valueOf(boxesResponse.getStatusCode());
				if (responseStatusCode == HttpStatus.OK.value()) {
					boxes = boxesResponse.getBoxes();
				}
			}
		} catch (Exception e) {
			logger.error("Error getting boxes", e);
			throw new ProductStoreServiceException("Error getting boxes", e);
		}
		return boxes;
	}

	@Override
	public CheckoutSummary checkout(Checkout checkout) throws ProductStoreServiceException {
		CheckoutSummary checkoutSummary = null;
		try {
			checkoutSummary = productStoreDao.callPostAPI("/checkout", CheckoutSummary.class, checkout);
		} catch (Exception e) {
			logger.error("Error checking the order", e);
			throw new ProductStoreServiceException("Error checking the order", e);
		}
		return checkoutSummary;
	}

}
