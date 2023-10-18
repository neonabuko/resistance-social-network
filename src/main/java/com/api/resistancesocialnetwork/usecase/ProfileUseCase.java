package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.entity.*;
import com.api.resistancesocialnetwork.facade.ItemFacade;
import com.api.resistancesocialnetwork.facade.ProfileFacade;
import com.api.resistancesocialnetwork.repository.repositoryinterfaces.UserRepository;
import com.api.resistancesocialnetwork.rules.ProfileRules;
import com.api.resistancesocialnetwork.rules.commons.ResistanceException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProfileUseCase {
    private final ProfileRules signUpRules;
    private final UserRepository userRepository;

    public ProfileUseCase(ProfileRules signUpRules, UserRepository userRepository) {
        this.signUpRules = signUpRules;
        this.userRepository = userRepository;
    }

    public void handle(ProfileFacade signup, String login) throws ResistanceException {
        User user = userRepository.findUserBy(login).orElseThrow(
                () -> new ResistanceException("user not found")
        );
        if (user.getRebel().isPresent()) throw new ResistanceException("profile already set");
        if (signup == null) throw new ResistanceException("must provide parameters");

        Rebel rebel = new Rebel(signup.name(), signup.age(), signup.gender());
        Location location = new Location(signup.latitude(), signup.longitude(), signup.base());

        List<Item> items = new ArrayList<>();
        for (ItemFacade itemFacade : signup.inventory()) {
            items.add(new Item(itemFacade.name(), itemFacade.price()));
        }
        Inventory inventory = new Inventory(items);

        signUpRules.handle(rebel, location, inventory);

        rebel.setLocation(location);
        rebel.setInventory(inventory);

        user.setRebel(rebel);
        user.setLocation(location);
        user.setInventory(inventory);

        userRepository.save(user);
    }
}
