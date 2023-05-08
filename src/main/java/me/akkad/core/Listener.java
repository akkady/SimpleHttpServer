package me.akkad.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread {
    private final Logger log = LoggerFactory.getLogger(Listener.class);
    private String webroot;
    private final ServerSocket serverSocket;

    public Listener(int port, String webroot) throws IOException {
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(port);
    }

    @Override
    public void run() {
        log.info("Start listening ...");
        try {
            while (serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new Handler(socket).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
