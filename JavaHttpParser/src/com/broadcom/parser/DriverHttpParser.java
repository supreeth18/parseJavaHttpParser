package com.broadcom.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DriverHttpParser {
	public static void main(String[] args) throws IOException, Exception {
		JavaHttpParser obj = new JavaHttpParser();
		List<String[]> list = new ArrayList<>();
		String[] array1 = {
				"HTTP/1.0 200 OK\r\n cache-control: public\r\n content-length: 0\r\n content-type: image/svg+xml\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n",
				"HTTP/1.1 302 Found\r\n cache-control: public\r\n Transfer-encoding: chunked\r\n invalid_header\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n",
				"Header1: value1\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n content-length: 1337\r\n" };

		list.add(array1);

		System.out.println(obj.parseRequest(list));

	}
}