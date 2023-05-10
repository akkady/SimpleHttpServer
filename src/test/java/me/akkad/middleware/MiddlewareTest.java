package me.akkad.middleware;

import me.akkad.exception.HttpParseException;
import me.akkad.http.HttpMethod;
import me.akkad.http.HttpRequest;
import me.akkad.http.HttpStatusCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class MiddlewareTest {

    private static Middleware underTest;

    @BeforeAll
    static void beforeAll() {
        underTest = new Middleware();
    }

    @Test
    void findRouteShouldNotThrowWhenRouteExistInMapper() throws HttpParseException {
        // GIVEN
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.GET);
        httpRequest.setRequestTarget("/users/3");
        // ACT
        try {
            underTest.findCorespendingRoute(httpRequest);
        } catch (HttpParseException e) {
            fail(e);
        }
    }
    @Test
    void findRouteShouldThrowWhenRouteNotExistInMapper() throws HttpParseException {
        // GIVEN
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.setMethod(HttpMethod.GET);
        httpRequest.setRequestTarget("/posts");
        // ACT
        try {
            underTest.findCorespendingRoute(httpRequest);
            fail();
        } catch (HttpParseException e) {
            assertEquals(HttpStatusCode.NOT_FOUND,e.getErrorStatusCode());
        }
    }
}