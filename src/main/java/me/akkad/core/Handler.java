package me.akkad.core;

import me.akkad.exception.HttpParseException;
import me.akkad.http.HttpParser;
import me.akkad.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Handler extends Thread {
    private final Logger log = LoggerFactory.getLogger(Handler.class);
    private final Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        log.info("Handling request from {}",socket.getInetAddress());
        try {
            HttpRequest request = HttpParser.parseHttpRequest(socket.getInputStream());
            OutputStream outputStream = socket.getOutputStream();

            String html = "<html><head><title>http server</title></head><body><h1>Server Response :D</h1></body></html>";
            final String CRLF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF +
                    CRLF + html;
            outputStream.write(response.getBytes());
            socket.close();
        } catch (IOException | HttpParseException e) {
            throw new RuntimeException(e);
        }
    }
}
