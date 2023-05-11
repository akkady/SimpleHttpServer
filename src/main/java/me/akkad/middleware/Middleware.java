package me.akkad.middleware;

import me.akkad.exception.HttpParseException;
import me.akkad.http.HttpRequest;
import me.akkad.http.HttpStatusCode;

public class Middleware {

    public Route findCorespendingRoute(HttpRequest request) throws HttpParseException {
        for (Route rote : Mapper.routes) {
            if (rote.isMatching(request.getRequestTarget(),request.getMethod())) {
                return rote;
            }
        }
        throw new HttpParseException(HttpStatusCode.NOT_FOUND);
    }
}
