package com.ruslan.magaz;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("/META-INF/spring/spring-root.xml");
    }
}
