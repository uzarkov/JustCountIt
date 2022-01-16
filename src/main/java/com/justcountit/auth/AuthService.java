package com.justcountit.auth;

import com.justcountit.user.AppUser;
import com.justcountit.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;

    public AppUser getEnabledUser(String email) {

        return appUserRepository.getAppUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
