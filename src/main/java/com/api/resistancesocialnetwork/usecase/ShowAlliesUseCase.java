package com.api.resistancesocialnetwork.usecase;

import com.api.resistancesocialnetwork.model.Rebel;
import com.api.resistancesocialnetwork.repositories.interfacerepositories.RebelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ShowAlliesUseCase {
    private final RebelRepository rebelRepo;

    @Autowired
    public ShowAlliesUseCase(RebelRepository rebelRepo) {
        this.rebelRepo = rebelRepo;
    }

    public List<String> handle() {
        List<String> allies = new ArrayList<>();

        for ( Rebel rebel : rebelRepo.findAll() ) {
            if (rebel.isNotTraitor()) {
                allies.addAll(Arrays.asList(
                        rebel.toString(),
                        rebel.getLocation().toString(),
                        rebel.getInventory().toString()
                ));
            }
        }
        return allies;
    }
}
