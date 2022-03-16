package com.shop.command.impl;

import com.shop.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExitCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(ExitCommand.class);

    @Override
    public void execute() {
        LOG.trace("ExitCommand");
    }
}
