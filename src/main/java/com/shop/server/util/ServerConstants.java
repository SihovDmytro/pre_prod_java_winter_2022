package com.shop.server.util;

public class ServerConstants {
    public static final String HTTP_REQUEST_REGEX = "^(GET (/[a-zA-Z\\d \\-_.~]+)+)(\\?([a-zA-Z\\d \\-_.~]+=[a-zA-Z\\d \\-_.~]+))? HTTP/\\d(\\.\\d)?$";
    public static final String TCP_REQUEST_REGEX = "^([A-Za-z\\d_ -]{3,30})(=([A-Za-z\\d_ -]{1,30}))?$";
    public static final String HTTP_REQUEST_ARGUMENT_VALUE_REGEX = "([A-Za-z\\d_ -]{3,30})=([A-Za-z\\d_ -]{1,30})$";

    public static final String HTML_PATTERN = "<html><body>%s</body></html>";
    public static final String HTTP_RESPONSE = "HTTP/1.1 200 OK\n" +
            "Cache-Control:no-cache\n" +
            "Content-Type:text/html; charset=UTF-8\r\n" +
            "Content-Length: %d\r\n" +
            "Connection:close\r\n" +
            "\r\n";

    public static final String HTTP_COMMAND_GET_COUNT = "GET /shop/count";
    public static final String HTTP_COMMAND_GET_ITEM = "GET /shop/item";
    public static final String TCP_COMMAND_GET_COUNT = "get count";
    public static final String TCP_COMMAND_GET_ITEM = "get item";
    public static final int TCP_SERVER_PORT = 3000;
    public static final int HTTP_SERVER_PORT = 3001;
}
