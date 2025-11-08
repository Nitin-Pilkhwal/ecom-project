package com.demo.project.nitin.ecommerce.auth;

import com.demo.project.nitin.ecommerce.cache.TokenCache;
import com.demo.project.nitin.ecommerce.constant.Constants;
import com.demo.project.nitin.ecommerce.constant.MessageKeys;
import com.demo.project.nitin.ecommerce.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final TokenCache tokenCache;
    private final MessageSource messageSource;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().startsWith("/auth") &&
                !request.getServletPath().equals("/auth/logout")){
            filterChain.doFilter(request, response);
            return;
        }
        String jwt;
        final String authHeader = request.getHeader(Constants.Security.AUTHORIZATION_HEADER);
        if (authHeader == null || !authHeader.startsWith(Constants.Security.AUTHORIZATION_HEADER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            jwt = authHeader.substring(Constants.Security.AUTHORIZATION_HEADER_PREFIX.length());
            Claims claims = jwtUtils.extractallclaims(jwt);
            String username = claims.getSubject();
            String tokenId = claims.getId();

            if(tokenCache.isTokenBlackListed(tokenId)){
                log.error("Token is blacklisted.");
                throw new ExpiredJwtException(null,claims,
                        MessageKeys.TOKEN_ALREADY_EXPIRED);
            }
            List<String> roles = claims.get(Constants.Security.ROLES, List.class);
            List<? extends GrantedAuthority> authorities = roles.stream().map(
                    SimpleGrantedAuthority::new
            ).toList();
            if(SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                username,null,authorities
                        );
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            filterChain.doFilter(request, response);
        }catch (JwtException ex) {

            String errorCode = switch (ex) {
                case ExpiredJwtException e -> MessageKeys.TOKEN_ALREADY_EXPIRED;
                case MalformedJwtException e -> MessageKeys.TOKEN_MALFORMED;
                case SignatureException e -> MessageKeys.TOKEN_SIGNATURE_INVALID;
                default -> MessageKeys.INVALID_TOKEN;
            };
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String localizedMessage = messageSource.getMessage(
                    errorCode, null, LocaleContextHolder.getLocale()
            );
            response.getWriter().write(
                    "{\"error\": \"" + localizedMessage + "\"}"
            );
        }

    }
}
