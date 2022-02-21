package com.deverything.candidate.productstore.dao;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.deverything.candidate.productstore.exception.ProductStoreDaoException;

@Component
public class ProductStoreDao {

	private static final Logger logger = Logger.getLogger(ProductStoreDao.class);

	private HttpHeaders headers;

	private RestTemplate restTemplate;

	@Value("${productstore.api.baseurl}")
	private String baseURL;

	@Value("${productstore.api.user}")
	private String user;

	@Value("${productstore.api.apikey}")
	private String apikey;

	@Autowired
	public ProductStoreDao() {
		this.restTemplate = new RestTemplate();
	}

	@PostConstruct
	public void postConstruct() {
		this.headers = new HttpHeaders();
		this.headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		this.headers.add("user", user);
		this.headers.add("apikey", apikey);
	}

	/**
	 * Call API using Http GET method
	 * 
	 * @param <T>       Generic class that represents the response type of the
	 *                  calling
	 * @param operation The operation to execute
	 * @param clazz     The class type of the response
	 * @return The response from the get call
	 * @throws ProductStoreDaoException for all the exceptions
	 */
	public <T> T callGetAPI(String operation, Class<T> clazz) throws ProductStoreDaoException {
		logger.info("Getting " + clazz.getSimpleName());
		T returnValue = null;
		String url = baseURL + operation;
		HttpEntity<T> entity = new HttpEntity<T>(this.headers);
		try {
			ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
			returnValue = response.getBody();
		} catch (Exception e) {
			logger.error("Error calling the API " + url, e);
			throw new ProductStoreDaoException("Error calling the API " + url, e);
		}
		return returnValue;
	}

	/**
	 * Call API using Http POST method
	 * 
	 * @param <T>       Generic class that represents the response type of the
	 *                  calling
	 * @param <V>       Generic class that represents the entity type to post
	 * @param operation The operation to execute
	 * @param clazz     The class type of the response
	 * @param body      The entity to post. Can be null
	 * @return The response from the post call
	 * @throws ProductStoreDaoException for all the exceptions
	 */
	public <T, V> T callPostAPI(String operation, Class<T> clazz, @Nullable V body) throws ProductStoreDaoException {
		logger.info("Posting " + clazz.getSimpleName());
		T returnValue = null;
		String url = baseURL + operation;
		HttpEntity<V> entity = new HttpEntity<>(body, this.headers);
		try {
			ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, clazz);
			returnValue = response.getBody();
		} catch (Exception e) {
			logger.error("Error calling the API " + url, e);
			throw new ProductStoreDaoException("Error calling the API " + url, e);
		}
		return returnValue;
	}
}
