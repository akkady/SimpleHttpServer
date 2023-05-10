package me.akkad.exception;

public class HttpVersionException extends Exception {
    public HttpVersionException() {
        super("Http version not supported");
    }

    public HttpVersionException(String message) {
        super(message);
    }
}
