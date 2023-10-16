package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.User;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ProfileController {
    private final ProfileUseCase profileUseCase;

    public ProfileController(ProfileUseCase profileUseCase) {
        this.profileUseCase = profileUseCase;
    }

    @GetMapping("/")
    public ResponseEntity<String> homePage() {
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> signup(@RequestBody ProfileFacade signup,
                                       @RequestHeader("Authorization") String header) throws ResistanceSocialNetworkException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profileUseCase.handle(signup, user.getLogin());
        return ResponseEntity.created(URI.create("/profile")).build();
    }
}
