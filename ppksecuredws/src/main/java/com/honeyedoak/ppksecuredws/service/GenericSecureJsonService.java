package com.honeyedoak.ppksecuredws.service;

import com.google.common.reflect.TypeToken;
import com.honeyedoak.ppksecuredws.model.SecuredJson;
import com.honeyedoak.ppksecuredws.model.UnsecuredJson;
import com.honeyedoak.cryptoutils.exception.CryptoException;

import java.security.Key;

public interface GenericSecureJsonService<T> extends SecureJsonService {

	default Class<? super T> getParameterClass() {
		final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
		};
		return typeToken.getRawType();
	}

	SecuredJson secureJson(T object, Key key) throws CryptoException;

	UnsecuredJson<T> unsecureJsonToObject(SecuredJson securedJson) throws CryptoException;
}
