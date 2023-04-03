package com.qs.applicationservice.config;

import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:aws.properties")
public class AWSConfig {
    @Value("${aws.accessKeyId}")
    private String accessKeyId;
    @Value("${aws.secretKey}")
    private String secretKey;
    @Bean
    public BasicAWSCredentials basicAWSCredentials() {
        return new BasicAWSCredentials(accessKeyId, secretKey);
    }


}
