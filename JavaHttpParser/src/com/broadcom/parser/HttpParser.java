package com.broadcom.parser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HttpParser {

	public static final String HTTP_TAG = "HTTP";
	public static final String HTTP_VERSION = "HTTP version";
	public static final String HTTP_STATUS = "Status";
	public static final String HTTP_VALID_HEADERS = "Number of valid headers";
	public static final String INVALID_STATUS_LINE = "Invalid status line";
	public static final String INVALID_HTTP_HEADER = "Invalid Http Header";

	List<String> parseRequest(List<String[]> list) throws IOException, Exception;

	void validateHttpHeader(String httpHeader) throws Exception;

	void validateHttpResponseProperty(String httpResponseHeaders, Map<String, String> map) throws Exception;
}
