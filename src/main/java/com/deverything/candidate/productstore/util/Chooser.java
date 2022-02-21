package com.deverything.candidate.productstore.util;

import java.util.List;

import com.deverything.candidate.productstore.domainobject.Box;
import com.deverything.candidate.productstore.domainobject.ProductDimensions;

public class Chooser {

	/**
	 * Algorithm to calculate the best box for a product list. The base of the
	 * algorithm is to calculate the total area for all the products in the list of
	 * products and also calculates the area of each box. The best box will be that
	 * complies with the difference between the box area and the total area of
	 * products is the most near to 0 or equal to 0. Differences minor to 0 are not
	 * considered because it represents that the box has not enough space for all
	 * the products received.
	 * 
	 * @param productDimensionsList List that contains all the products will be put
	 *                              into the box
	 * @param boxes                 List that contains all the possible boxes for
	 *                              the products
	 * @return The best box
	 */
	public static int calculateBestBox(List<ProductDimensions> productDimensionsList, List<Box> boxes) {
		int bestBoxId = 0;
		Integer bestBoxArea = 0;
		Integer totalAreaOfProducts = calculateTotalAreaOfProducts(productDimensionsList);
		for (Box box : boxes) {
			Integer boxArea = box.getArea();
			Integer bestBoxAreaAux = boxArea - totalAreaOfProducts;

			if (bestBoxAreaAux == 0) {
				bestBoxId = box.getId();
				bestBoxArea = bestBoxAreaAux;
				break;
			}

			if (bestBoxAreaAux > 0 && (bestBoxArea == 0 || bestBoxAreaAux < bestBoxArea)) {
				bestBoxId = box.getId();
				bestBoxArea = bestBoxAreaAux;
			}

		}
		return bestBoxId;
	}

	private static Integer calculateTotalAreaOfProducts(List<ProductDimensions> productDimensionsList) {
		Integer totalArea = 0;
		for (ProductDimensions productDimensions : productDimensionsList) {
			totalArea += productDimensions.getArea();
		}
		return totalArea;
	}

}
