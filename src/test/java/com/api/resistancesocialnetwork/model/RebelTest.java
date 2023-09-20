package com.api.resistancesocialnetwork.model;

import com.api.resistancesocialnetwork.repositories.LocationRepository;
import com.api.resistancesocialnetwork.repositories.RebelRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RebelTest {

    @Autowired
    private RebelRepository rebelRepo;
    @Autowired
    private LocationRepository locationRepo;

    @Test
    @Transactional
    void should() {
        Rebel rebel = new Rebel("matheus", 28, "male");
        Location location = new Location(32.1, 32.1, "base");
        rebel.setLocation(location);
        rebelRepo.save(rebel);
        locationRepo.save(location);

        System.out.println(rebelRepo.findById(rebel.getId()).get().getLocation() + "\naaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

}