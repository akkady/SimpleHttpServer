package me.akkad.core;

import me.akkad.exception.HttpParseException;
import me.akkad.http.*;
import me.akkad.http.parser.RequestParser;
import me.akkad.http.parser.ResponseParser;
import me.akkad.middleware.Middleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Handler extends Thread {
    private final Logger log = LoggerFactory.getLogger(Handler.class);
    private final Middleware middleware = new Middleware();
    private final Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("Handling request from {}",socket.getInetAddress());
        try {
            HttpRequest request = RequestParser.parseHttpRequest(socket.getInputStream());
            middleware.findCorespendingRoute(request).getHandler().handel(request);
            HttpResponse response = new HttpResponse();
            String html = "<html><head><title>http server</title>"+
                    "</head><body><h1>Server Response :D</h1></body></html>";
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "text/html; charset=utf-8");
            headers.put("Content-Length", String.valueOf(html.getBytes().length));
            response.setHttpVersion(request.getHttpVersion());
            response.setStatusCode(HttpStatusCode.OK.code);
            response.setStatusLiteral(HttpStatusCode.OK.message);
            response.setHeaders(headers);
            response.setBody(html);
            ResponseParser.parseHttpResponse(response,socket.getOutputStream());
            socket.close();
        } catch (IOException | HttpParseException e) {
            throw new RuntimeException(e);
        }
    }
}
