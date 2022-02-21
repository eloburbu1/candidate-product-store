package com.deverything.candidate.productstore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import com.deverything.candidate.productstore.dao.ProductStoreDao;
import com.deverything.candidate.productstore.domainobject.Box;
import com.deverything.candidate.productstore.domainobject.Checkout;
import com.deverything.candidate.productstore.domainobject.CheckoutSummary;
import com.deverything.candidate.productstore.domainobject.Product;
import com.deverything.candidate.productstore.domainobject.ProductDimensions;
import com.deverything.candidate.productstore.util.Chooser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest(classes = { ProductStoreServiceImpl.class, ProductStoreDao.class })
public class ApiServiceTest {

	@Autowired
	private ApiService<Product, ProductDimensions, Box, Checkout, CheckoutSummary> api;

	@Test
	public void testAllTheThings() throws Exception {
		ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

		List<Product> products = api.getProducts();

		List<Product> filteredProductsByPrice = null;
		if (!CollectionUtils.isEmpty(products)) {
			filteredProductsByPrice = (List<Product>) products.stream()
					.filter(product -> product.getPrice() > 300).collect(Collectors.toList());
		}

		List<ProductDimensions> productDimensionsList = new ArrayList<ProductDimensions>();
		productDimensionsList.add(api.getProductDimensions(3));
		productDimensionsList.add(api.getProductDimensions(7));

		List<Box> boxes = api.getBoxes();

		Integer bestBoxId = 0;
		CheckoutSummary checkoutSummary = null;
		if (!CollectionUtils.isEmpty(productDimensionsList) && !CollectionUtils.isEmpty(boxes)) {
			bestBoxId = Chooser.calculateBestBox(productDimensionsList, boxes);
			
			Checkout checkout = new Checkout();
			checkout.setBoxId(bestBoxId);
			checkout.setProductIds(Arrays.asList(3, 7));
			checkoutSummary = api.checkout(checkout);
		}

		System.out.println("Let's get all products from the API:");
		System.out.println(mapper.writeValueAsString(products));

		System.out.println("Let's list all products with a price higher then 300");
		System.out.println(mapper.writeValueAsString(filteredProductsByPrice));

		System.out.println("Let's get product dimensions for products with id 3 and 7");
		System.out.println(mapper.writeValueAsString(productDimensionsList));

		System.out.println("Get all boxes and choose the best one that fits both the products 3 and 7 in a single box");
		System.out.println(mapper.writeValueAsString(boxes));
		System.out.println("Best box id: " + bestBoxId);

		System.out.println("Now we place the order using the checkout in the API");
		System.out.println(mapper.writeValueAsString(checkoutSummary));
	}
}