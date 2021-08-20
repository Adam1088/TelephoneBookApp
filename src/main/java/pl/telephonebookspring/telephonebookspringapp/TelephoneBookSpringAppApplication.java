package pl.telephonebookspring.telephonebookspringapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.telephonebookspring.telephonebookspringapp.controller.TeleBookController;


@SpringBootApplication
public class TelephoneBookSpringAppApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(TelephoneBookSpringAppApplication.class, args);
        TeleBookController bookController = ctx.getBean(TeleBookController.class);
        bookController.loop();
    }

}
