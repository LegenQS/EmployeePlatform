package com.qs.gatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;

//The jwt filter that we want to add to the chain of filters of Spring Security
@Component
public class EmployeeFilter implements GatewayFilter {

    private JwtProvider jwtProvider;

    @Autowired
    public void setJwtProvider(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("get into employee filter");
        List<String> header = exchange.getRequest().getHeaders().getOrEmpty("Authorization");

        if (header == null || header.isEmpty()) return exchange.getResponse().setComplete();

        Optional<AuthUserDetail> authUserDetailOptional = jwtProvider.resolveToken(header.get(0));

        if (authUserDetailOptional.isPresent()){
            AuthUserDetail authUserDetail = authUserDetailOptional.get();
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    authUserDetail.getUsername(),
                    null,
                    authUserDetail.getAuthorities()
            ); // generate authentication object

            List<String> authorities = authentication.getAuthorities().stream()
                    .map(p -> p.getAuthority())
                    .collect(Collectors.toList());

            System.out.println(authorities);

            if (!authorities.contains("employee")) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                System.out.println("access denied");
                return exchange.getResponse().setComplete();
            }
            // put authentication object in the securityContext
            exchange.getRequest().mutate().header("user-name", authUserDetail.getUsername());
//            exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_SECURITY_CONTENT_ATTR, authentication);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        }
        else {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }


        return chain.filter(exchange);
    }
}
