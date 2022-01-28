package com.justcountit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository appUserRepository;

    public AppUser getUserById(Long id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    public AppUser getUserByEmail(String email){
        return appUserRepository.getAppUserByEmail(email).orElseThrow();
    }

    public Long getUserId(String email){
        return getUserByEmail(email).getId();
    }





}
