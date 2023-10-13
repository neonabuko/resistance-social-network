package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.facade.LoginFacade;
import com.api.resistancesocialnetwork.facade.RegisterFacade;
import com.api.resistancesocialnetwork.usecase.LoginUseCase;
import com.api.resistancesocialnetwork.usecase.RegisterUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final LoginUseCase login;
    private final RegisterUseCase register;

    public AuthController(LoginUseCase login, RegisterUseCase register) {
        this.login = login;
        this.register = register;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFacade login) {
        var token = this.login.handle(login);
        
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterFacade register) {
        this.register.handle(register);
        return ResponseEntity.ok().build();
    }
}
