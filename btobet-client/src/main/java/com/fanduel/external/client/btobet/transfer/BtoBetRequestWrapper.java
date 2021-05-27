package com.fanduel.external.client.btobet.transfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class BtoBetRequestWrapper<T> {

	@JsonProperty("request")
	T request;

	public BtoBetRequestWrapper( T request ) {
		this.request = request;
	}

	public T getRequest() {
		return this.request;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this ).add( "request", request ).toString();
	}
}
