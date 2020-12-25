package flightTicketsShopUI.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HttpUtils {

	private static String TOKEN;
	private static boolean IS_ADMIN;
	private static String USER_NAME;

	public static ResponseEntity<String> sendGetReturnString(String url, String token) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		return response;
	}

	public static ResponseEntity<Integer> sendGetReturnInteger(String url, String token) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.GET, entity, Integer.class);

		return response;
	}

	public static ResponseEntity<Object> sendGetReturnObject(String url, String token) {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		if (!token.isEmpty())
			headers.add("Authorization", token);
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);

		return response;
	}

	public static ResponseEntity<String> sendPostReturnString(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		return response;
	}

	public static ResponseEntity<Integer> sendPostReturnInteger(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.POST, entity, Integer.class);

		return response;
	}

	public static ResponseEntity<Object> sendPostReturnObject(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);

		return response;
	}

	public static ResponseEntity<String> sendPutReturnString(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

		return response;
	}

	public static ResponseEntity<Integer> sendPutReturnInteger(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Integer> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);

		return response;
	}

	public static ResponseEntity<Object> sendPutReturnObject(String url, Object body, String token)
			throws RestClientException {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		if (!token.isEmpty())
			headers.add("Authorization", token);

		HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);

		ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);

		return response;
	}

	public static String getTOKEN() {
		return TOKEN;
	}

	public static void setTOKEN(String tOKEN) {
		TOKEN = tOKEN;
	}

	public static boolean isIS_ADMIN() {
		return IS_ADMIN;
	}

	public static void setIS_ADMIN(boolean iS_ADMIN) {
		IS_ADMIN = iS_ADMIN;
	}

	public static String getUSER_NAME() {
		return USER_NAME;
	}

	public static void setUSER_NAME(String uSER_NAME) {
		USER_NAME = uSER_NAME;
	}

}
