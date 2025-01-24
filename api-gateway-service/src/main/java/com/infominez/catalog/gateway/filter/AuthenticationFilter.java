package com.infominez.catalog.gateway.filter;

import com.google.common.net.HttpHeaders;
import com.infominez.catalog.gateway.GatewayServiceApplication;
import com.infominez.catalog.gateway.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest httpRequest = null;
            if (validator.isSecured.test(exchange.getRequest())) {

                if (!exchange.getRequest().getHeaders().containsKey("X-Tenant-ID")) {
                    return unauthorizedResponse(exchange, "Missing Tenant ID");
                }

                String tenantId = exchange.getRequest().getHeaders().get("X-Tenant-ID").get(0);
                log.info("tenantId : {}", tenantId);
                if (!GatewayServiceApplication.tenantDataSourceMap.containsKey(tenantId)) {
                    return unauthorizedResponse(exchange, "Invalid Tenant ID");
                }

                //header contains token or not
//                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                    return unauthorizedResponse(exchange, "Missing Authorization header");
//                }
//
//                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
//
//                if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                    authHeader = authHeader.substring(7);
//                } else {
//                    return unauthorizedResponse(exchange, "Invalid Authorization header format");
//                }
//
//                String url = "http://localhost:8073/api/auth-service/auth/isTokenBlacklisted?token=" + authHeader;
//                log.info("URL : {}", url);
//                ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
//                response.getBody();
//                boolean isBlackListed = response.getBody();
//                log.info("isBlackListed : {}", isBlackListed);
//                if (isBlackListed) {
//                    return unauthorizedResponse(exchange, "Session expired. Please sign in again");
//                }

                try {
//                    jwtUtil.validateToken(authHeader);
//                    String token = authHeader;
                    httpRequest = exchange.getRequest()
                            .mutate()
                            .headers(headers -> {
//                                headers.add("Authorization", "Bearer " + token);
                                headers.add("X-Tenant-ID", tenantId);
//                                headers.add("username", jwtUtil.extractUser(token));
                            })
                            .build();

                } catch (Exception e) {
                    return unauthorizedResponse(exchange, "Session expired. Please sign in again");
                }
            }
            log.info("Final request : {}", httpRequest.getPath());
            return chain.filter(exchange.mutate().request(httpRequest).build());
        });
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");

        String jsonResponse = "{"
                + "\"timestamp\": \"" + new Date() + "\","
                + "\"status\": 401,"
                + "\"error\": \"Unauthorized Access\","
                + "\"message\": \"" + message + "\","
                + "\"path\": \"" + exchange.getRequest().getPath() + "\""
                + "}";

        byte[] responseBytes = jsonResponse.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);

        return exchange.getResponse().writeWith(Mono.just(buffer));
    }


    public static class Config {

    }
}
