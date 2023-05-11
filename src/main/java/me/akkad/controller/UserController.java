package me.akkad.controller;

import me.akkad.http.HttpRequest;
import me.akkad.middleware.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController{
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private static final Map<String, String> users = new HashMap<>(
            Map.of("1", "Marli", "2", "Eren", "3", "Gogo")
    );
    private static final Pattern userIdRegex = Pattern.compile("/users/(?<userId>\\d+)");


    public static List<String> getUsers(HttpRequest request) {
        log.info("Request to get all users");
        return new ArrayList<>(users.values());
    }

    public static String getUserById(HttpRequest request) {
        Matcher matcher = userIdRegex.matcher(request.getRequestTarget());
        if (matcher.find()) {
            String usrKey = matcher.group("userId");
            log.info("Request to get user : {}",usrKey);
            log.info(usrKey+" : "+users.get(usrKey));
            return users.get(usrKey);
        }
        return "";
    }
}
