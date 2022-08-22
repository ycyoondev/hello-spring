package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {
    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//            System.out.println("Global Filter baseMessage -> " + config.getBaseMassage());
//
//            if (config.isPreLogger()) {
//                System.out.println("request.getId() = " + request.getId());
//            }
//
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                System.out.println("Custom POST filter: response code -> " + response.getStatusCode());
//            }));
//        });
        return new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            System.out.println("Logging Filter baseMessage -> " + config.getBaseMassage());

            if (config.isPreLogger()) {
                System.out.println("request.getId() = " + request.getId());
            }

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                System.out.println("Logging POST filter: response code -> " + response.getStatusCode());
            }));
        }, Ordered.HIGHEST_PRECEDENCE);
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

        public void setBaseMassage(String baseMassage) {
            this.baseMassage = baseMassage;
        }

        public void setPreLogger(boolean preLogger) {
            this.preLogger = preLogger;
        }

        public void setPostLogger(boolean postLogger) {
            this.postLogger = postLogger;
        }

        private String baseMassage;
        private boolean preLogger;
        private boolean postLogger;
    }
}
