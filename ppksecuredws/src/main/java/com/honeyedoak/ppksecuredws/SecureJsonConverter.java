package com.honeyedoak.ppksecuredws;

import com.honeyedoak.ppksecuredws.model.SecuredJson;
import com.honeyedoak.ppksecuredws.model.UnsecuredJson;
import com.honeyedoak.cryptoutils.exception.CryptoException;

import java.security.Key;

public interface SecureJsonConverter {

	SecuredJson secureJson(String plainJson, Key key) throws CryptoException;

	UnsecuredJson<String> unsecureJson(SecuredJson securedJson) throws CryptoException;
}
