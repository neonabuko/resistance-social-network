package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.AuthFacade;
import com.api.resistancesocialnetwork.infra.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public LoginUseCase(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public String handle(AuthFacade data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((User) auth.getPrincipal());
    }
}
