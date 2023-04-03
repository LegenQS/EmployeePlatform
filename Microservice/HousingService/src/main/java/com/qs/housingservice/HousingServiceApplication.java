package com.qs.housingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@EnableEurekaClient
@EnableSwagger2
public class HousingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HousingServiceApplication.class, args);
    }

}
