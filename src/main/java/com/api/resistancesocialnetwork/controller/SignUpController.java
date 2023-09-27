package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.SignupRequest;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> handleSignUp(@RequestBody SignupRequest signupRequest) throws ResistanceSocialNetworkException {
        signupUseCase.handle(signupRequest.signup());
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }
}
