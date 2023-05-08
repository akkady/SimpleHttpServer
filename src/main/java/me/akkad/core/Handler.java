package me.akkad.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
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
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            int _byte;
            while ((_byte = inputStream.read()) >= 0) {
                System.out.print((char) _byte);
            }
            String html = "<html><head><title>http server</title></head><body><h1>Server Response :D</h1></body></html>";
            final String CRLF = "\n\r";
            String response = "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF +
                    CRLF + html;
            outputStream.write(response.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
