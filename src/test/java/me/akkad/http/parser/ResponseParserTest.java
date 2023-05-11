package me.akkad.http.parser;

import me.akkad.http.HttpResponse;
import me.akkad.http.HttpStatusCode;
import me.akkad.http.HttpVersion;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ResponseParserTest {

    @Test
    void parseHttpResponseShouldExactMatch() throws IOException {
        // GIVEN
        HttpResponse response = generateHttpResponseObject4Test1();
        OutputStream outputStream = new ByteArrayOutputStream();
        // ACT
        ResponseParser.parseHttpResponse(response, outputStream);
        String expected = "HTTP/1.1 200 OK\r\nContent-Length: 92\r\n" +
                "Content-Type: text/html; charset=utf-8\r\n\r\n" +
                "<html><head><title>http server</title></head><body><h1>Server Response :D</h1></body></html>\r\n";
        assertEquals(expected,outputStream.toString());
    }

    private static HttpResponse generateHttpResponseObject4Test1() {
        HttpResponse response = new HttpResponse();
        String html = "<html><head><title>http server</title>"+
                "</head><body><h1>Server Response :D</h1></body></html>";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html; charset=utf-8");
        headers.put("Content-Length", String.valueOf(html.getBytes().length));
        response.setHttpVersion(HttpVersion.HTTP_1_1);
        response.setStatusCode(HttpStatusCode.OK.code);
        response.setStatusLiteral(HttpStatusCode.OK.message);
        response.setHeaders(headers);
        response.setBody(html);
        return response;
    }


}