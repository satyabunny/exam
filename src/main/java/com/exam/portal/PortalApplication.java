package com.exam.portal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.exam.portal")
public class PortalApplication extends SpringBootServletInitializer {

    private static Class<PortalApplication> application = PortalApplication.class;

    public static void main(String[] args) {
    	System.out.println("Inside application===========");
        SpringApplication.run(application, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	System.out.println("Inside Initializer===========");
        return builder.sources(application);
    }
}
