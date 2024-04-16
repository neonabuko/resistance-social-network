package com.api.resistancesocialnetwork.service;

import com.api.resistancesocialnetwork.repository.repositoryinterfaces.ResistanceUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
    private final ResistanceUserRepository resistanceUserRepository;

    public AuthService(ResistanceUserRepository resistanceUserRepository) {
        this.resistanceUserRepository = resistanceUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return resistanceUserRepository.findUserDetailsBy(username).orElseThrow();
    }
}
