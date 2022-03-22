package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.service.AssortmentService;
import com.shop.util.Serializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExitCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(ExitCommand.class);
    private AssortmentService assortmentService;

    public ExitCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public void execute() {
        LOG.trace("ExitCommand");
        Serializer.serializeProducts(assortmentService.getProductList());
    }
}
