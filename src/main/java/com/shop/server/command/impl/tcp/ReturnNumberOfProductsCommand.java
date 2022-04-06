package com.shop.server.command.impl.tcp;

import com.shop.server.command.ServerCommand;
import com.shop.service.AssortmentService;

public class ReturnNumberOfProductsCommand implements ServerCommand {
    private AssortmentService assortmentService;

    public ReturnNumberOfProductsCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public String execute(String arg) {
        return String.valueOf(assortmentService.getProductList().size());
    }
}
