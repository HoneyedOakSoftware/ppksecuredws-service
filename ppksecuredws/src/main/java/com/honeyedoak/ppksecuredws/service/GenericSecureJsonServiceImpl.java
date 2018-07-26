package com.honeyedoak.ppksecuredws.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.honeyedoak.ppksecuredws.model.SecuredJson;
import com.honeyedoak.ppksecuredws.model.UnsecuredJson;
import com.honeyedoak.cryptoutils.AsymetricCryptoUtils;
import com.honeyedoak.cryptoutils.SymetricCryptoUtils;
import com.honeyedoak.cryptoutils.exception.CryptoException;

import java.io.IOException;
import java.security.Key;

public class GenericSecureJsonServiceImpl<T> extends AbstractSecureService implements GenericSecureJsonService<T> {

	public GenericSecureJsonServiceImpl(String charset, int oneTimePasswordLength, String keystoreLocation, String keystorePassword, SymmetricCryptoService symmetricCryptoService, AsymmetricCryptoService asymmetricCryptoService) {
		super(charset, oneTimePasswordLength, keystoreLocation, keystorePassword, symmetricCryptoService, asymmetricCryptoService);
	}

	@Override
	public SecuredJson secureJson(T object, Key key) throws CryptoException {
		try {
			return this.secureJson(new ObjectMapper().writerFor(getParameterClass()).writeValueAsString(object), key);
		} catch (JsonProcessingException e) {
			throw new CryptoException(e);
		}
	}

	@Override
	public UnsecuredJson<T> unsecureJsonToObject(SecuredJson securedJson) throws CryptoException {
		try {
			UnsecuredJson<String> unsecuredJson = this.unsecureJson(securedJson);
			return new UnsecuredJson<>(new ObjectMapper().readerFor(getParameterClass()).readValue(unsecuredJson.getObject()), unsecuredJson.getClientKey());
		} catch (IOException e) {
			throw new CryptoException(e);
		}
	}
}
