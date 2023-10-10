package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.facade.AuthFacade;
import com.api.resistancesocialnetwork.facade.RegisterFacade;
import com.api.resistancesocialnetwork.usecase.LoginUseCase;
import com.api.resistancesocialnetwork.usecase.RegisterUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final LoginUseCase loginUseCase;
    private final RegisterUseCase registerUseCase;

    public AuthController(LoginUseCase loginUseCase, RegisterUseCase registerUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUseCase = registerUseCase;
    }

    @PostMapping("/username")
    public ResponseEntity<String> login(@RequestBody AuthFacade data) {
        var token = loginUseCase.handle(data);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterFacade data) {
        registerUseCase.handle(data);
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
