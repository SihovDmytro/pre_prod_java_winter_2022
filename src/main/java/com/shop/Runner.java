package com.shop;

import com.shop.command.Command;
import com.shop.command.impl.DefaultCommand;
import com.shop.command.impl.ExitCommand;
import com.shop.initialization.ApplicationInit;
import com.shop.util.MenuUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Scanner;

public class Runner {
    private static final Logger LOG = LogManager.getLogger(Runner.class);
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        LOG.trace("Start application");
        ApplicationInit applicationInit = new ApplicationInit(scanner);
        boolean continueWork = applicationInit.init();
        LOG.debug("Load properties: " + continueWork);
        if (!continueWork) return;

        Map<String, Command> commandsContainer = applicationInit.getCommandsContainer();
        while (continueWork) {
            MenuUtil.printMainMenu();
            String option = scanner.nextLine();
            LOG.debug("option: " + option);
            Command command = commandsContainer.getOrDefault(option, new DefaultCommand());
            LOG.trace("command: " + command);
            command.execute();
            if (command.getClass().equals(ExitCommand.class)) {
                continueWork = false;
            }
        }
        scanner.close();
    }
}
