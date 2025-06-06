package org.example;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Context initialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Context destroyed");
    }
}
