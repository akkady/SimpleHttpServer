package me.akkad.http;

import me.akkad.exception.HttpParseException;
import me.akkad.exception.HttpVersionException;

public class HttpRequest extends HttpMessage {
    private String originalHttpVersion;
    private String requestTarget;
    private HttpMethod method;

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }
    public HttpMethod getMethod() {
        return method;
    }
    public String getOriginalHttpVersion() {
        return originalHttpVersion;
    }

    public void setHttpVersion(String originalHttpVersion) throws HttpParseException {
        this.originalHttpVersion = originalHttpVersion;
        try {
            setHttpVersion(HttpVersion.getBestCompatibleVersion(originalHttpVersion));
        } catch (HttpVersionException e) {
            throw new HttpParseException(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED);
        }
    }

    public void setMethod(String method) throws HttpParseException {
        try {
            this.method = HttpMethod.valueOf(method);
        } catch (Exception e) {
            throw new HttpParseException(HttpStatusCode.NOT_IMPLEMENTED);
        }

    }
    public void setMethod(HttpMethod method) {
        this.method = method;
    }
}
