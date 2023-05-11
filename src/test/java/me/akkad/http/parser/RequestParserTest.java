package me.akkad.http.parser;

import me.akkad.exception.HttpParseException;
import me.akkad.http.HttpMethod;
import me.akkad.http.HttpRequest;
import me.akkad.http.HttpStatusCode;
import me.akkad.http.HttpVersion;
import me.akkad.http.parser.RequestParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;


class RequestParserTest {

    @Test
    void parseHttpRequestLine() throws IOException {
        // Given
        String rowData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8083\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        HttpRequest request = null;
        // ACT
        try {
            request = RequestParser.parseHttpRequest(inputStream);
        } catch (HttpParseException e) {
            fail(e);
        }

        // Verify
        Assertions.assertEquals(HttpMethod.GET, request.getMethod());
        assertEquals("/", request.getRequestTarget());
        Assertions.assertEquals(HttpVersion.HTTP_1_1, request.getHttpVersion());
    }

    @Test
    void parseBadeMethodName() {
        // Given
        String rowData = "GEEYYY / HTTP/1.1\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // Verify
        assertThrows(HttpParseException.class, () -> RequestParser.parseHttpRequest(inputStream));
    }

    @Test
    void parseBadeRequest() throws IOException {
        // Given
        String rowData = "GET / NOT_VALID_HERE HTTP/1.1\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // Verify
        try {
            RequestParser.parseHttpRequest(inputStream);
            fail();
        } catch (HttpParseException e) {
            Assertions.assertEquals(e.getErrorStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseWithEmptyRequestLine() throws IOException {
        // Given
        String rowData = "\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // Verify
        try {
            RequestParser.parseHttpRequest(inputStream);
            fail();
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseRequestLineWithoutCRorLF() throws IOException {
        // Given
        String rowData = "\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // Verify
        try {
            RequestParser.parseHttpRequest(inputStream);
            fail();
        } catch (HttpParseException e) {
            assertEquals(e.getErrorStatusCode(), HttpStatusCode.BAD_REQUEST);
        }
    }

    @Test
    void parseRequestLineWithBadHttpVersionShouldThrow() throws IOException {
        // Given
        String rowData = "GET / HTP/3.1\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // ACT
        try {
            RequestParser.parseHttpRequest(inputStream);
            fail();
        } catch (HttpParseException e) {
            // Verify
            assertEquals(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED, e.getErrorStatusCode());
        }
    }

    @Test
    void parseRequestLineWithSupportedHttpVersionShouldNotThrow() throws IOException {
        // Given
        String rowData = "GET / HTTP/1.1\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // ACT
        try {
            RequestParser.parseHttpRequest(inputStream);
        } catch (HttpParseException e) {
            fail(e);
        }
    }

    @Test
    void parseHeadersShouldHaveSameHeadersSize() throws IOException {
        // GIVEN
        String rowData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8083\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        HttpRequest request = null;
        // ACT
        try {
            request = RequestParser.parseHttpRequest(inputStream);
        } catch (HttpParseException e) {
            fail(e);
        }
        // VERIFY
        assertEquals(request.getHeaders().size(), 3);
    }

    @Test
    void parseHeadersShouldHaveSameElements() throws IOException {
        // GIVEN
        String rowData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8083\r\n" +
                "Connection: keep-alive\r\n" +
                "Cache-Control: max-age=0\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        HttpRequest request = null;
        // ACT
        try {
            request = RequestParser.parseHttpRequest(inputStream);
        } catch (HttpParseException e) {
            fail(e);
        }
        // VERIFY
        assertEquals("localhost:8083", request.getHeaders().get("Host"));
        assertEquals("keep-alive", request.getHeaders().get("Connection"));
        assertEquals("max-age=0", request.getHeaders().get("Cache-Control"));
    }

    @Test
    void parseRequestBodyShouldReturnTheCorrectBodyWithPOSTorPUT() throws IOException {
        // GIVEN
        String rowData = "PUT / HTTP/1.1\r\n" +
                "Host: localhost:8083\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n" +  // blank line to finish headers block and start body block
                "{\"firstname\":\"younes\",\r\n" +
                "\"lastname\":\"akkad\"}\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // ACT
        try {
            String result = RequestParser.parseHttpRequest(inputStream).getBody();
            String expected = "{\"firstname\":\"younes\",\"lastname\":\"akkad\"}";
            assertEquals(expected,result);
        } catch (HttpParseException e) {
            fail(e);
        }
    }
    @Test
    void parseRequestBodyShouldBeEmptyWithoutPOSTorPUT() throws IOException {
        // GIVEN
        String rowData = "GET / HTTP/1.1\r\n" +
                "Host: localhost:8083\r\n" +
                "Connection: keep-alive\r\n" +
                "\r\n" +  // blank line to finish headers block and start body block
                "{\"firstname\":\"younes\",\r\n" +
                "\"lastname\":\"akkad\"}\r\n";
        InputStream inputStream = generateGivenStreamToTest(rowData);
        // ACT
        try {
            String result = RequestParser.parseHttpRequest(inputStream).getBody();
            assertEquals(result,"");
        } catch (HttpParseException e) {
            fail(e);
        }
    }

    private InputStream generateGivenStreamToTest(String rowData) {
        return new ByteArrayInputStream(rowData.getBytes(StandardCharsets.UTF_8));
    }


}