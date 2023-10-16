package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.facade.LoginFacade;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.usecase.LoginUseCase;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final LoginUseCase login;
    private final SignupUseCase signup;

    public AuthController(LoginUseCase login, SignupUseCase signup) {
        this.login = login;
        this.signup = signup;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginFacade login) {
        var token = this.login.handle(login);
        
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody SignupFacade signupFacade) {
        signup.handle(signupFacade);
        return ResponseEntity.ok().build();
    }
}
