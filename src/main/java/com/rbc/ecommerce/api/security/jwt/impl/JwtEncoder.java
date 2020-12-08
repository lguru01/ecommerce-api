package com.rbc.ecommerce.api.security.jwt.impl;

import java.security.SecureRandom;
import java.text.ParseException;
import java.time.Clock;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEDecrypter;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.DirectDecrypter;
import com.nimbusds.jose.crypto.DirectEncrypter;
import com.nimbusds.jwt.JWTClaimsSet;
import com.rbc.ecommerce.api.security.jwt.IJwtEncoder;
import com.rbc.ecommerce.exception.AuthenticationException;

public class JwtEncoder implements IJwtEncoder {
	private static final Logger logger = LoggerFactory.getLogger(JwtEncoder.class);
	
	private final Clock clock;
	private final JWEHeader jweHeader;
	private final JWEEncrypter encrypter;
	private final JWEDecrypter decrypter;

	public JwtEncoder(Params params, String encryptionKey, SecureRandom random, Clock clock) {
		this.clock = clock;
		
		byte[] decodedKey = Base64.getDecoder().decode(encryptionKey);
		SecretKey key = new SecretKeySpec(decodedKey, 0, decodedKey.length, params.getEncryptionKeyType());
		
		try {
			this.encrypter = new DirectEncrypter(key);
			this.encrypter.getJCAContext().setSecureRandom(random);
			this.decrypter = new DirectDecrypter(key);
		} catch (KeyLengthException e) {
			throw new IllegalStateException("Error creating JwtEncoder and Decoder "+e.getMessage());
		}
		
		JWEAlgorithm alg = JWEAlgorithm.parse(params.getEncryptionAlgorithm());
		EncryptionMethod method = EncryptionMethod.parse(params.getEncryptionMethod());
		this.jweHeader = new JWEHeader(alg, method);
		
		logger.info("Using {} algorithm, {} method with key length {} of type {}", 
				alg, method, decodedKey.length, key.getAlgorithm());
	}

	@Override
	public String encode(String issuer, Map<String, Object> claims) throws AuthenticationException {
		JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
		builder.issueTime(Date.from(clock.instant()));
		builder.issuer(issuer);
		
		for(Map.Entry<String, Object> map : claims.entrySet()) {
			builder.claim(map.getKey(), map.getValue());
		}
		
		JWTClaimsSet claimSet = builder.build();
		
		Payload payload = new Payload(claimSet.toJSONObject());
		logger.debug(payload.toString());
		
		JWEObject jweObject = new JWEObject(jweHeader, payload);
		
		try {
			jweObject.encrypt(encrypter);
		} catch (JOSEException e) {
			throw new AuthenticationException("JWT token encrypt error", e);
		}
		
		
		return jweObject.serialize();
	}

	@Override
	public Map<String, Object> decode(String data) throws AuthenticationException {
		try {
			JWEObject jweObject = JWEObject.parse(data);
			jweObject.decrypt(decrypter);
			JWTClaimsSet claimset = JWTClaimsSet.parse(jweObject.getPayload().toJSONObject());
			
			if((!jweHeader.getAlgorithm().equals(jweObject.getHeader().getAlgorithm())) || 
					(!jweHeader.getEncryptionMethod().equals(jweObject.getHeader().getEncryptionMethod()))) {
				throw new AuthenticationException("JWT token algorithm or method mismatch");
			}
			
			Instant issueTime = Optional.ofNullable(claimset.getIssueTime()).map(Date::toInstant).orElse(null);
			if(issueTime == null) {
				throw new AuthenticationException("JWT token issuetime is null");
			}
			
			return claimset.getClaims();
		} catch (ParseException e) {
			throw new AuthenticationException("JWT token parse error ", e);
		} catch (JOSEException e) {
			throw new AuthenticationException("JWT token decrypt error ", e);
		}	
	}

	public static class Params {
		private String encryptionKeyType;
		private String encryptionMethod;
		private String encryptionAlgorithm;

		public void setEncryptionKeyType(String encryptionKeyType) {
			this.encryptionKeyType = encryptionKeyType;
		}

		public void setEncryptionMethod(String encryptionMethod) {
			this.encryptionMethod = encryptionMethod;
		}

		public void setEncryptionAlgorithm(String encryptionAlgorithm) {
			this.encryptionAlgorithm = encryptionAlgorithm;
		}

		public String getEncryptionKeyType() {
			return encryptionKeyType;
		}

		public String getEncryptionMethod() {
			return encryptionMethod;
		}

		public String getEncryptionAlgorithm() {
			return encryptionAlgorithm;
		}

	}

}
