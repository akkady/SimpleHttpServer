package me.akkad.http;

public class HttpRequest extends HttpMessage {
    private String requestTarget;

    public String getRequestTarget() {
        return requestTarget;
    }

    public void setRequestTarget(String requestTarget) {
        this.requestTarget = requestTarget;
    }
}
