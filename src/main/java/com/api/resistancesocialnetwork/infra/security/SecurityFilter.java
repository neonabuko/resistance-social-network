package com.api.resistancesocialnetwork.infra.security;

import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final ResistanceTokenService resistanceTokenService;
    private final ResistanceUserRepository resistanceUserRepository;

    public SecurityFilter(ResistanceTokenService resistanceTokenService, ResistanceUserRepository resistanceUserRepository) {
        this.resistanceTokenService = resistanceTokenService;
        this.resistanceUserRepository = resistanceUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = getTokenFromRequest(request);
        if (token.isPresent()) {
            var username = resistanceTokenService.getUsernameFromToken(token.get());
            if (username.isPresent()) {
                var userDetails = resistanceUserRepository.findUserDetailsBy(username.get()).orElseThrow();
                var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokenFromRequest(HttpServletRequest request) {
        Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));
        return authHeader.map(s -> s.replace("Bearer ", ""));
    }
}
