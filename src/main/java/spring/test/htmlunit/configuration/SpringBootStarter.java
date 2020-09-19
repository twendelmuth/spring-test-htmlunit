package spring.test.htmlunit.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"spring.test"})
public class SpringBootStarter {

    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(SpringBootStarter.class).run(args);
    }

}
