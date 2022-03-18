package com.shop.command.impl;

import com.shop.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(DefaultCommand.class);

    @Override
    public void execute() {
        LOG.debug("Default case");
        System.out.println("Unknown operation.");
    }
}
