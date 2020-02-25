package adstech.vn.com.payroll.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RestUtil {
	public static JsonNode getRequest(String url, Map<String, String> headers,
			Map<String, String> requestParams) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders listHeader = new HttpHeaders();
		listHeader.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		if (headers != null) {
			headers.forEach((k, v) -> {
				listHeader.set(k, v);
			});
		}

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);

		if (requestParams != null) {
			requestParams.forEach((k, v) -> {
				builder.queryParam(k, v);
			});
		}

		HttpEntity<?> entity = new HttpEntity<>(listHeader);

		try {
			HttpEntity<String> response = restTemplate.exchange(URLDecoder.decode(builder.toUriString(), "UTF-8"),
					HttpMethod.GET, entity, String.class);

			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree(response.getBody());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void postMethod(String url, HashMap<String, String> headers, Object data) {

		try {
			RestTemplate restTemplate = new RestTemplate();
			URI uri;
			uri = new URI(url);

			HttpHeaders listHeader = new HttpHeaders();
			listHeader.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			if (headers != null) {
				headers.forEach((k, v) -> {
					listHeader.set(k, v);
				});
			}

			HttpEntity<Object> request = new HttpEntity<>(data, listHeader);
			restTemplate.postForEntity(uri, request, String.class);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
