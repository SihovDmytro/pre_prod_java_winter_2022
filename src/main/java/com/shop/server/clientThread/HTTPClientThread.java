package com.shop.server.clientThread;

import com.shop.server.command.ServerCommand;
import com.shop.server.command.impl.UnknownCommand;
import com.shop.server.util.ServerConstants;
import com.shop.util.ParseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class HTTPClientThread extends Thread {
    private Socket socket;
    private Map<String, ServerCommand> commandsContainer;
    private static final Logger LOG = LogManager.getLogger(HTTPClientThread.class);

    public HTTPClientThread(Socket socket, Map<String, ServerCommand> commandsContainer) {
        this.socket = socket;
        this.commandsContainer = commandsContainer;
    }

    @Override
    public void run() {
        LOG.debug("HTTPClientThread start");
        String response = new UnknownCommand().execute(null);
        try (OutputStream outputStream = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String request = reader.readLine();
            LOG.debug("request: " + request);
            String command = ParseUtil.parseHTTPRequestCommand(request);
            LOG.debug("command: " + command);
            if (StringUtils.isNotEmpty(command)) {
                LOG.debug("request matches the pattern");
                String arg = ParseUtil.parseHTTPRequestArg(request);
                LOG.debug("arg: " + arg);
                ServerCommand serverCommand = commandsContainer.getOrDefault(command, new UnknownCommand());
                LOG.debug("serverCommand: " + serverCommand);
                response = serverCommand.execute(arg);
            }
            LOG.debug("response: " + response);
            writeResponse(response, outputStream);
        } catch (IOException exception) {
            LOG.error("Cannot execute query", exception);
        } finally {
            try {
                socket.close();
            } catch (IOException | NullPointerException exception) {
                LOG.error("Cannot close socket", exception);
            }
        }
        LOG.debug("HTTPClientThread end");
    }

    private void writeResponse(String serverResponse, OutputStream outputStream) throws IOException {
        serverResponse = String.format(ServerConstants.HTML_PATTERN, serverResponse);
        String httpResponse = String.format(ServerConstants.HTTP_RESPONSE, serverResponse.length());
        String result = httpResponse + serverResponse;
        LOG.trace("result: " + result);
        outputStream.write(result.getBytes());
        outputStream.flush();
    }
}
