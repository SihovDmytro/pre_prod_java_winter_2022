package com.shop.server.command.impl.tcp;

import com.shop.entity.Product;
import com.shop.server.command.ServerCommand;
import com.shop.service.AssortmentService;
import com.shop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReturnProductInfoCommand implements ServerCommand {
    private AssortmentService assortmentService;
    private static final Logger LOG = LogManager.getLogger(ReturnProductInfoCommand.class);

    public ReturnProductInfoCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public String execute(String arg) {
        String response = Constants.CANNOT_EXECUTE_COMMAND;
        try {
            int index = Integer.parseInt(arg);
            Product product = assortmentService.getProduct(index);
            if (product != null) {
                response = product.getName() + "|" + product.getPrice();
            }

        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse product index", exception);
        }
        return response;
    }
}
