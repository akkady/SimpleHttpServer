package me.akkad.http;

import me.akkad.exception.HttpParseException;
import me.akkad.exception.HttpVersionException;

import java.util.Map;

public abstract class HttpMessage {

    protected HttpVersion httpVersion;
    protected Map<String, String> headers;
    protected String body = "";



    public HttpVersion getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
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




}
