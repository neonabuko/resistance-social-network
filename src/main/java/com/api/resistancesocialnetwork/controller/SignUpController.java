package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.SignupUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class SignUpController {
    private final SignupUseCase signupUseCase;

    public SignUpController(SignupUseCase signupUseCase) {
        this.signupUseCase = signupUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> homePage() {
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody SignupFacade signup,
                                       @RequestHeader("Authorization") String header) throws ResistanceSocialNetworkException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        signupUseCase.handle(signup, user.getLogin());
        return ResponseEntity.created(URI.create("/signup")).build();
    }
}
