package com.broadcom.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaHttpParser implements HttpParser {

	private String httpResponse;
	private int countOfInvalidHeaders = 0;
	private boolean invalidHeaderFlag = false;

	private String httpVersion;
	private int status;
	private int validHeaders;

	/*
	 * This is the method which extracts the valid/invalid headers info from the map
	 * & processes
	 */
	public List<String> parseRequest(List<String[]> list) throws IOException, Exception {
		List<String> result = new ArrayList<>();
		for (String[] s : list) {
			for (String st : s) {
				/* Start reading the http response */
				BufferedReader reader = new BufferedReader(new StringReader(st));

				/*
				 * usually the HTTP response should be terminated with a CR LF sequence, so
				 * start reading line by line
				 */
				validateHttpHeader(reader.readLine());

				/* if the http response doesnt contain any HTTP tag that means its invalid */
				if (!httpResponse.contains(HTTP_TAG)) {
					result.add("\r\n" + INVALID_STATUS_LINE + "\r\n");
					// System.out.println(" Invalid status line " + "\r\n");

					/*
					 * if http response contains HTTP tag then we must further check for
					 * valid/invalid headers
					 */
				} else if (httpResponse.contains(HTTP_TAG)) {
					Map<String, String> map = new HashMap<>();
					String header = reader.readLine();
					while (header != null && header.length() > 0) {
						/*
						 * this method appendHttpHeaders will push all valid headers info into the map
						 */
						validateHttpResponseProperty(header, map);
						header = reader.readLine();
					}

					String[] array = httpResponse.split(" ");

					int index_of_httpVersion = array[0].indexOf('/');

					/*
					 * Extract the information needed to print i,e httpVersion, status,
					 * validHeaders/inValidHeaders size
					 */
					httpVersion = array[0].substring(index_of_httpVersion + 1);
					status = Integer.parseInt(array[1]);
					validHeaders = map.size();

					/*
					 * check if A header line consists of a header name, followed by ":", followed
					 * by the header value
					 */
					if (invalidHeaderFlag) {

						result.add("\r\n" + HTTP_VERSION + ":" + httpVersion + "\r\n" + HTTP_STATUS + ":" + status
								+ "\r\n" + HTTP_VALID_HEADERS + ":" + validHeaders + "\r\n" + HTTP_INVALID_HEADERS + ":"
								+ countOfInvalidHeaders + "\r\n");

					} else {

						result.add("\r\n" + HTTP_VERSION + ":" + httpVersion + "\r\n" + HTTP_STATUS + ":" + status
								+ "\r\n" + HTTP_VALID_HEADERS + ":" + validHeaders + "\r\n" + HTTP_INVALID_HEADERS + ":"
								+ 0 + "\r\n");
					}
				}
				reader.close();
			}
		}
		return result;
	}

	/* This method will check if the http-header is null/empty */
	public void validateHttpHeader(String httpHeader) throws Exception {
		if (httpHeader == null || httpHeader.length() == 0) {
			throw new Exception(INVALID_HTTP_HEADER + httpHeader);
		}
		httpResponse = httpHeader;
	}

	/*
	 * This method will check if http response contains any invalid headers else
	 * will validate the http response properties into the map for processing
	 */
	public void validateHttpResponseProperty(String httpResponseHeaders, Map<String, String> map) throws Exception {

		int index = httpResponseHeaders.indexOf(":");
		if (index == -1) {
			invalidHeaderFlag = true;
			countOfInvalidHeaders++;
		} else {
			map.put(httpResponseHeaders.substring(0, index),
					httpResponseHeaders.substring(index + 1, httpResponseHeaders.length()));
		}

	}
}
