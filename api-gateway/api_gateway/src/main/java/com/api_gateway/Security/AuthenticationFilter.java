package com.api_gateway.Security;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter
        extends OncePerRequestFilter {

    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/**"
    );

    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    private final JwtUtil jwtUtil;

    public AuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = getPathWithinApplication(request);
        return PUBLIC_PATHS.stream().anyMatch(publicPath -> PATH_MATCHER.match(publicPath, path));
    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired token");
            return;
        }

        String userId = jwtUtil.extractUserId(token);
        String role = jwtUtil.extractRole(token);

        // Build a wrapped request that:
        // 1. Strips any client-injected X-User-Id / X-User-Role headers
        // 2. Injects the values derived from the signed JWT
        MutableHeaderRequest wrappedRequest = new MutableHeaderRequest(request,
                Set.of("X-User-Id", "X-User-Role"));
        wrappedRequest.putHeader("X-User-Id", userId);
        if (role != null) {
            wrappedRequest.putHeader("X-User-Role", role);
        }

        filterChain.doFilter(wrappedRequest, response);
    }

    private String getPathWithinApplication(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();

        if (StringUtils.hasText(contextPath) && requestUri.startsWith(contextPath)) {
            return requestUri.substring(contextPath.length());
        }

        return requestUri;
    }

    private static final class MutableHeaderRequest extends HttpServletRequestWrapper {
        private final Map<String, String> headers = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        private final Set<String> strippedHeaders;

        /**
         * @param request         the original request
         * @param headersToStrip  header names to strip from the original request (case-insensitive)
         */
        private MutableHeaderRequest(HttpServletRequest request, Set<String> headersToStrip) {
            super(request);
            // Copy the set with case-insensitive matching
            TreeMap<String, Boolean> stripped = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            headersToStrip.forEach(h -> stripped.put(h, Boolean.TRUE));
            this.strippedHeaders = stripped.keySet();
        }

        void putHeader(String name, String value) {
            headers.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = headers.get(name);
            if (headerValue != null) return headerValue;
            if (strippedHeaders.contains(name)) return null;
            return super.getHeader(name);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            String headerValue = headers.get(name);
            if (headerValue != null) {
                return Collections.enumeration(Collections.singletonList(headerValue));
            }
            if (strippedHeaders.contains(name)) {
                return Collections.emptyEnumeration();
            }
            return super.getHeaders(name);
        }

        @Override
        public Enumeration<String> getHeaderNames() {
            Map<String, String> allHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            Enumeration<String> headerNames = super.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                if (!strippedHeaders.contains(headerName)) {
                    allHeaders.put(headerName, super.getHeader(headerName));
                }
            }
            allHeaders.putAll(headers);
            return Collections.enumeration(allHeaders.keySet());
        }
    }
}
