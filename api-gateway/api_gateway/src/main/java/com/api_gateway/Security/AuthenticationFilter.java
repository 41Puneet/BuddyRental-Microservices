package com.api_gateway.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String userId = jwtUtil.extractUserId(token);
        HttpServletRequest wrappedRequest = new MutableHeaderRequest(request, "X-User-Id", userId);
        filterChain.doFilter(wrappedRequest, response);
    }

    private static final class MutableHeaderRequest extends HttpServletRequestWrapper {
        private final Map<String, String> headers = new HashMap<>();

        private MutableHeaderRequest(HttpServletRequest request, String name, String value) {
            super(request);
            headers.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = headers.get(name);
            return headerValue != null ? headerValue : super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            String headerValue = headers.get(name);
            if (headerValue != null) {
                return Collections.enumeration(Collections.singletonList(headerValue));
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Map<String, String> allHeaders = new HashMap<>();
            Enumeration<String> headerNames = super.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                allHeaders.put(headerName, super.getHeader(headerName));
            }
            allHeaders.putAll(headers);
            return Collections.enumeration(allHeaders.keySet());
        }
    }
}
