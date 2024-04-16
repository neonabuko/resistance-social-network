package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.LoginFacade;
import com.api.resistancesocialnetwork.infra.security.ResistanceTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final ResistanceTokenService resistanceTokenService;

    public LoginUseCase(AuthenticationManager authenticationManager, ResistanceTokenService resistanceTokenService) {
        this.authenticationManager = authenticationManager;
        this.resistanceTokenService = resistanceTokenService;
    }

    public String handle(LoginFacade data) {
        var username = data.getUsername().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "must provide username")
        );
        var password = data.getPassword().orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "must provide password")
        );

        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        return resistanceTokenService.generateToken((ResistanceUser) auth.getPrincipal());
    }
}
