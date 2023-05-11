package me.akkad.http.parser;

import me.akkad.exception.HttpParseException;
import me.akkad.http.HttpMethod;
import me.akkad.http.HttpRequest;
import me.akkad.http.HttpStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestParser {
    private static final Logger log = LoggerFactory.getLogger(RequestParser.class);

    private RequestParser() {
        throw new IllegalStateException("Utility class should not be initialized");
    }
    public static HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParseException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        HttpRequest request = new HttpRequest();

        parseRequestLine(reader, request);
        parseRequestHeader(reader, request);
        parseRequestBody(reader, request);

        return request;
    }

    private static void parseRequestLine(BufferedReader reader, HttpRequest request) throws IOException, HttpParseException {
        log.info("Parsing status line");

        String requestLine = reader.readLine();
        String[] requestLineElements = requestLine.split(" ");

        if (requestLineElements.length != 3) {
            throw new HttpParseException(HttpStatusCode.BAD_REQUEST);
        }

        request.setMethod(requestLineElements[0]);
        request.setRequestTarget(requestLineElements[1]);
        request.setHttpVersion(requestLineElements[2]);
    }

    private static void parseRequestHeader(BufferedReader reader, HttpRequest request) throws IOException {
        log.info("Parsing Headers block");

        Map<String, String> headers = new HashMap<>();
        String singleHeaderLine;
        while ((singleHeaderLine = reader.readLine()) != null && !singleHeaderLine.isBlank()) {
            String[] headerParts = singleHeaderLine.split(": ");
            headers.put(headerParts[0], headerParts[1]);
        }

        request.setHeaders(headers);
    }

    private static void parseRequestBody(BufferedReader reader, HttpRequest request) throws IOException {
        log.info("Parsing Body block");
        if (request.getMethod() != HttpMethod.POST && request.getMethod() != HttpMethod.PUT) {
            return;
        }
        StringBuilder requestBody = new StringBuilder();
        String bodyLineReader;
        while ((bodyLineReader = reader.readLine()) != null && !bodyLineReader.isBlank()) {
            requestBody.append(bodyLineReader);
        }
        request.setBody(requestBody.toString());
    }
}
