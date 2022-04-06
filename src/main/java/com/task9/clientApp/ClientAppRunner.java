package com.task9.clientApp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientAppRunner {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean continueWork = true;
        while (continueWork) {
            try (Socket socket = new Socket(ApplicationUtil.TCP_SERVER_HOST, ApplicationUtil.TCP_SERVER_PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                System.out.println("Enter command: ");
                String command = scanner.nextLine();
                writer.write(command);
                writer.newLine();
                writer.flush();
                String answer = reader.readLine();
                System.out.println(answer);
                System.out.printf("Print '%s' to continue work%n", ApplicationUtil.PRINT_TO_CONTINUE);
            } catch (IOException exception) {
                System.out.println("Cannot connect to server");
            } finally {
                if (!ApplicationUtil.PRINT_TO_CONTINUE.equals(scanner.nextLine())) {
                    continueWork = false;
                }
            }
        }
        scanner.close();
    }
}
