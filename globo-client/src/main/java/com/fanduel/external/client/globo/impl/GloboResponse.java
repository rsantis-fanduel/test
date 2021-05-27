package com.fanduel.external.client.globo.impl;

import com.google.common.base.MoreObjects;

public class GloboResponse {
	private int code;
	private String body;

	public int getCode() {
		return code;
	}

	public void setCode( final int code ) {
		this.code = code;
	}

	public String getBody() {
		return body;
	}

	public void setBody( final String body ) {
		this.body = body;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper( this )
				.add( "code", code )
				.add( "body", body )
				.toString();
	}
}
