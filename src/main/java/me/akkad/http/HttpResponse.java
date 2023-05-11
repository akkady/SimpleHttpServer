package me.akkad.http;

public class HttpResponse extends HttpMessage {
    private int statusCode; // 200, 201, 400, 500 ...
    private String statusLiteral; // OK, Created, Not found ...
    private String errorMessage;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLiteral() {
        return statusLiteral;
    }

    public void setStatusLiteral(String statusLiteral) {
        this.statusLiteral = statusLiteral;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getHttpVersionLiteral() {
        return getHttpVersion().literal;
    }
}
