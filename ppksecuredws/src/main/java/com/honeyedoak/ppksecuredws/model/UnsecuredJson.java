package com.honeyedoak.ppksecuredws.model;

import lombok.*;

import java.security.PublicKey;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UnsecuredJson<T> {

	private T object;

	private PublicKey clientKey;
}
