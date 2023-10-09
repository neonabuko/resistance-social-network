package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.request.SignupRequest;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> handleSignUp(@RequestBody SignupRequest signupRequest,
                                             @RequestHeader HttpServletRequest httpServletRequest) throws ResistanceSocialNetworkException {
        String header = httpServletRequest.getHeader("Authorization");
        signupUseCase.handle(signupRequest.signup(), header);
        return new ResponseEntity<>(HttpStatus.valueOf(201));
    }
}
