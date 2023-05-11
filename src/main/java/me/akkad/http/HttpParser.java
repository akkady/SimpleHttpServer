package me.akkad.http;

import me.akkad.exception.HttpParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {
    private final static Logger log = LoggerFactory.getLogger(HttpParser.class);

    public static void parseHttpResponse(HttpResponse response, OutputStream outputStream) throws IOException {
        final String CRLF = "\r\n";
        final String SP = " ";
        final String H_DELIMITER = " :";
        StringBuilder responseWriter = new StringBuilder();
        // Status line
        responseWriter.append(
                response.getHttpVersion() + SP + response.getStatusCode()+ SP + response.getStatusLiteral() + CRLF
        );
        // Headers
        for (Map.Entry header : response.getHeaders().entrySet()) {
            responseWriter.append(header.getKey() + H_DELIMITER + header.getValue() + CRLF);
        }
        // END HEADERS BLOCK
        responseWriter.append(CRLF);
        // BODY
        responseWriter.append(response.getBody());

        log.info(responseWriter.toString());
        outputStream.write(responseWriter.toString().getBytes());
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
        if (request.getMethod() != HttpMethod.POST && request.getMethod() != HttpMethod.PUT) {
            return;
        }
        StringBuffer requestBody = new StringBuffer();
        String bodyLineReader;
        while ((bodyLineReader = reader.readLine()) != null && !bodyLineReader.isBlank()) {
            requestBody.append(bodyLineReader);
        }
        request.setBody(requestBody.toString());
    }
}
