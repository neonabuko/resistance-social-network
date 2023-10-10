package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.SignupRequest;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.service.LoginService;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SignUpController {
    private final SignupUseCase signupUseCase;
    private final LoginService loginService;

    public SignUpController(SignupUseCase signupUseCase, LoginService loginService) {
        this.signupUseCase = signupUseCase;
        this.loginService = loginService;
    }

    @GetMapping("/")
    public ResponseEntity<String> displayMainPage() {
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> handleSignUp(@RequestBody SignupRequest signupRequest,
                                             @RequestHeader("Authorization") String header) throws ResistanceSocialNetworkException {
        String login = loginService.recoverLogin(header);
        signupUseCase.handle(signupRequest.signup(), login);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }
}
