package com.qs.gatewayservice.config;

import com.qs.gatewayservice.security.AllFilter;
import com.qs.gatewayservice.security.EmployeeFilter;
import com.qs.gatewayservice.security.HRFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);

    EmployeeFilter employeeFilter;

    HRFilter hrFilter;

    AllFilter allFilter;

    @Autowired
    public GatewayConfig(EmployeeFilter employeeFilter, HRFilter hrFilter, AllFilter allFilter) {
        this.employeeFilter = employeeFilter;
        this.hrFilter = hrFilter;
        this.allFilter = allFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user-service/user/**")
                        .filters(f -> f.filter(allFilter)).uri("lb://user-service"))

                .route("application-service", r -> r.path("/application-service/**")
                        .filters(f -> f.filter(allFilter)).uri("lb://application-service"))

                .route("email-service", r -> r.path("/email-service/**")
                        .filters(f -> f.filter(hrFilter)).uri("lb://email-service"))

                .route("housing-service-hr", r -> r.path("/housing-service/hr/**")
                        .filters(f -> f.filter(hrFilter)).uri("lb://housing-service/hr"))

                .route("housing-service-employee", r -> r.path("/housing-service/employee/**")
                        .filters(f -> f.filter(employeeFilter)).uri("lb://housing-service/employee"))

                .route("housing-service", r -> r.path("/housing-service/**")
                        .filters(f -> f.filter(allFilter)).uri("lb://housing-service"))

                .route("auth-service", r -> r.path("/auth-service/**")
                        .uri("lb://auth-service"))

                .route("composite-service", r -> r.path("/composite-service/**")
                        .filters(f -> f.filter(allFilter)).uri("lb://composite-service"))
                .build();
    }
}