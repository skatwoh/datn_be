package be.bds.bdsbes;

import be.bds.bdsbes.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BdsbesApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BdsbesApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BdsbesApplication.class);
    }
}
