package me.akkad.exception;

import me.akkad.http.HttpStatusCode;

public class HttpParseException extends Exception {
    private HttpStatusCode errorStatusCode;

    public HttpParseException(HttpStatusCode errorStatusCode) {
        super(errorStatusCode.message);
        this.errorStatusCode = errorStatusCode;
    }

    public HttpParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpStatusCode getErrorStatusCode() {
        return errorStatusCode;
    }
}
