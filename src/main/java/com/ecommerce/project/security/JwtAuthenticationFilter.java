package com.ecommerce.project.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // ✅ Get Authorization header
        String authHeader = request.getHeader("Authorization");

        System.out.println("👉 Request URI: " + request.getRequestURI());
        System.out.println("👉 Authorization Header: " + authHeader);

        // ❌ If header missing or wrong format → skip
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // ✅ Extract token
            String token = authHeader.substring(7);
            System.out.println("👉 Token: " + token);

            // ✅ Extract username (email)
            String email = jwtUtil.extractUsername(token);
            System.out.println("👉 Extracted Email: " + email);

            // ✅ Check if not already authenticated
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Load user
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                System.out.println("👉 User Loaded: " + userDetails.getUsername());

                // Validate token
                if (jwtUtil.validateToken(token, userDetails)) {

                    System.out.println("✅ Token is valid");

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // ✅ Set authentication
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                } else {
                    System.out.println("❌ Token validation failed");
                }
            }

        } catch (Exception e) {
            System.out.println("❌ JWT Error: " + e.getMessage());
        }

        // ✅ Continue filter chain
        filterChain.doFilter(request, response);
    }
}