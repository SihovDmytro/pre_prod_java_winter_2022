package com.shop.server.command.impl.http;

import com.shop.entity.Product;
import com.shop.server.command.ServerCommand;
import com.shop.service.AssortmentService;
import com.shop.util.Constants;
import com.shop.util.ParseUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;

public class ReturnProductInfoHTTPCommand implements ServerCommand {
    public static final String NAME = "name";
    public static final String PRICE = "price";
    private AssortmentService assortmentService;
    private static final Logger LOG = LogManager.getLogger(ReturnProductInfoHTTPCommand.class);


    public ReturnProductInfoHTTPCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public String execute(String arg) {
        String response = Constants.CANNOT_EXECUTE_COMMAND;
        try {
            int index = Integer.parseInt(ParseUtil.parseArgumentValue(arg));
            Product product = assortmentService.getProduct(index);
            if (product != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(NAME, product.getName());
                jsonObject.put(PRICE, product.getPrice());
                response = jsonObject.toJSONString();
            }
        } catch (NumberFormatException exception) {
            LOG.error("Cannot parse product index", exception);
        }
        return response;
    }
}
