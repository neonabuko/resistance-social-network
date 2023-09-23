package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.RequestSignUp;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final SignupUseCase signupUseCase;

    public SignUpController(SignupUseCase signupUseCase) {
        this.signupUseCase = signupUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> displayMainPage() {
        return ResponseEntity.ok("Welcome to the Star Wars Resistance Social Network.");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> handleSignUp(@RequestBody RequestSignUp signUpData) {
        signupUseCase.handle(
                signUpData.rebel(),
                signUpData.location(),
                signUpData.inventory()
        );
        return ResponseEntity.ok("Registration successful");
    }
}
