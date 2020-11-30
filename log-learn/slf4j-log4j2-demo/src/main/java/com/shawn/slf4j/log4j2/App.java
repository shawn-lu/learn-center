package com.shawn.slf4j.log4j2;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hello world!
 */
public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    private static final org.apache.logging.log4j.Logger logger2 = LogManager.getLogger(App.class);

    public static void main(String[] args) {
//        logger.info("Hello, World!");
        logger2.info("Hello, World!");
    }
}
