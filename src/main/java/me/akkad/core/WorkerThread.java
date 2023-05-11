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

public class WorkerThread extends Thread {
    private final Logger log = LoggerFactory.getLogger(WorkerThread.class);
    private final Middleware middleware = new Middleware();
    private final Socket socket;

    public WorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("Handling request from {}",socket.getInetAddress());
        try {
            handelRequest();
        } catch (IOException e) {
            try {
                sendResponseWhenFail(HttpStatusCode.INTERNAL_SERVER_ERROR,"Server error");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handelRequest() throws IOException {
        try {
            HttpRequest request = RequestParser.parseHttpRequest(socket.getInputStream());
            ResponseEntity<?> response = middleware.findCorespendingRoute(request).getHandler().handel(request);
            sendResponseWhenSuccess(request, response);
        } catch (HttpParseException exception) {
            sendResponseWhenFail(exception.getErrorStatusCode(), exception.getMessage());
        } catch (RuntimeException e) {
            sendResponseWhenFail(HttpStatusCode.INTERNAL_SERVER_ERROR, "Server error");
        }
    }


    private void sendResponseWhenSuccess(HttpRequest request, ResponseEntity<?> responseEntity) throws IOException {
        HttpResponse response = new HttpResponse();
        response.setHttpVersion(request.getHttpVersion());
        response.setStatusCode(responseEntity.getStatusCode().code);
        response.setStatusLiteral(responseEntity.getStatusCode().message);
        response.setHeaders(responseEntity.getHeaders());
        response.setBody(responseEntity.stringifiedResponseBody());
        ResponseParser.parseHttpResponse(response,socket.getOutputStream());
    }

    private void sendResponseWhenFail(HttpStatusCode status,String errMsg) throws IOException {
        Map<String, String> headers = new HashMap<>();
        headers.put("X-Error-Message", errMsg);
        HttpResponse response = new HttpResponse();
        response.setHttpVersion(HttpVersion.HTTP_1_1);
        response.setStatusCode(status.code);
        response.setStatusLiteral(status.message);
        response.setHeaders(headers);
        ResponseParser.parseHttpResponse(response,socket.getOutputStream());
    }

}
