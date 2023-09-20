package com.api.resistancesocialnetwork.controller;


import com.api.resistancesocialnetwork.repositories.InventoryRepository;
import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import com.api.resistancesocialnetwork.request.RequestSignUp;
import com.api.resistancesocialnetwork.usecase.RegistrationUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    private final RebelRepository rebelRepo;
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepo;
    private final RegistrationUseCase registrationUseCase;

    @Autowired
    public SignUpController(RebelRepository rebelRepo, LocationRepository locationRepo, InventoryRepository inventoryRepo, RegistrationUseCase registrationUseCase) {
        this.rebelRepo = rebelRepo;
        this.locationRepo = locationRepo;
        this.inventoryRepo = inventoryRepo;
        this.registrationUseCase = registrationUseCase;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> handleSignUp(@RequestBody RequestSignUp signUpData) {
        registrationUseCase.handle(signUpData.rebel(), signUpData.location(), signUpData.inventory());
        return ResponseEntity.ok("Registration successful\n\n" + signUpData);
    }
}
