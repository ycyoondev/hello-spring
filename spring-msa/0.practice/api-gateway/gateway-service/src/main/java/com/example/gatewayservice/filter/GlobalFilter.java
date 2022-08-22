package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {
    public GlobalFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            System.out.println("Global Filter baseMessage -> " + config.getBaseMassage());

            if (config.isPreLogger()) {
                System.out.println("request.getId() = " + request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Custom POST filter: response code -> " + response.getStatusCode());
            }));
        });
    }

    public static class Config {
        public String getBaseMassage() {
            return baseMassage;
        }

        public boolean isPreLogger() {
            return preLogger;
        }

        public boolean isPostLogger() {
            return postLogger;
        }

        private String baseMassage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
