package com.api.resistancesocialnetwork.service;

import com.api.resistancesocialnetwork.infra.security.TokenService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final TokenService tokenService;

    public LoginService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String recoverLogin(String header) {
        var tokenHeader = header.replace("Bearer ", "");
        return tokenService.validateToken(tokenHeader);
    }

}
