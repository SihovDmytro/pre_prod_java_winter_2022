package com.shop.server.factory;

import com.shop.server.command.ServerCommand;

import java.net.Socket;
import java.util.Map;

public interface ClientThreadFactory {
    Thread createClientThread(Socket socket, Map<String, ServerCommand> commandsContainer);
}
