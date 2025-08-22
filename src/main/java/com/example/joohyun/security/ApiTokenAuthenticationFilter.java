package com.example.joohyun.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * 단순 정적 API 토큰 기반 인증 필터.
 * Authorization: Bearer {token} 헤더 값을 읽어 설정값과 일치하면 인증 처리
 */
@Component
public class ApiTokenAuthenticationFilter extends OncePerRequestFilter {

    private final ApiTokenProperties properties;

    public ApiTokenAuthenticationFilter(ApiTokenProperties properties) {
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (properties.getToken().equals(token)) {
                Authentication authentication = new ApiTokenAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /** 간단한 인증 객체 */
    static class ApiTokenAuthentication extends AbstractAuthenticationToken {
        private final String token;
        ApiTokenAuthentication(String token) {
            super(defaultAuthorities());
            this.token = token;
            setAuthenticated(true);
        }
        @Override
        public Object getCredentials() { return token; }
        @Override
        public Object getPrincipal() { return "api-client"; }
    }

    private static Collection<? extends GrantedAuthority> defaultAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_API"));
    }
}
