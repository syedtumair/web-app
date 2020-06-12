package com.microservices.report.utils;

import java.util.HashMap;
import java.util.Map;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class HttpUtil {
	private static final OkHttpClient client = new OkHttpClient();

	public static Response doPost(String requestmMediaType, String requestString, String url,
			Map<String, String> requestHeaders) throws Exception {
		Headers headers = null;
		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, requestString);
		if (requestHeaders == null) {
			headers = Headers.of(new HashMap<>());
		} else {
			headers = Headers.of(requestHeaders);
		}
		Request request = new Request.Builder().url(url).post(body).headers(headers).build();
		System.out.println(request.toString());
		Response response = client.newCall(request).execute();
		System.out.println(response.toString());
		// add logger
		return response;

	}

	public static Response doGet(String requestmMediaType, String url, Map<String, String> requestHeaders)
			throws Exception {

		Headers headers = null;
		if (requestHeaders == null) {
			headers = Headers.of(new HashMap<>());
		}
		Request request = new Request.Builder().url(url).get().headers(headers).build();

		System.out.println(url);
		Response response = client.newCall(request).execute();
		// Never Print here as it flushes the response buffer
		// System.out.println(response.body().string());
		return response;

	}

	public static Response doPut(String requestmMediaType, String requestString, String url,
			Map<String, String> requestHeaders) throws Exception {
		Headers headers = null;
		MediaType mediaType = MediaType.parse(requestmMediaType);
		RequestBody body = RequestBody.create(mediaType, requestString);
		if (requestHeaders == null) {
			headers = Headers.of(new HashMap<>());
		} else {
			headers = Headers.of(requestHeaders);
		}
		Request request = new Request.Builder().url(url).put(body).headers(headers).build();
		System.out.println(request.toString());
		Response response = client.newCall(request).execute();
		System.out.println(response.toString());
		// add logger
		return response;

	}

}
