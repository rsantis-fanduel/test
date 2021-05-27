package com.fanduel.external.client.globo.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Stream;

import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

public class URLEncoder {
	private static final Logger LOGGER = LoggerFactory.getLogger( URLEncoder.class );
	public static final String TARGET_ENCODING = "utf-8";

	public String encode( final List<BasicNameValuePair> form ) {
		final Stream<String> encodedForm = form.stream()
				.filter( nameValue -> nameValue.getName() != null && nameValue.getValue() != null )
				.map( nameValue -> {
					try {
						return nameValue.getName() + "=" + java.net.URLEncoder.encode(
								nameValue.getValue(), TARGET_ENCODING );
					} catch ( UnsupportedEncodingException e ) {
						// swallow
						LOGGER.warn( "Problem encoding {}={}: {}", nameValue.getName(),
								nameValue.getValue(), e );
						return "";
					}
				} );
		return Joiner.on( "&" ).join( encodedForm.iterator() );
	}
}
