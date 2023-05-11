package me.akkad.http;

public enum HttpStatusCode {
    /* SUCCESS CODES */
    OK(200, "OK"),
    CREATED(201, "Created"),
    /* CLIENT ERRORS */
    BAD_REQUEST(400, "Bad request"),
    NOT_FOUND(404, "Not found"),
    METHOD_NOT_ALLOWED(401, "Methode not allowed"),
    BAD_REQUEST_URI(414, "Bad request, uri too long"),
    /* SERVER ERRORS */
    INTERNAL_SERVER_ERROR(500, "Internal server error"),
    NOT_IMPLEMENTED(504, "Methode not implemented"),
    HTTP_VERSION_NOT_SUPPORTED(505 ,"HTTP Version Not Supported");
    public final int code;
    public final String message;

    HttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
