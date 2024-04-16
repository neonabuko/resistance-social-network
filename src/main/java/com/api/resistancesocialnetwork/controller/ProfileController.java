package com.api.resistancesocialnetwork.controller;

import com.api.resistancesocialnetwork.entity.ResistanceUser;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import com.api.resistancesocialnetwork.usecase.ProfileUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ProfileController {
    private final ProfileUseCase profile;

    public ProfileController(ProfileUseCase profile) {
        this.profile = profile;
    }

    @GetMapping("/")
    public ResponseEntity<String> getHomePage() {
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @PostMapping("/profile")
    public ResponseEntity<Void> handleProfile(@RequestBody ProfileFacade signup,
                                              @RequestHeader("Authorization") String header) throws ResistanceException {
        ResistanceUser user = (ResistanceUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        profile.handle(signup, user.getLogin());
        return ResponseEntity.created(URI.create("/profile")).build();
    }
}
