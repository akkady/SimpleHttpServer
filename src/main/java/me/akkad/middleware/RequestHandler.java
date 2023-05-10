package me.akkad.middleware;

import me.akkad.http.HttpRequest;

@FunctionalInterface
public interface RequestHandler {
    void handel(HttpRequest request);
}
