package com.shop.util;

import com.shop.server.util.ServerConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {
    public static String parseHTTPRequestCommand(String request) {
        Pattern pattern = Pattern.compile(ServerConstants.HTTP_REQUEST_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(request);
        return matcher.find() ? matcher.group(1) : StringUtils.EMPTY;
    }

    public static String parseHTTPRequestArg(String request) {
        Pattern pattern = Pattern.compile(ServerConstants.HTTP_REQUEST_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(request);
        return matcher.find() ? matcher.group(4) : StringUtils.EMPTY;
    }

    public static String parseTCPRequestCommand(String request) {
        Pattern pattern = Pattern.compile(ServerConstants.TCP_REQUEST_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(request);
        return matcher.find() ? matcher.group(1) : StringUtils.EMPTY;
    }

    public static String parseTCPRequestArg(String request) {
        Pattern pattern = Pattern.compile(ServerConstants.TCP_REQUEST_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(request);
        return matcher.find() ? matcher.group(3) : StringUtils.EMPTY;
    }

    public static String parseArgumentValue(String arg) {
        Pattern pattern = Pattern.compile(ServerConstants.HTTP_REQUEST_ARGUMENT_VALUE_REGEX, Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(arg);
        return matcher.find() ? matcher.group(2) : StringUtils.EMPTY;
    }
}
