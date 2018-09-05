package com.aryanda.rms;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;

public class ServletStartup implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Locale.setDefault(Locale.US);
    }
}
