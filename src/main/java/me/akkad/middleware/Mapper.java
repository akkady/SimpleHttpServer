package me.akkad.middleware;

import me.akkad.controller.UserController;
import me.akkad.http.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Mapper {
    public final static List<Route> routes = new ArrayList<>();

    public static void addRoute(Route route) {
        routes.add(route);
    }
    static {
        addRoute(new Route(Pattern.compile("^/users$"), HttpMethod.GET, UserController::getUsers));
        addRoute(new Route(Pattern.compile("^/users/\\d+$"), HttpMethod.GET, UserController::getUserById));
    }

}
