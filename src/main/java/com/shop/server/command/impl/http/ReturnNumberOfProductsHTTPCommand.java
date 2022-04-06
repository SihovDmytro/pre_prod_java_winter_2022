package com.shop.server.command.impl.http;

import com.shop.server.command.ServerCommand;
import com.shop.service.AssortmentService;
import org.json.simple.JSONObject;

public class ReturnNumberOfProductsHTTPCommand implements ServerCommand {
    public static final String COUNT = "count";
    private AssortmentService assortmentService;

    public ReturnNumberOfProductsHTTPCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public String execute(String arg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(COUNT, assortmentService.getProductList().size());
        return jsonObject.toJSONString();
    }
}
