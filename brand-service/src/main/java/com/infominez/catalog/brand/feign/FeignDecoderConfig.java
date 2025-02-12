package com.infominez.catalog.brand.feign;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.codec.Decoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignDecoderConfig {

    @Bean
    public Decoder feignDecoder(ObjectMapper objectMapper) {
        return (response, type) -> {
            JavaType javaType = objectMapper.getTypeFactory().constructType(type);
            return objectMapper.readValue(response.body().asInputStream(), javaType);
        };
    }
}
