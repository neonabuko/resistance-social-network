package com.api.resistancesocialnetwork.repositories;

import com.api.resistancesocialnetwork.model.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Integer> {
}
