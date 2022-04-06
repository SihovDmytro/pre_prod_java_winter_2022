package com.shop.server.factory;

import com.shop.server.clientThread.HTTPClientThread;
import com.shop.server.command.ServerCommand;

import java.net.Socket;
import java.util.Map;

public class HTTPClientThreadFactory implements ClientThreadFactory {
    @Override
    public Thread createClientThread(Socket socket, Map<String, ServerCommand> commandsContainer) {
        return new HTTPClientThread(socket, commandsContainer);
    }
}
