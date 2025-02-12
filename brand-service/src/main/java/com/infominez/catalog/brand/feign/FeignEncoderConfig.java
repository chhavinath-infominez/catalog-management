package com.infominez.catalog.brand.feign;

import com.infominez.catalog.brand.tenant.TenantContextHolder;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignEncoderConfig {
    @Bean
    public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringFormEncoder(new SpringEncoder(converters));
    }

    @Bean
    public RequestInterceptor tenantIdRequestInterceptor() {
        return requestTemplate -> {
            // Fetch tenantId from TenantContextHolder
            String tenantId = TenantContextHolder.getTenantId();
            if (tenantId != null) {
                requestTemplate.header("X-Tenant-ID", tenantId); // Add tenantId to the headers
            }
        };
    }
}
