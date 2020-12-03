package com.shawn.springboot.learn;

import com.shawn.springboot.my.starter.HelloFormatTemplate;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Hello world!
 */
@SpringBootApplication
public class Bootstrap implements CommandLineRunner {
    @Autowired
    private HelloFormatTemplate helloFormatTemplate;
    private static String STATIC_LOGGER_BINDER_PATH = "org/slf4j/impl/StaticLoggerBinder.class";

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        System.out.println("Hello World!");
        try {
            ClassLoader loggerFactoryClassLoader = LoggerFactory.class.getClassLoader();
            Enumeration<URL> paths;
            System.out.println(loggerFactoryClassLoader);
            if (loggerFactoryClassLoader == null) {
                paths = ClassLoader.getSystemResources(STATIC_LOGGER_BINDER_PATH);
            } else {
                paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
            }
            while(paths.hasMoreElements()){
                System.out.println(paths.nextElement());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(String... args) throws Exception {
        String formatProperties = helloFormatTemplate.doFormat();
        System.out.println(formatProperties);
    }
}
