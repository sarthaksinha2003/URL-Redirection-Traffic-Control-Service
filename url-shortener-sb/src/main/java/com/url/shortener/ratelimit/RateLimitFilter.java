package com.url.shortener.ratelimit;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiter;

    public RateLimitFilter(RateLimiterService rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // Apply rate limit only to important endpoints
        if (path.startsWith("/api/auth") ||
                path.startsWith("/api/urls/shorten") ||
                path.matches("^/[a-zA-Z0-9]+$")) {

            String ip = request.getRemoteAddr();

            if (!rateLimiter.allowRequest(ip)) {
                response.setStatus(429);
                response.getWriter().write("Too many requests");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
