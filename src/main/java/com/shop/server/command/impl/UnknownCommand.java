package com.shop.server.command.impl;

import com.shop.server.command.ServerCommand;
import com.shop.util.Constants;

public class UnknownCommand implements ServerCommand {
    @Override
    public String execute(String arg) {
        return Constants.UNKNOWN_COMMAND;
    }
}
