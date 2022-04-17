package com.shop.server.clientThread;

import com.shop.entity.Product;
import com.shop.server.command.ServerCommand;
import com.shop.server.command.impl.tcp.ReturnNumberOfProductsCommand;
import com.shop.server.command.impl.tcp.ReturnProductInfoCommand;
import com.shop.server.util.ServerConstants;
import com.shop.service.AssortmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

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

class TCPClientThreadTest {
    private static HashMap<String, ServerCommand> TCPServerCommandContainer;
    private Socket socket;
    private static AssortmentService assortmentService;

    @BeforeAll
    static void beforeAll() {
        assortmentService = mock(AssortmentService.class);
        TCPServerCommandContainer = new HashMap<>();
        TCPServerCommandContainer.put(ServerConstants.TCP_COMMAND_GET_COUNT, new ReturnNumberOfProductsCommand(assortmentService));
        TCPServerCommandContainer.put(ServerConstants.TCP_COMMAND_GET_ITEM, new ReturnProductInfoCommand(assortmentService));
    }

    @Test
    public void shouldWriteNumberOfProducts() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProductList()).thenReturn(new ArrayList<>(Arrays.asList(new Product(), new Product())));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream((ServerConstants.TCP_COMMAND_GET_COUNT + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new TCPClientThread(socket, TCPServerCommandContainer);
        thread.start();
        thread.join();

        Assertions.assertEquals("2" + System.lineSeparator(), outputStream.toString());
    }

    @Test
    public void shouldWriteProductInfo() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProduct(1)).thenReturn(new Product(new BigDecimal("13.333"), "test"));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(("get item=1" + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new TCPClientThread(socket, TCPServerCommandContainer);
        thread.start();
        thread.join();

        Assertions.assertEquals("test|13.333" + System.lineSeparator(), outputStream.toString());
    }

    @Test
    public void shouldWriteResponseWhenCommandIsInvalid() throws IOException, InterruptedException {
        socket = mock(Socket.class);
        OutputStream outputStream = new ByteArrayOutputStream();
        when(assortmentService.getProduct(1)).thenReturn(new Product(new BigDecimal("13.333"), "test"));
        when(socket.getInputStream()).thenReturn(new ByteArrayInputStream(("abrakadabra" + System.lineSeparator()).getBytes()));
        when(socket.getOutputStream()).thenReturn(outputStream);

        Thread thread = new TCPClientThread(socket, TCPServerCommandContainer);
        thread.start();
        thread.join();

        Assertions.assertEquals("Unknown command" + System.lineSeparator(), outputStream.toString());
    }

}