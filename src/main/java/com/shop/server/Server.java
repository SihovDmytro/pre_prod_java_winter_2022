package com.shop.server;

import com.shop.server.command.ServerCommand;
import com.shop.server.factory.ClientThreadFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server extends Thread {
    private static final Logger LOG = LogManager.getLogger(Server.class);
    private int port;
    private Map<String, ServerCommand> commandsContainer;
    private ClientThreadFactory factory;

    public Server(int port, Map<String, ServerCommand> commandsContainer, ClientThreadFactory factory) {
        this.port = port;
        this.commandsContainer = commandsContainer;
        this.factory = factory;
    }

    @Override
    public void run() {
        LOG.debug("Server start");
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!isInterrupted()) {
                try {
                    Socket socket = serverSocket.accept();
                    LOG.debug("New connection!");
                    factory.createClientThread(socket, commandsContainer).start();
                } catch (IOException exception) {
                    LOG.error("Error on tcp server", exception);
                    interrupt();
                }
            }
        } catch (IOException exception) {
            LOG.error("Server error", exception);
        }
        LOG.debug("Server end");
    }
}
