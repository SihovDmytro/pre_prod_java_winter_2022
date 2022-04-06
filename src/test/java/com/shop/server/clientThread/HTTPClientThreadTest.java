package com.shop.server.clientThread;

import com.shop.entity.Product;
import com.shop.server.command.ServerCommand;
import com.shop.server.command.impl.http.ReturnNumberOfProductsHTTPCommand;
import com.shop.server.command.impl.http.ReturnProductInfoHTTPCommand;
import com.shop.server.util.ServerConstants;
import com.shop.service.AssortmentService;
import com.shop.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HTTPClientThreadTest {
    private static HashMap<String, ServerCommand> httpServerCommandContainer;
    private Socket socket;
    private static AssortmentService assortmentService;

    @BeforeAll
    static void beforeAll() {
        assortmentService = mock(AssortmentService.class);
        httpServerCommandContainer = new HashMap<>();
        httpServerCommandContainer.put(ServerConstants.HTTP_COMMAND_GET_COUNT, new ReturnNumberOfProductsHTTPCommand(assortmentService));
        httpServerCommandContainer.put(ServerConstants.HTTP_COMMAND_GET_ITEM, new ReturnProductInfoHTTPCommand(assortmentService));
    }

    @Test
    public void shouldWriteNumberOfProducts() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProductList()).thenReturn(new ArrayList<>(Arrays.asList(new Product(), new Product())));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(("GET /shop/count HTTP/1.1" + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new HTTPClientThread(socket, httpServerCommandContainer);
        thread.start();
        thread.join();

        String expected = String.format(ServerConstants.HTTP_RESPONSE, 37) + String.format(ServerConstants.HTML_PATTERN, "{\"count\":2}");

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void shouldWriteProductInfo() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProduct(1)).thenReturn(new Product(new BigDecimal("13.333"), "test"));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(("GET /shop/item?get_info=1 HTTP/1.1" + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new HTTPClientThread(socket, httpServerCommandContainer);
        thread.start();
        thread.join();

        String expected = String.format(ServerConstants.HTTP_RESPONSE, 56) + String.format(ServerConstants.HTML_PATTERN, "{\"price\":13.333,\"name\":\"test\"}");

        Assertions.assertEquals(expected, outputStream.toString());
    }

    @Test
    public void shouldWriteResponseWhenCommandIsInvalid() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProductList()).thenReturn(new ArrayList<>(Arrays.asList(new Product(), new Product())));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(("get count" + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new HTTPClientThread(socket, httpServerCommandContainer);
        thread.start();
        thread.join();

        String expected = String.format(ServerConstants.HTTP_RESPONSE, 41) + String.format(ServerConstants.HTML_PATTERN, Constants.UNKNOWN_COMMAND);

        Assertions.assertEquals(expected, outputStream.toString());
    }
}