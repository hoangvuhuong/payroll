package adstech.vn.com.payroll.security;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

	@Autowired
	private Environment env;

	public Long getUserIdFromToken(String token) {
		DecodedJWT decode = JWT.decode(token);

		return decode.getClaim("user_id").asLong();
	}

	public UserPrincipal getUserDetailFromToken(String token) {
		DecodedJWT decode = JWT.decode(token);
		UserPrincipal principal = new UserPrincipal(decode.getClaim("user_id").asLong(),
				decode.getClaim("user_email").asString(), token, Arrays.asList(new SimpleGrantedAuthority("USER")));
		return principal;
	}

	public String getUsernameFromToken(String token) {
		DecodedJWT decode = JWT.decode(token);

		return decode.getClaim("username").asString();
	}

	public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders listHeader = new HttpHeaders();
		listHeader.add("Authorization", "Bearer " + token);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("url.authen") + "permissions");

		HttpEntity<?> entity = new HttpEntity<>(listHeader);
		ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity,
				String.class);
		ObjectMapper mapper = new ObjectMapper();
		List<GrantedAuthority> authorities = new ArrayList<>();
		try {
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				JsonNode temp = mapper.readTree(response.getBody()).get("response");
				if (temp.isArray()) {
					for (final JsonNode objNode : temp) {
						authorities.add(new SimpleGrantedAuthority(objNode.asText()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return authorities;
	}

	public boolean validateToken(String authToken) {
		try {
			Algorithm algorithm = Algorithm.HMAC384(env.getProperty("app.auth.tokenSecret"));
			JWTVerifier verifier = JWT.require(algorithm).build();
			verifier.verify(authToken);
			return true;
		} catch (Exception e) {
			logger.debug("ERR", e);
		}
		return false;
	}

}
