package com.myreflectionthoughts.user.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myreflectionthoughts.user.adapter.feign.FeignErrorDecoder;
import feign.Client;
import feign.codec.ErrorDecoder;
import feign.httpclient.ApacheHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper){
        return new FeignErrorDecoder(objectMapper);
    }

    @Bean
    Client client(){
        return new ApacheHttpClient();
    }
}
