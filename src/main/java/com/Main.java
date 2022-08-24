package com;
import com.config.SpringConfig;
import com.view.Console;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Console dictionaryConsole = applicationContext.getBean(Console.class);
        dictionaryConsole.start();
    }
}
