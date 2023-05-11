package me.akkad.middleware;

import me.akkad.http.HttpRequest;
import me.akkad.http.ResponseEntity;

@FunctionalInterface
public interface RequestHandler {
    ResponseEntity<?> handel(HttpRequest request);
}
