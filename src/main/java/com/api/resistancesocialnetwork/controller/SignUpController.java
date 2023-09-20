package com.api.resistancesocialnetwork.controller;


import com.api.resistancesocialnetwork.request.RequestSignUp;
import com.api.resistancesocialnetwork.usecase.RegistrationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final RegistrationUseCase registrationUseCase;

    @Autowired
    public SignUpController(RegistrationUseCase registrationUseCase) {
        this.registrationUseCase = registrationUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> handleSignUp(@RequestBody RequestSignUp signUpData) {
        try {
            registrationUseCase.handle(signUpData.rebel(), signUpData.location(), signUpData.inventory());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Registration successful");
    }
}
