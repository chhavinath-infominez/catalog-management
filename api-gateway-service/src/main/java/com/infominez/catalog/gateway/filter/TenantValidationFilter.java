package com.infominez.catalog.gateway.filter;

import com.infominez.catalog.gateway.GatewayServiceApplication;
import com.infominez.catalog.gateway.entity.TenantDataSource;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class TenantValidationFilter implements GatewayFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String tenantId = exchange.getRequest().getHeaders().getFirst("X-Tenant_ID");

        if (tenantId.isEmpty()) {
            return invalidTenantResponse(exchange, "Tenant ID is required");
        }

        TenantDataSource tenantDataSource = GatewayServiceApplication.tenantDataSourceMap.get(tenantId);
        if (tenantDataSource == null) {
            return invalidTenantResponse(exchange, "Tenant not found");
        }

        ServerWebExchange updatedExchange = exchange.mutate()
                .request(builder -> builder.header("X-Tenant-ID", tenantId)).build();

        return chain.filter(updatedExchange);
    }

    private Mono<Void> invalidTenantResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

        String jsonResponse = "{"
                + "\"timestamp\": \"" + new Date() + "\","
                + "\"status\": 401,"
                + "\"error\": \"Invalid Tenant\","
                + "\"message\": \"" + message + "\","
                + "\"path\": \"" + exchange.getRequest().getPath() + "\""
                + "}";

        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE; // Ensure this filter runs first
    }
}
