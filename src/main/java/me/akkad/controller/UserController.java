package me.akkad.controller;

import me.akkad.http.HttpRequest;
import me.akkad.http.HttpStatusCode;
import me.akkad.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final List<User> users = getInitialUsers();
    private static final Pattern userIdRegex = Pattern.compile("/users/(?<userId>\\d+)");


    public static ResponseEntity<List<User>> getUsers(HttpRequest request) {
        log.info("Request to get all users");
        ResponseEntity<List<User>> responseEntity = ResponseEntity.builder(HttpStatusCode.OK, users);
        responseEntity.setStatusCode(HttpStatusCode.OK);
        responseEntity.addHeader("Some-Header", "every thing goes ok :)");
        return responseEntity;
    }

    public static ResponseEntity<User> getUserById(HttpRequest request) {
        Matcher matcher = userIdRegex.matcher(request.getRequestTarget());
        User user = null;
        if (!matcher.find()) {
            int userId = Integer.parseInt(matcher.group("userId"));
            user= users.stream().filter(u -> u.getId() == userId).findAny().orElse(new User());
        }
        return ResponseEntity.builder(HttpStatusCode.OK, user);
    }

    private static List<User> getInitialUsers() {
        return List.of(
                new User(1, "Eren", 18),
                new User(1, "Marli", 18),
                new User(1, "Bejita", 18)
        );
    }
}
