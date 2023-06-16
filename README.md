# parseJavaHttpParser

possible sample Input-Edge-cases for Java-Input-Parser

String[] array1 = {"HTTP/1.0 200 OK\r\n cache-control: public\r\n content-length: 0\r\n content-type: image/svg+xml\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n"};
				
String[] array2 = {"HTTP/1.1 302 Found\r\n cache-control: public\r\n Transfer-encoding: chunked\r\n invalid_header\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n"};

String[] array3 = {"Header1: value1\r\n date: Tue, 22 Jun 2021 22:24:42 GMT\r\n content-length: 1337\r\n"};

output for the above edge cases
[
HTTP version:1.0
Status:200
Number of valid headers:4
Number of invalid headers : 0
, 
HTTP version:1.1
Status:302
Number of valid headers:3
Number of invalid headers : 1
, 
Invalid status line
]