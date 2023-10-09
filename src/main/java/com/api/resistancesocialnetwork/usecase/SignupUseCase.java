package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.domain.user.User;
import com.api.resistancesocialnetwork.entity.Inventory;
import com.api.resistancesocialnetwork.entity.Location;
import com.api.resistancesocialnetwork.entity.Rebel;
import com.api.resistancesocialnetwork.infra.security.TokenService;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.RebelRepository;
import com.api.resistancesocialnetwork.repositories.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.request.facade.SignupFacade;
import com.api.resistancesocialnetwork.rules.SignupRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceSocialNetworkException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SignupUseCase {
    private final RebelRepository rebelRepo;
    private final SignupRules signUpRules;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public SignupUseCase(RebelRepository rebelRepo, SignupRules signUpRules, UserRepository userRepository, TokenService tokenService) {
        this.rebelRepo = rebelRepo;
        this.signUpRules = signUpRules;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public void handle(SignupFacade signup, String header) throws ResistanceSocialNetworkException {
        signUpRules.handle(signup);

        Location formattedLocation = signup.location();
        Rebel formattedRebel = signup.rebel();
        Inventory formattedInventory = signup.inventory();

        formattedRebel.setLocation(formattedLocation);
        formattedRebel.setInventory(formattedInventory);

        rebelRepo.save(formattedRebel);
        var tokenHeader = header.replace("Bearer ", "");
        var login = tokenService.validateToken(tokenHeader);

        User relatedUser = userRepository.findUserByLogin(login).orElseThrow();

        relatedUser.setRebel(formattedRebel);
        relatedUser.setLocation(formattedLocation);
        relatedUser.setInventory(formattedInventory);

        userRepository.save(relatedUser);
    }
}
