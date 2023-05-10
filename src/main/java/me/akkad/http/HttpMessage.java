package me.akkad.http;

import me.akkad.exception.HttpParseException;
import me.akkad.exception.HttpVersionException;

import java.util.Map;

public abstract class HttpMessage {
    private HttpMethod method;
    private String originalHttpVersion;
    private HttpVersion httpVersion;
    private Map<String, String> headers;
    private String body = "";

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(String method) throws HttpParseException {
        try {
            this.method = HttpMethod.valueOf(method);
        } catch (Exception e) {
            throw new HttpParseException(HttpStatusCode.NOT_IMPLEMENTED);
        }

    }

    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public void setHttpVersion(String originalHttpVersion) throws HttpParseException {
        this.originalHttpVersion = originalHttpVersion;
        try {
            this.httpVersion = HttpVersion.getBestCompatibleVersion(originalHttpVersion);
        } catch (HttpVersionException e) {
            throw new HttpParseException(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED);
        }

    }
}
